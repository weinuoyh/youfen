package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.MobileAttrInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileAttrInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(MobileAttrInfo record);

    int insertSelective(MobileAttrInfo record);

    MobileAttrInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MobileAttrInfo record);

    int updateByPrimaryKey(MobileAttrInfo record);

    MobileAttrInfo selectByMobile(@Param("mobile") String mobile);
}