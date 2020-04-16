package com.vinspier.item.service.impl;

import com.vinspier.common.enums.RabbitRouteKeyEnum;
import com.vinspier.item.service.RabbitMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息队列 服务
 * */
@Service
public class RabbitMsgServiceImpl implements RabbitMsgService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMsgServiceImpl.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * type 类型 （routeKey）
     * id 商品id
     * 未指定管道名称 使用yml配置文件默认的名称
     * */
    @Override
    public void sendMsg(String type, Long id) {
        // 所有异常都try起来 不能影响正常业务
        try {
            System.out.println(RabbitRouteKeyEnum.ITEM.getKey() + type);
            amqpTemplate.convertAndSend(RabbitRouteKeyEnum.ITEM.getKey() + type,id);
        } catch (Exception e) {
            logger.error("{}商品消息发送异常，商品id：{}",type,id,e);
        }
    }
}
