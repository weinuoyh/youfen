package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.IdcardMobileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdcardMobileInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(IdcardMobileInfo record);

    int insertSelective(IdcardMobileInfo record);

    IdcardMobileInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IdcardMobileInfo record);

    int updateByPrimaryKey(IdcardMobileInfo record);

    IdcardMobileInfo selectByThreePart(@Param("idcard") String idcard,@Param("name") String name,@Param("cellphone")  String cellphone);
}