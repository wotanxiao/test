package com.jxduhai.manager.service.impl;

import com.jxduhai.manager.mapper.TestMapper;
import com.jxduhai.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/****
 *@author yxw
 *@date 2018/3/9
 */
@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestMapper testMapper;


    @Override
    public String queryDate() {
        return testMapper.queryDate();
    }
}
