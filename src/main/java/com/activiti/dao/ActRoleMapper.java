package com.activiti.dao;

import com.activiti.entity.ActRole;

public interface ActRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActRole record);

    int insertSelective(ActRole record);

    ActRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActRole record);

    int updateByPrimaryKey(ActRole record);
}