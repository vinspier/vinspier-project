package com.vinspier.search;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.pojo.Spu;
import com.vinspier.search.client.GoodsClient;
import com.vinspier.search.pojo.Goods;
import com.vinspier.search.repository.GoodsRepository;
import com.vinspier.search.service.SearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchApplicationTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

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

    /**
     * 创建商品数据索引文档
     * */
    @Test
    public void createDocs(){
        int page = 2;
        int rows = 100;
        do {
            ResponseTemplate<PageResult<SpuBo>> template = goodsClient.querySpuBoByPage(null,true,page,rows);
            PageResult<SpuBo> spuBoPageResult = template.getData();
            List<Goods> goodsList = spuBoPageResult.getItems().stream().map(spuBo -> {
                try {
                    return searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
            goodsRepository.saveAll(goodsList);
            rows = spuBoPageResult.getItems().size();
            page++;
        }while (rows == 100);
    }

    /**
     * 删除索引数据
     * */
    public void deleteDocs(){
        goodsRepository.deleteAll();
    }

}
