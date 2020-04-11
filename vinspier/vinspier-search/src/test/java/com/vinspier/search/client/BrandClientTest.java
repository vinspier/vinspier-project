package com.vinspier.search.client;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.search.SearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class BrandClientTest {

    @Autowired
    private BrandClient brandClient;

    @Test
    public void findById(){
        ResponseTemplate template =  brandClient.findById(4986L);
        System.out.println(template.toString());
    }

}