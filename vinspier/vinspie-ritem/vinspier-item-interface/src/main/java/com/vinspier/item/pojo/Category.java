package com.vinspier.item.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_category")
@ToString
@Data
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // 名称
    private String name;

    // 父栏目id
    private Long parentId;

    // 是否是父级栏目
    private Boolean isParent;

    // 排序号
    private Integer sort;
}
