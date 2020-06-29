package com.vinspier.sms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * 读取属性配置文件中的vinspier.sms中的属性
 */
@ConfigurationProperties(prefix = "vinspier.sms")
public class SmsProperties {

    @Value("{accessKeyId}")
    String accessKeyId;

    @Value("{accessKeySecret}")
    String accessKeySecret;

    @Value("{signName}")
    String signName;

    @Value("{verifyCodeTemplate}")
    String verifyCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
