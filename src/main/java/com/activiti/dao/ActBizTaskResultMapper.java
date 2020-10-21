package com.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.activiti.entity.ActBizTaskResult;

public interface ActBizTaskResultMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActBizTaskResult record);

    int insertSelective(ActBizTaskResult record);

    ActBizTaskResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActBizTaskResult record);

    int updateByPrimaryKey(ActBizTaskResult record);
    
    List<ActBizTaskResult> queryAllProcHis(@Param("procInstId") Long procInstId);
}