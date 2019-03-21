package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.PerDishSample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerDishSampleDao {
    int deleteByPrimaryKey(Long id);

    int insert(PerDishSample record);

    int insertSelective(PerDishSample record);

    PerDishSample selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PerDishSample record);

    int updateByPrimaryKeyWithBLOBs(PerDishSample record);

    int updateByPrimaryKey(PerDishSample record);

    List<PerDishSample> selectByNameAndIdCard(@Param("name") String name, @Param("idCard") String idCard);
}