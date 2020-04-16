package com.vinspier.search.service;

import com.vinspier.common.pojo.PageResult;
import com.vinspier.item.pojo.Spu;
import com.vinspier.search.pojo.Goods;
import com.vinspier.search.vo.SearchVo;

import java.io.IOException;

public interface SearchService {

    Goods buildGoods(Spu spu) throws IOException;

    PageResult<Goods> search(SearchVo searchVo);

    void createIndex(Long id) throws Exception;

    void deleteIndex(Long id) throws Exception;
}
