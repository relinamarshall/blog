package com.alibaba.nacos.config.server.service;

import com.alibaba.nacos.common.utils.Pair;
import com.alibaba.nacos.config.server.model.ConfigHistoryInfo;
import com.alibaba.nacos.config.server.model.ConfigInfoWrapper;
import com.alibaba.nacos.config.server.model.Page;
import com.alibaba.nacos.config.server.service.repository.ConfigInfoPersistService;
import com.alibaba.nacos.config.server.service.repository.HistoryConfigInfoPersistService;
import com.alibaba.nacos.plugin.auth.exception.AccessException;
import com.alibaba.nacos.plugin.encryption.handler.EncryptionHandler;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;

/**
 * HistoryService
 *
 * @author Wenzhou
 * @since 2023/5/10 13:48
 */
@Service
@RequiredArgsConstructor
public class HistoryService {
    /**
     * historyConfigInfoPersistService
     */
    private final HistoryConfigInfoPersistService historyConfigInfoPersistService;
    /**
     * configInfoPersistService
     */
    private final ConfigInfoPersistService configInfoPersistService;

    public Page<ConfigHistoryInfo> listConfigHistory(String dataId, String group, String namespaceId, Integer pageNo,
                                                     Integer pageSize) {
        return this.historyConfigInfoPersistService.findConfigHistory(dataId, group, namespaceId, pageNo, pageSize);
    }

    public ConfigHistoryInfo getConfigHistoryInfo(String dataId, String group, String namespaceId, Long nid)
            throws AccessException {
        ConfigHistoryInfo configHistoryInfo = this.historyConfigInfoPersistService.detailConfigHistory(nid);
        if (Objects.isNull(configHistoryInfo)) {
            return null;
        } else {
            this.checkHistoryInfoPermission(configHistoryInfo, dataId, group, namespaceId);
            String encryptedDataKey = configHistoryInfo.getEncryptedDataKey();
            Pair<String, String> pair = EncryptionHandler.decryptHandler(dataId, encryptedDataKey,
                    configHistoryInfo.getContent());
            configHistoryInfo.setContent(pair.getSecond());
            return configHistoryInfo;
        }
    }

    public ConfigHistoryInfo getPreviousConfigHistoryInfo(String dataId, String group, String namespaceId, Long id)
            throws AccessException {
        ConfigHistoryInfo configHistoryInfo = this.historyConfigInfoPersistService.detailPreviousConfigHistory(id);
        if (Objects.isNull(configHistoryInfo)) {
            return null;
        } else {
            this.checkHistoryInfoPermission(configHistoryInfo, dataId, group, namespaceId);
            return configHistoryInfo;
        }
    }

    public List<ConfigInfoWrapper> getConfigListByNamespace(String namespaceId) {
        return this.configInfoPersistService.queryConfigInfoByNamespace(namespaceId);
    }

    /**
     * 覆盖原有的校验方式，oracle 数据库没有空 字符串 只有 null
     *
     * @param configHistoryInfo ConfigHistoryInfo
     * @param dataId            String
     * @param group             String
     * @param namespaceId       String
     */
    private void checkHistoryInfoPermission(ConfigHistoryInfo configHistoryInfo, String dataId, String group,
                                            String namespaceId) throws AccessException {
        if (!Objects.equals(configHistoryInfo.getDataId(), dataId)) {
            throw new AccessException("Please check dataId.");
        }

        if (!Objects.equals(configHistoryInfo.getGroup(), group)) {
            throw new AccessException("Please check group.");
        }

        if (!CharSequenceUtil.isEmpty(configHistoryInfo.getTenant())
                && !Objects.equals(configHistoryInfo.getTenant(), namespaceId)) {
            throw new AccessException("Please check namespaceId.");
        }
    }
}
