package com.activiti.dao;

import com.activiti.entity.ActBizLeave;

public interface ActBizLeaveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActBizLeave record);

    int insertSelective(ActBizLeave record);

    ActBizLeave selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActBizLeave record);

    int updateByPrimaryKey(ActBizLeave record);
}