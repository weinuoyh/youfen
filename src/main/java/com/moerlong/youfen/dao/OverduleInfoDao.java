package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.OverduleInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverduleInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(OverduleInfo record);

    int insertSelective(OverduleInfo record);

    OverduleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OverduleInfo record);

    int updateByPrimaryKey(OverduleInfo record);

    List<OverduleInfo> selectByMobileAndCycle(@Param("mobile") String mobile, @Param("cycle") int cycle);
}