package com.vinspier.search.client;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.search.SearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class GoodsClientTest {

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 查询某一商品的特有信息
     * */
    @Test
    public void getSpuDetail(){
        ResponseTemplate template = goodsClient.querySpuDetailBySpuId(2L);
        System.out.println(template.toString());
    }
    /**
     * 创建商品数据索引文档
     * */
    @Test
    public void querySpuBoByPage() {
        int page = 1;
        int rows = 10;
        ResponseTemplate<PageResult<SpuBo>> template = goodsClient.querySpuBoByPage(null, true, page, rows);
        System.out.println(template.toString());
    }
}