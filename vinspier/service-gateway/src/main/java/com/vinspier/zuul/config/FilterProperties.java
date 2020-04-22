package com.vinspier.zuul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 无需鉴权的路径白名单
 */
@ConfigurationProperties(prefix = "vinspier.filter")
public class FilterProperties {
    private List<String> allowPaths;

    public List<String> getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(List<String> allowPaths) {
        this.allowPaths = allowPaths;
    }
}
