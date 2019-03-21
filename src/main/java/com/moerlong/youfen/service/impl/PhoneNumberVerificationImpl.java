package com.moerlong.youfen.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.moerlong.youfen.Utils.HttpUtils;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.dao.IdcardMobileInfoDao;
import com.moerlong.youfen.dao.MobileAttrInfoDao;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.IdcardMobileInfo;
import com.moerlong.youfen.pojo.MobileAttrInfo;
import com.moerlong.youfen.pojo.OperationLogTbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/*
package:com.moerlong.youfen.service.impl
project:youfen
date:2018/10/9
name:shaxueting
*/
@Service("phoneNumberVerificationImpl")
public class PhoneNumberVerificationImpl extends AbstractInvokeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    Date nowDate = new Date();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MobileAttrInfoDao mobileAttrInfoDao;


    @Override
    public Result invokeAfter(String apiCode, String getUrl, String method, Map<String, String> businessParamsMap) throws Exception {
        String res = null;
        JSONObject resultJsonObject = null;
        OperationLogTbl operationLogTbl = new OperationLogTbl();
        String cellphone = businessParamsMap.get("cellphone");
        //查询数据库是否有有效数据
        MobileAttrInfo mobileAttrInfo = mobileAttrInfoDao.selectByMobile(cellphone);
        if (mobileAttrInfo != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("provinceName", mobileAttrInfo.getProvinceName());
            jsonObject.put("desc", mobileAttrInfo.getMobileCardType());
            jsonObject.put("areaName", mobileAttrInfo.getAreaName());
            jsonObject.put("isp", mobileAttrInfo.getMobileIsp());
            jsonObject.put("provinceCode", mobileAttrInfo.getProvinceCode());
            jsonObject.put("areaCode", mobileAttrInfo.getAreaCode());
            jsonObject.put("cityCode", mobileAttrInfo.getCityCode());
            return Result.success(jsonObject,"");
        }
        //无有效数据远程调用第三方
        try {
            logger.info("优分手机号归属地访问url" + getUrl);
            res = HttpUtils.httpURLConectionGET(getUrl);
            logger.info("优分手机号归属地返回参数" + res);
            operationLogTbl.setApiName("手机号归属地");
            operationLogTbl.setApiUri(getUrl);
            operationLogTbl.setCreateTime(nowDate);
            JSONObject jsonObject = JSONObject.parseObject(res);
            //一致数据，成功调用第三方，存入数据库
            if ("0000".equals(jsonObject.get("resCode"))) {
                String data = jsonObject.get("data").toString();
                JSONObject dataJsonObject = JSONObject.parseObject(data);
                if (dataJsonObject != null) {
                    if ("2012".equals(dataJsonObject.get("statusCode"))) {
                        operationLogTbl.setIsSucceed(true);
                        String result = dataJsonObject.get("result").toString();
                        resultJsonObject = JSONObject.parseObject(result);
                        //inser数据库
                        MobileAttrInfo mobileAttrInfoNew = new MobileAttrInfo();
                        mobileAttrInfoNew.setMobile(cellphone);
                        mobileAttrInfoNew.setAreaCode(resultJsonObject.get("areaCode").toString());
                        mobileAttrInfoNew.setCityName("");
                        mobileAttrInfoNew.setProvinceName(resultJsonObject.get("provinceName").toString());
                        mobileAttrInfoNew.setAreaName(resultJsonObject.get("areaName").toString());
                        mobileAttrInfoNew.setCityCode(resultJsonObject.get("cityCode").toString());
                        mobileAttrInfoNew.setMobileCardType(resultJsonObject.get("desc").toString());
                        mobileAttrInfoNew.setMobileIsp(resultJsonObject.get("isp").toString());
                        mobileAttrInfoNew.setProvinceCode(resultJsonObject.get("provinceCode").toString());
                        mobileAttrInfoNew.setIsDeleted(true);
                        mobileAttrInfoNew.setCreateTime(nowDate);
                        mobileAttrInfoNew.setLastModifyTime(nowDate);
                        mobileAttrInfoNew.setOprationUser(0);
                        try {
                            mobileAttrInfoDao.insertSelective(mobileAttrInfoNew);
                        } catch (Exception e) {
                            logger.error("手机归属地有效数据结果存入数据库异常" + e.getMessage());
                        }
                    } else {
                        // 监听写日志
                        operationLogTbl.setIsSucceed(false);
                        operationLogTbl.setMessage(res);
                        applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                        return Result.result(dataJsonObject.get("statusMsg"),"0");
                    }
                }
                // 监听写日志
                operationLogTbl.setIsSucceed(true);
                operationLogTbl.setMessage(res);
                applicationContext.publishEvent(new LogEvent(this, operationLogTbl));

                return Result.success(resultJsonObject,"");

            } else {
                // 监听写日志
                operationLogTbl.setIsSucceed(false);
                operationLogTbl.setMessage(res);
                applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                return Result.result(jsonObject.get("resMsg"),"0");
            }

        } catch (Exception e) {
            logger.error("手机归属地接口调用异常" + e.getMessage());
            return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
        }
    }
}
