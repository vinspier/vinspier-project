package com.vinspier.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.vinspier.sms.config.SmsProperties;
import com.vinspier.sms.util.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @auther Mr.Liao
 * @date 2019/5/18 10:27
 */
@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 发送短信验证码
     * @param msg
     * @throws ClientException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "vinspier.sms.queue",durable = "true"),
            exchange = @Exchange(value = "vinspier.sms.exchange",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"verifyCode.sms"}
    ))
    public void sendSms(Map<String, String> msg) throws ClientException {
        if (CollectionUtils.isEmpty(msg)){
            return;
        }
        String phone = msg.get("phone");
        String code = msg.get("code");
        // 若参数都为空 不做处理 前端页面做验证 1min未收到可再次发送
        if (StringUtils.isNoneBlank(phone) && StringUtils.isNoneBlank(code)){
            this.smsUtils.sendSms(phone, code, this.smsProperties.getSignName(),this.smsProperties.getVerifyCodeTemplate());
        }
    }
}
