package com.vinspier.item.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_brand")
@ToString
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 品牌名称
    private String name;

    // 品牌图片
    private String image;

    // 字母
    private Character letter;
}
