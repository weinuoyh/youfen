package com.moerlong.youfen.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.moerlong.youfen.Utils.HttpUtils;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.ValidationEnum;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.dao.ValidationInfoMapper;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.OperationLogTbl;
import com.moerlong.youfen.pojo.ValidationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Description: 类作用描述
* @Author: mxd
* @CreateDate: 2018/10/11 18:01
*/
@Service("validationBankCardServiceImpl")
public class ValidationBankCardServiceImpl extends AbstractInvokeService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    Date nowDate = new Date();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ValidationInfoMapper validationInfoMapper;

    @Value(value = "${spring.oreo.cost.validation.bankcard}")
    private String bankcard_cost;

    @Override
    public Result invokeAfter(String apiCode,String getUrl, String method,Map<String,String> businessParamsMap) throws Exception {
        String idCard = businessParamsMap.get("idcard") != null ? businessParamsMap.get("idcard") : "";
        String name = businessParamsMap.get("name") != null ? businessParamsMap.get("name") : "";
        String cellPhone = businessParamsMap.get("cellphone") != null ? businessParamsMap.get("cellphone") : "";
        String bankCard = businessParamsMap.get("bankcard") != null ? businessParamsMap.get("bankcard") : "";
        String type = businessParamsMap.get("type");
        String typeName = ValidationEnum.getTypeName(type);
        String res = null;
        String cost = "0";
        OperationLogTbl operationLogTbl = new OperationLogTbl();
        // 查询数据库是否有有效数据

        List<ValidationInfo> list = validationInfoMapper.selectValidationInfo(name,idCard,cellPhone,bankCard);

        if (list.size() > 0) {
            return Result.success("一致" , "0");
        }
        // 无有效数据远程调用第三方
        try {
            logger.info("优分银行卡有效性核查访问url"+getUrl + ",typeName=" + typeName);
            res = HttpUtils.httpURLConectionGET(getUrl);
            // 监听写日志
            logger.info("优分银行卡有效性核查返回参数"+res);
            operationLogTbl.setApiName("优分银行卡有效性核查");
            operationLogTbl.setApiUri(getUrl);
            operationLogTbl.setCreateTime(nowDate);
            Map map = (Map) JSONUtils.parse(res);
            //一致数据，成功调用第三方，存入数据库
            if ("0000".equals(map.get("resCode"))) {
                Map dataMap = (Map) map.get("data");
                if (dataMap != null) {
                    String statusCode = (String) dataMap.get("statusCode");
                    String statusMsg = (String) dataMap.get("statusMsg");
                    if ("2050".equals(statusCode)) { //验证成功 计费
                        //inser数据库
                        ValidationInfo validationInfo = new ValidationInfo();
                        validationInfo.setIdCard(idCard);
                        validationInfo.setBankCard(bankCard);
                        validationInfo.setCellPhone(cellPhone);
                        validationInfo.setName(name);
                        validationInfo.setType(type);
                        validationInfo.setTypeName(typeName);
                        validationInfo.setCreateTime(nowDate);
                        validationInfo.setLastModifyTime(nowDate);
                        validationInfo.setOprationUser(0);
                        try {
                            validationInfoMapper.insertSelective(validationInfo);
                        } catch (Exception e) {
                            logger.error("优分身份核查有效数据结果存入数据库异常" + e.getMessage());
                        }
                        cost = this.bankcard_cost;
                        // 监听写日志
                        operationLogTbl.setIsSucceed(true);
                        operationLogTbl.setMessage(res);
                        operationLogTbl.setSpendAmount(Double.valueOf(cost));
                        applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                        return Result.success(statusMsg,cost);
                    }else if("2051".equals(statusCode) || "2052".equals(statusCode)){ //2051 验证不确定 2052 验证失败 计费
                        // 监听写日志
                        cost = this.bankcard_cost;
                        operationLogTbl.setIsSucceed(true);
                        operationLogTbl.setMessage(res);
                        operationLogTbl.setSpendAmount(Double.valueOf(cost));
                        applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                        return Result.success(statusMsg,cost);
                    }else {
                        // 监听写日志
                        operationLogTbl.setIsSucceed(false);
                        operationLogTbl.setMessage(res);
                        applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                        return Result.result(statusMsg,cost);
                    }
                }
            } else {
                // 监听写日志
                operationLogTbl.setIsSucceed(false);
                operationLogTbl.setMessage(res);
                applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                return Result.result(map.get("resMsg"),cost);
            }
        } catch (Exception e) {
            logger.error("优分身份核查接口调用异常" + e.toString());
            return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
        }
        return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
    }
}
