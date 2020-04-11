package com.vinspier.search.client;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.item.bo.SpuBo;
import com.vinspier.item.pojo.SpecParam;
import com.vinspier.search.SearchApplication;
import com.vinspier.search.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SpecClientTest {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;

    @Test
    public void createDocs(){
        int page = 1;
        int rows = 100;
        Set<Long> cidSet = new HashSet<>();
        do {
            ResponseTemplate<PageResult<SpuBo>> template = goodsClient.querySpuBoByPage(null,true,page,rows);
            PageResult<SpuBo> spuBoPageResult = template.getData();
            spuBoPageResult.getItems().forEach(spuBo -> {
                if (!cidSet.contains(spuBo.getCid3())){
                    List<SpecParam> params = specClient.queryParams(null,spuBo.getCid3(),null,true).getData();
                    cidSet.add(spuBo.getCid3());
                }
            });
        }while (rows == 100);
    }
}