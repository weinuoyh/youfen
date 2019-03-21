package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.CreditDetailInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDetailInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(CreditDetailInfo record);

    int insertSelective(CreditDetailInfo record);

    CreditDetailInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CreditDetailInfo record);

    int updateByPrimaryKeyWithBLOBs(CreditDetailInfo record);

    int updateByPrimaryKey(CreditDetailInfo record);

    CreditDetailInfo selectByMobileAndCycle(@Param("mobile") String mobile,@Param("cycle") int cycle);

}