package com.vinspier.goods.listener;

import com.vinspier.goods.service.GoodsHtmlService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列的 消费者
 * 监听商品操作的动作
 * */
@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 读取消息队列中消息
     * 创建或更新商品检索信息
     *
     * */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "vinspier.web.queue.insert",durable = "true"),
            exchange = @Exchange(value = "vinspier.item.exchange",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
        )
    )
    public void listenInsert(Long id) throws Exception{
        if (id != null){
            System.out.println("=========== 创建/新增静态页面信息 id : " + id + " =========");
            goodsHtmlService.createHtml(id);
        }
    }

    /**
     * 读取消息队列中消息
     * 删除商品检索信息
     *
     * */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "vinspier.web.queue.delete",durable = "true"),
            exchange = @Exchange(value = "vinspier.item.exchange",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    )
    )
    public void listenDelete(Long id) throws Exception{
        if (id != null){
            System.out.println("=========== 删除静态页面信息 id : " + id + "=========");
            goodsHtmlService.deleteHtml(id);
        }
    }

}
