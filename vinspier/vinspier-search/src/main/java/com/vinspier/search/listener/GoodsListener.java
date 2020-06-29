package com.vinspier.search.listener;

import com.vinspier.search.service.SearchService;
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
    private SearchService searchService;

    /**
     * 读取消息队列中消息
     * 创建或更新商品检索信息
     *
     * */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "vinspier.search.queue.insert",durable = "true"),
            exchange = @Exchange(value = "vinspier.item.exchange",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
        )
    )
    public void listenInsert(Long id) throws Exception{
        if (id != null){
            System.out.println("=========== 创建/修改索引信息 id : " + id + "=========");
            searchService.createIndex(id);
        }
    }

    /**
     * 读取消息队列中消息
     * 删除商品检索信息
     *
     * */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "vinspier.search.queue.delete",durable = "true"),
            exchange = @Exchange(value = "vinspier.item.exchange",ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    )
    )
    public void listenDelete(Long id) throws Exception{
        if (id != null){
            System.out.println("=========== 删除索引信息 id : " + id + "=========");
            searchService.deleteIndex(id);
        }
    }

}
