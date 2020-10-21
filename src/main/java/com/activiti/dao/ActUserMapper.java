package com.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.activiti.entity.ActUser;

public interface ActUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActUser record);

    int insertSelective(ActUser record);

    ActUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActUser record);

    int updateByPrimaryKey(ActUser record);
    
    ActUser selectByName(@Param("name") String name);
    
    List<ActUser> queryAllUser();
}