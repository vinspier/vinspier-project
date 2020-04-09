package com.vinspier.search.service;

import com.vinspier.item.pojo.Spu;
import com.vinspier.search.pojo.Goods;

import java.io.IOException;

public interface SearchService {

    Goods buildGoods(Spu spu) throws IOException;

}
