package com.jxduhai.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/****
 *@author yxw
 *@date 2018/3/14
 */
@Data
public class TaoResult<T> implements Serializable{

    private Long total;

    private List<T> rows;
}
