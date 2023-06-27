package com.alibaba.nacos.console.model;

/**
 * NamespaceAllInfo
 *
 * @author Wenzhou
 * @since 2023/5/10 13:46
 */
public class NamespaceAllInfo extends Namespace {

    public NamespaceAllInfo(String namespace, String namespaceShowName, int quota, int configCount, int type,
                            String namespaceDesc) {
        super(namespace, namespaceShowName, namespaceDesc, quota, configCount, type);
    }

}