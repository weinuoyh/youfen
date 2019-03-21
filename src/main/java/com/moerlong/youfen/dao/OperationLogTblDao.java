package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.OperationLogTbl;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationLogTblDao {
    int deleteByPrimaryKey(Long id);

    int insert(OperationLogTbl record);

    int insertSelective(OperationLogTbl record);

    OperationLogTbl selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperationLogTbl record);

    int updateByPrimaryKeyWithBLOBs(OperationLogTbl record);

    int updateByPrimaryKey(OperationLogTbl record);
}