package com.vinspier.item.service;

public interface RabbitMsgService {

    void sendMsg(String type,Long id);

}
