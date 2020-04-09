package com.vinspier.search.client;

import com.vinspier.common.template.ResponseTemplate;
import com.vinspier.search.SearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class CategoryClientTest {
    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategories() {
        ResponseTemplate<List<String>> template = this.categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
        List<String> names = template.getData();
        names.forEach(System.out::println);
    }

}