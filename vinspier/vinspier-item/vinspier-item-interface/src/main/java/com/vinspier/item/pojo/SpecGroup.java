package com.vinspier.item.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Table(name = "tb_spec_group")
@ToString
@Data
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private String name;

    @Transient// 表中没有此字段，就忽略
    private List<SpecParam> params;
}
