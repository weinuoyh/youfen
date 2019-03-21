package com.moerlong.youfen.dao;

import com.moerlong.youfen.pojo.ValidationInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ValidationInfo record);

    int insertSelective(ValidationInfo record);

    ValidationInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ValidationInfo record);

    int updateByPrimaryKey(ValidationInfo record);

    List<ValidationInfo> selectValidationInfo(@Param("name") String name,@Param("idCard") String idCard,@Param("cellPhone") String cellPhone,@Param("bankCard") String bankCard);
}