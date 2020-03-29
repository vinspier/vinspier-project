package com.vinspier.upload.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigFileProperties {

    @Value("${upload.path.local}")
    private String imageLocalPrefix;

    @Value("${upload.path.server}")
    private String imageServer;

    public String getImageLocalPrefix() {
        return imageLocalPrefix;
    }

    public String getImageServer() {
        return imageServer;
    }
}
