package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.PerBillVeriflation;
import org.springframework.stereotype.Repository;

@Repository
public interface PerBillVeriflationDao {
    int deleteByPrimaryKey(Long id);

    int insert(PerBillVeriflation record);

    int insertSelective(PerBillVeriflation record);

    PerBillVeriflation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PerBillVeriflation record);

    int updateByPrimaryKey(PerBillVeriflation record);
}