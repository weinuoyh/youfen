package com.moerlong.youfen.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moerlong.youfen.Utils.HttpUtils;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.dao.CreditDetailInfoDao;
import com.moerlong.youfen.dao.IdcardMobileInfoDao;
import com.moerlong.youfen.dao.PerBillVeriflationDao;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*
package:com.moerlong.youfen.service.impl
project:youfen
date:2018/10/9
name:shaxueting
*/
@Service("personalCreditFraudImpl")
public class PersonalCreditFraudImpl extends AbstractInvokeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    Date nowDate = new Date();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    IdcardMobileInfoDao idcardMobileInfoDao;

    @Autowired
    CreditDetailInfoDao creditDetailInfoDao;

    @Autowired
    PerBillVeriflationDao perBillVeriflationDao;

    @Value(value = "${spring.oreo.cost.acedata.user.creditinfoall}")
    private String creditinfoall;

    @Value(value = "${spring.oreo.cost.acedata.user.overdueloan}")
    private String overdueloan;

    @Value(value = "${spring.oreo.cost.acedata.user.verificationB}")
    private String verificationB;

    @Value(value = "${spring.oreo.cost.acedata.user.creditinfo}")
    private String creditinfo;


    @Override
    public Result invokeAfter(String apiCode, String getUrl, String method, Map<String, String> params) throws Exception {

        if ("acedata.user.creditinfo.v1".equals(apiCode)) {
            /**
             *多头借贷信息核查接口开发
             */
            String res = null;
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            try {
                logger.info("多头借贷信息核查接口开发访问url" + getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("多头借贷信息核查接口开发返回参数" + res);
                operationLogTbl.setApiName("多头借贷信息核查接口开发");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //获取json
                return jsonObject(jsonObject, operationLogTbl, res, creditinfo);
            } catch (Exception e) {
                logger.error("多头借贷信息核查接口开发" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        } else if ("acedata.user.overdueloan.v1".equals(apiCode)) {
            /**
             * 多头借贷逾期核查
             */
            String res = null;
            JSONObject dataJsonObject = null;

            OperationLogTbl operationLogTbl = new OperationLogTbl();
            try {
                logger.info("优分多头借贷逾期核查访问url" + getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("优分多头借贷逾期核查返回参数" + res);
                operationLogTbl.setApiName("多头借贷逾期核查");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //获取json
                return jsonObject(jsonObject, operationLogTbl, res, overdueloan);
            } catch (Exception e) {
                logger.error("多头借贷逾期核查接口调用异常" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }

        } else if ("acedata.user.creditinfoall.v1".equals(apiCode)) {
            String res = null;
            JSONObject resultJsonObject = null;
            String cellphone = params.get("cellphone");
            int cycle = Integer.parseInt(params.get("cycle"));
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            try {
                logger.info("优分多头借贷全量核查访问url" + getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("优分多头借贷全量核查返回参数" + res);
                operationLogTbl.setApiName("多头借贷全量核查");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //获取json
                //一致数据，成功调用第三方，存入数据库
                if ("0000".equals(jsonObject.get("resCode"))) {
                    String data = jsonObject.get("data").toString();
                    JSONObject dataJsonObject = JSONObject.parseObject(data);
                    if (dataJsonObject != null) {
                        if ("2012".equals(dataJsonObject.get("statusCode"))) {
//                            String result = dataJsonObject.get("result").toString();
//                            resultJsonObject = JSONObject.parseObject(result);
//                            //inser数据库
//                            CreditDetailInfo creditDetailInfoNew = new CreditDetailInfo();
//                            creditDetailInfoNew.setMobile(cellphone);
//                            creditDetailInfoNew.setCycle(cycle);
//                            creditDetailInfoNew.setProvName(resultJsonObject.get("province").toString());
//                            creditDetailInfoNew.setCityName(resultJsonObject.get("city").toString());
//                            creditDetailInfoNew.setData(resultJsonObject.get("data").toString());
//                            creditDetailInfoNew.setIsDeleted(true);
//                            creditDetailInfoNew.setCreateTime(nowDate);
//                            creditDetailInfoNew.setLastModifyTime(nowDate);
//                            creditDetailInfoNew.setOprationUser(0);
//                            try {
//                                creditDetailInfoDao.insertSelective(creditDetailInfoNew);
//                            } catch (Exception e) {
//                                logger.error("多头借贷全量核查有效数据结果存入数据库异常" + e.getMessage());
//                            }
                        } else if ("2007".equals(dataJsonObject.get("statusCode"))) {

                        } else {
                            // 监听写日志
                            operationLogTbl.setIsSucceed(false);
                            operationLogTbl.setMessage(res);
                            applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                            return Result.result(dataJsonObject.get("statusMsg"), "0");
                        }
                    }
                    // 监听写日志
                    operationLogTbl.setIsSucceed(true);
                    operationLogTbl.setSpendAmount(Double.valueOf(creditinfoall));
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.success(dataJsonObject, creditinfoall);
                } else {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.result(jsonObject.get("resMsg"), "0");
                }

            } catch (Exception e) {
                logger.error("多头借贷全量核查接口调用异常" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        } else if ("acedata.user.verificationB.v1".equals(apiCode)) {
            String res = null;
            String name = params.get("name");
            String bankcard = params.get("bankcard");
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            try {
                logger.info("个人银联账单接口开发访问url" + getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("个人银联账单接口开发返回参数" + res);
                operationLogTbl.setApiName("个人银联账单接口开发");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //一致数据，成功调用第三方，存入数据库
                if ("0000".equals(jsonObject.get("resCode"))) {
                    String data = jsonObject.get("data").toString();
                    JSONObject dataJsonObject = JSONObject.parseObject(data);
                    if (dataJsonObject != null) {
                        if ("2012".equals(dataJsonObject.get("statusCode"))) {
                            String result = dataJsonObject.get("result").toString();
                            JSONArray list = JSONArray.parseArray(result);
                            for (Object o : list) {
                                Date date = new Date();
                                JSONObject dishonestyListMap = JSONObject.parseObject(o.toString());
                                //inser数据库
                                PerBillVeriflation perBillVeriflation = new PerBillVeriflation();
                                perBillVeriflation.setName(name);
                                perBillVeriflation.setBankCard(bankcard);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String transTime = dishonestyListMap.get("transTime").toString();
                                perBillVeriflation.setTransTime(simpleDateFormat.parse(transTime));
                                perBillVeriflation.setTransAmount(Double.valueOf(dishonestyListMap.get("transAmount").toString()));
                                perBillVeriflation.setCurrency(dishonestyListMap.get("currency").toString());
                                perBillVeriflation.setIsDeleted(true);
                                perBillVeriflation.setCreateTime(date);
                                perBillVeriflation.setLastModifyTime(date);
                                perBillVeriflation.setOprationUser(0);
                                try {
                                    perBillVeriflationDao.insertSelective(perBillVeriflation);
                                } catch (Exception e) {
                                    logger.error("个人银联账单接口有效数据结果存入数据库异常" + e.getMessage());
                                }
                            }
                            // 监听写日志
                            operationLogTbl.setIsSucceed(true);
                            operationLogTbl.setMessage(res);
                            operationLogTbl.setSpendAmount(Double.valueOf(verificationB));
                            applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                            return Result.success(dataJsonObject, verificationB);
                        } else {
                            // 监听写日志
                            operationLogTbl.setIsSucceed(false);
                            operationLogTbl.setMessage(res);
                            applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                            return Result.result(dataJsonObject.get("statusMsg"), "0");
                        }
                    } else {
                        // 监听写日志
                        operationLogTbl.setIsSucceed(false);
                        operationLogTbl.setMessage(res);
                        applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                        return Result.result(dataJsonObject.get("statusMsg"), "0");
                    }

                } else {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.result(jsonObject.get("resMsg"), "0");
                }
            } catch (Exception e) {
                logger.error("个人银联账单接口开发" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        }
        return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);

    }

    private Result jsonObject(JSONObject jsonObject, OperationLogTbl operationLogTbl, String res, String cost) {
        JSONObject resultJsonObject = null;
        if ("0000".equals(jsonObject.get("resCode"))) {
            String data = jsonObject.get("data").toString();
            JSONObject dataJsonObject = JSONObject.parseObject(data);
            if (dataJsonObject != null) {
                if ("2012".equals(dataJsonObject.get("statusCode")) || "2017".equals(dataJsonObject.get("statusCode"))) {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(true);
                    operationLogTbl.setMessage(res);
                    operationLogTbl.setSpendAmount(Double.valueOf(cost));
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.success(dataJsonObject, cost);

                } else {
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.result(dataJsonObject.get("statusMsg"), "0");
                }
            } else {
                // 监听写日志
                operationLogTbl.setIsSucceed(false);
                operationLogTbl.setMessage(res);
                applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                return Result.result(resultJsonObject, "0");
            }

        } else {
            // 监听写日志
            operationLogTbl.setIsSucceed(false);
            operationLogTbl.setMessage(res);
            applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
            return Result.result(jsonObject.get("resMsg"), "0");
        }
    }
}
