package com.moerlong.youfen.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.moerlong.youfen.Utils.HttpUtils;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.dao.IdcardMobileInfoDao;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.IdcardMobileInfo;
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
@Service("identityVerificationImpl")
public class IdentityVerificationImpl extends AbstractInvokeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    Date nowDate = new Date();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    IdcardMobileInfoDao idcardMobileInfoDao;


    @Override
    public Result invokeAfter(String apiCode,String getUrl, String method,Map<String,String> businessParamsMap) throws Exception {
        if("acedata.user.validation.v1".equals(apiCode)){
            /**
             * 姓名、手机号、身份证三要素
             */
            String idcard = businessParamsMap.get("idcard");
            String name = businessParamsMap.get("name");
            String cellphone = businessParamsMap.get("cellphone");
            String res = null;
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            //查询数据库是否有有效数据
            IdcardMobileInfo idcardMobileInfo = idcardMobileInfoDao.selectByThreePart(idcard,name,cellphone );

            if (idcardMobileInfo != null) {
                return Result.success("一致","");
            }
            //无有效数据远程调用第三方
            try {
                logger.info("优分姓名、手机号、身份证三要素访问url"+getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("优分姓名、手机号、身份证三要素返回参数"+res);
                operationLogTbl.setApiName("姓名、手机号、身份证三要素");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                Map map = (Map) JSONUtils.parse(res);
                //一致数据，成功调用第三方，存入数据库
                if ("0000".equals(map.get("resCode"))) {
                    Map dataMap = (Map) map.get("data");
                    if (dataMap != null) {
                        if ("2005".equals(dataMap.get("statusCode"))) {
                            //inser数据库
                            IdcardMobileInfo idcardMobileInfo1 = new IdcardMobileInfo();
                            idcardMobileInfo1.setIdCard(idcard);
                            idcardMobileInfo1.setMobile(cellphone);
                            idcardMobileInfo1.setName(name);
                            idcardMobileInfo1.setOprationUser(0);
                            idcardMobileInfo1.setCreateTime(nowDate);
                            idcardMobileInfo1.setLastModifyTime(nowDate);
                            try {
                                idcardMobileInfoDao.insertSelective(idcardMobileInfo1);
                            } catch (Exception e) {
                                logger.error("三要素有效数据结果存入数据库异常" + e.getMessage());
                            }
                        }else if("2006".equals(dataMap.get("statusCode"))){

                        }else {
                            // 监听写日志
                            operationLogTbl.setIsSucceed(false);
                            operationLogTbl.setMessage(res);
                            applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                            return Result.result(dataMap.get("statusMsg"),"0");
                        }
                    }
                    // 监听写日志
                    operationLogTbl.setIsSucceed(true);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                    return Result.success(dataMap.get("statusMsg"),"");
                } else {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                    return Result.result(map.get("resMsg"),"0");
                }
            } catch (Exception e) {
                logger.error("三要素接口调用异常" + e.toString());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        }
        return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
    }
}
