package com.jxduhai.manager.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_item_cat")
public class ItemCat extends BasePojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    private String name;

    private Integer status;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "is_parent")
    private Boolean isParent;

    public String getText(){
        return this.getName();
    }

    public String getState(){
        return this.isParent? "closed" : "open";
    }
}
