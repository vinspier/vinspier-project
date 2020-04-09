package com.vinspier.search;

import com.vinspier.search.pojo.Goods;
import com.vinspier.search.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchApplicationTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testRun(){
        System.out.println("start application");
    }

    /**
     * 创建索引库，以及映射
     * */
    @Test
    public void createIndex(){
        this.template.createIndex(Goods.class);
        this.template.putMapping(Goods.class);
    }

}
