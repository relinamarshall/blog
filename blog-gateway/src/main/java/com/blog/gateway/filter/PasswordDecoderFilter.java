package com.blog.gateway.filter;


import com.blog.common.core.constant.SecurityConstants;
import com.blog.gateway.config.GatewayConfigProperties;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PasswordDecoderFilter
 * <p>
 * 密码解密工具类
 *
 * @author Wenzhou
 * @since 2023/5/11 11:17
 */
@Slf4j
@RequiredArgsConstructor
public class PasswordDecoderFilter extends AbstractGatewayFilterFactory {
    private static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

    private static final String PASSWORD = "password";

    private static final String KEY_ALGORITHM = "AES";

    private final GatewayConfigProperties gatewayConfig;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // 1. 不是登录请求，直接向下执行
            if (!CharSequenceUtil.containsAnyIgnoreCase(request.getURI().getPath(), SecurityConstants.OAUTH_TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 2. 刷新token类型，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (CharSequenceUtil.equals(SecurityConstants.REFRESH_TOKEN, grantType)) {
                return chain.filter(exchange);
            }

            // 3. 前端加密密文解密逻辑
            Class inClass = String.class;
            Class outClass = String.class;
            ServerRequest serverRequest = ServerRequest.create(exchange, MESSAGE_READERS);

            // 4. 解密生产新的报文
            Mono<?> modifiedBody = serverRequest.bodyToMono(inClass).flatMap(decryptAES());
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.putAll(exchange.getRequest().getHeaders());
            httpHeaders.remove(HttpHeaders.CONTENT_LENGTH);
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, httpHeaders);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequestDecorator decorate = decorate(exchange, httpHeaders, outputMessage);
                return chain.filter(exchange.mutate().request(decorate).build());
            }));
        };
    }


    /**
     * 原文解密
     *
     * @return Function
     */
    private Function decryptAES() {
        return s -> {
            // 构建前端对应解密AES 因子
            AES aes = new AES(Mode.CFB, Padding.NoPadding,
                    new SecretKeySpec(gatewayConfig.getEncodeKey().getBytes(), KEY_ALGORITHM),
                    new IvParameterSpec(gatewayConfig.getEncodeKey().getBytes()));

            // 获取请求密码并解密
            Map<String, String> inParamsMap = HttpUtil.decodeParamMap((String) s, CharsetUtil.CHARSET_UTF_8);
            if (inParamsMap.containsKey(PASSWORD)) {
                String password = aes.decryptStr(inParamsMap.get(PASSWORD));
                // 返回修改后报文字符
                inParamsMap.put(PASSWORD, password);
            } else {
                log.error("非法请求数据:{}", s);
            }
            return Mono.just(HttpUtil.toParams(inParamsMap, Charset.defaultCharset(), true));
        };
    }

    /**
     * 报文交换
     *
     * @param exchange      ServerWebExchange
     * @param headers       HttpHeaders
     * @param outputMessage CachedBodyOutputMessage
     * @return ServerHttpRequestDecorator
     */
    private ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());

                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }
}
