package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.PerDishDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerDishDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(PerDishDetail record);

    int insertSelective(PerDishDetail record);

    PerDishDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PerDishDetail record);

    int updateByPrimaryKey(PerDishDetail record);

    PerDishDetail selectById(@Param("dishId") String dishId);
}