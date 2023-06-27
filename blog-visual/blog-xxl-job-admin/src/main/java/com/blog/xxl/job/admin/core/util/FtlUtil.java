package com.blog.xxl.job.admin.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import lombok.experimental.UtilityClass;

/**
 * FtlUtil
 *
 * @author Wenzhou
 * @since 2023/5/10 21:18
 */
@UtilityClass
public class FtlUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtlUtil.class);

    private static final BeansWrapper WRAPPER = new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
            .build();

    public static TemplateHashModel generateStaticModel(String packageName) {
        try {
            TemplateHashModel staticModels = WRAPPER.getStaticModels();
            return (TemplateHashModel) staticModels.get(packageName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
