package com.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.activiti.entity.ActBizUrl;

@Component
public interface ActBizUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActBizUrl record);

    int insertSelective(ActBizUrl record);

    ActBizUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActBizUrl record);

    int updateByPrimaryKey(ActBizUrl record);
    
    List<ActBizUrl> queryUrlListBy(@Param("deployId") String deployId);
    
    List<ActBizUrl> queryUrlListByProcIdAndTaskId(@Param("procId") String procId, @Param("taskId") String taskId);
}