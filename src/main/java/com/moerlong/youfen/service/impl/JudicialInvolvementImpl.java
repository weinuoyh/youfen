package com.moerlong.youfen.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moerlong.youfen.Utils.HttpUtils;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.dao.IdcardMobileInfoDao;
import com.moerlong.youfen.dao.PerDishDetailDao;
import com.moerlong.youfen.dao.PerDishSampleDao;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.IdcardMobileInfo;
import com.moerlong.youfen.pojo.OperationLogTbl;
import com.moerlong.youfen.pojo.PerDishDetail;
import com.moerlong.youfen.pojo.PerDishSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*
package:com.moerlong.youfen.service.impl
project:youfen
date:2018/10/9
name:shaxueting
*/
@Service("judicialInvolvementImpl")
public class JudicialInvolvementImpl extends AbstractInvokeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    Date nowDate = new Date();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    PerDishSampleDao perDishSampleDao;

    @Autowired
    PerDishDetailDao perDishDetailDao;


    @Override
    public Result invokeAfter(String apiCode,String getUrl, String method,Map<String,String> businessParamsMap) throws Exception {
        if("acedata.user.simplequery.v1".equals(apiCode)) {
            String res = null;
            String name = businessParamsMap.get("name");
            String idcard = businessParamsMap.get("idcard");
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            //查询数据库是否有有效数据
            List<PerDishSample> perDishSamplesList = perDishSampleDao.selectByNameAndIdCard(name, idcard);
            if (perDishSamplesList.size() > 0) {
                ArrayList<Object> list = new ArrayList<>();
                for (PerDishSample o : perDishSamplesList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", o.getDishId());
                    jsonObject.put("caseTime", o.getCaseTime());
                    jsonObject.put("content", o.getContent());
                    jsonObject.put("title", o.getTitle());
                    jsonObject.put("dataType", o.getDataType());
                    jsonObject.put("matchRatio", o.getMatchRatio());
                    list.add(jsonObject);
                }
                return Result.success(list,"");
            }
            //无有效数据远程调用第三方
            try {
                logger.info("优分个人法院失信（简项）访问url" + getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                // res="{\"resCode\":\"0000\",\"resMsg\":\"提交成功\",\"data\":{\"statusCode\":\"2012\",\"statusMsg\":\"查询成功\",\"result\":{\"count\":\"8\",\"list\":[{\"id\":\"c2018420203zhi52_t20180110_pchencanlin\",\"caseTime\":\"2018年01月10日\",\"content\":\"有履行能力而拒不履行生效法律文...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2017420204zhi23_t20170118_pchencanlin\",\"caseTime\":\"2017年01月18日\",\"content\":\"有履行能力而拒不履行生效法律文...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2016420103zhi1338_t20160829_pchencanlin\",\"caseTime\":\"2016年08月29日\",\"content\":\"其他有履行能力而拒不履行生效法...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2015xiaexialuzhi66_t20150209_pchencanlin\",\"caseTime\":\"2015年02月09日\",\"content\":\"违反财产报告制度,其他有履行能...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2014xiaexialuzhi320_t20141014_pchencanlin\",\"caseTime\":\"2014年10月14日\",\"content\":\"违反财产报告制度,其他有履行能...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2014xiaexialuzhi319_t20141014_pchencanlin\",\"caseTime\":\"2014年10月14日\",\"content\":\"违反财产报告制度,其他有履行能...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2014xiaexialuzhi310_t20140916_pchencanlin\",\"caseTime\":\"2014年09月16日\",\"content\":\"违反财产报告制度,其他有履行能...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"},{\"id\":\"c2014huangzhi221_t20140724_pchencanlin\",\"caseTime\":\"2014年07月24日\",\"content\":\"其他有履行能力而拒不履行生效法...\",\"title\":\"陈灿林\",\"dataType\":\"失信公告\",\"matchRatio\":\"99%\"}]}}}\n";
                logger.info("优分个人法院失信（简项）返回参数" + res);
                operationLogTbl.setApiName("个人法院失信（简项）");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //一致数据，成功调用第三方，存入数据库
                if ("0000".equals(jsonObject.get("resCode"))) {
                    String data = jsonObject.get("data").toString();
                    JSONObject dataJsonObject = JSONObject.parseObject(data);
                    if (dataJsonObject != null) {
                        if ("2012".equals(dataJsonObject.get("statusCode")) || "2007".equals(dataJsonObject.get("statusCode"))) {
                            String result = dataJsonObject.get("result").toString();
                            JSONObject resultJsonObject = JSONObject.parseObject(result);
                            String jsonDocList = resultJsonObject.get("list").toString();
                            JSONArray docList = JSONArray.parseArray(jsonDocList);
                            for (Object o : docList) {
                                Date date = new Date();
                                JSONObject dishonestyListMap = JSONObject.parseObject(o.toString());
                                //inser数据库
                                PerDishSample perDishSample = new PerDishSample();
                                perDishSample.setName(name);
                                perDishSample.setIdCard(idcard);
                                perDishSample.setDishId(dishonestyListMap.get("id").toString());
                                perDishSample.setCaseTime(dishonestyListMap.get("caseTime").toString());
                                perDishSample.setContent(dishonestyListMap.get("content").toString());
                                perDishSample.setTitle(dishonestyListMap.get("title").toString());
                                perDishSample.setDataType(dishonestyListMap.get("dataType").toString());
                                perDishSample.setMatchRatio(dishonestyListMap.get("matchRatio").toString());
                                perDishSample.setIsDeleted(true);
                                perDishSample.setCreateTime(date);
                                perDishSample.setLastModifyTime(date);
                                perDishSample.setOprationUser(0);
                                try {
                                    perDishSampleDao.insertSelective(perDishSample);
                                } catch (Exception e) {
                                    logger.error("个人法院失信（简项）有效数据结果存入数据库异常" + e.getMessage());
                                }
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
                    return Result.success(dataJsonObject,"");
                } else {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this, operationLogTbl));
                    return Result.result(jsonObject.get("resMsg"),"0");
                }
            } catch (Exception e) {
                logger.error("个人法院失信（简项）接口调用异常" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        }else if("acedata.user.detailquery.v1".equals(apiCode)) {
            String docId = businessParamsMap.get("docId");
            String res = null;
            JSONObject resultJsonObject =null;
            OperationLogTbl operationLogTbl = new OperationLogTbl();
            //查询数据库是否有有效数据
            PerDishDetail perDishDetail = perDishDetailDao.selectById(docId);
            if (perDishDetail != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("docId",perDishDetail.getDishId());
                jsonObject.put("caseTime",perDishDetail.getCaseTime());
                jsonObject.put("gender",perDishDetail.getGender());
                jsonObject.put("performInfo",perDishDetail.getPerfInfo());
                jsonObject.put("performBasisNo",perDishDetail.getPerfBasisNo());
                jsonObject.put("relatedParty",perDishDetail.getRelatedParty());
                jsonObject.put("judgmentCourtName",perDishDetail.getJudgCoutName());
                jsonObject.put("cardId",perDishDetail.getIdCard());
                jsonObject.put("executeCourtName",perDishDetail.getExecCoutName());
                jsonObject.put("concreteSituation",perDishDetail.getConcSitu());
                jsonObject.put("obligation",perDishDetail.getObligation());
                jsonObject.put("age",perDishDetail.getAge());
                jsonObject.put("executedName",perDishDetail.getExecName());
                jsonObject.put("province",perDishDetail.getProvince());
                jsonObject.put("publishTime",perDishDetail.getPublishTime());
                jsonObject.put("judicialType",perDishDetail.getDataType());
                return Result.success(jsonObject,"");
            }
            //无有效数据远程调用第三方
            try {
                logger.info("优分个人法院失信（详情）访问url"+getUrl);
                res = HttpUtils.httpURLConectionGET(getUrl);
                // 监听写日志
                logger.info("优分个人法院失信（详情）返回参数"+res);
                operationLogTbl.setApiName("个人法院失信（详情）");
                operationLogTbl.setApiUri(getUrl);
                operationLogTbl.setCreateTime(nowDate);
                JSONObject jsonObject = JSONObject.parseObject(res);
                //一致数据，成功调用第三方，存入数据库
                if ("0000".equals(jsonObject.get("resCode"))) {
                    String data = jsonObject.get("data").toString();
                    JSONObject dataJsonObject = JSONObject.parseObject(data);
                    if (dataJsonObject != null) {
                        if ("2012".equals(dataJsonObject.get("statusCode"))||"2007".equals(dataJsonObject.get("statusCode"))) {
                            String result = dataJsonObject.get("result").toString();
                            JSONArray resultList = JSONArray.parseArray(result);
                            resultJsonObject = JSONObject.parseObject(resultList.getString(0));
                            //inser数据库
                            PerDishDetail perDishDetailNew = new PerDishDetail();
                            perDishDetailNew.setDishId(docId);
                            perDishDetailNew.setCaseTime(resultJsonObject.get("caseTime").toString());
                            perDishDetailNew.setGender(resultJsonObject.get("gender").toString());
                            perDishDetailNew.setPerfInfo(resultJsonObject.get("performInfo").toString());
                            perDishDetailNew.setPerfBasisNo(resultJsonObject.get("performBasisNo").toString());
                            perDishDetailNew.setRelatedParty(resultJsonObject.get("relatedParty").toString());
                            perDishDetailNew.setJudgCoutName(resultJsonObject.get("judgmentCourtName").toString());
                            perDishDetailNew.setIdCard(resultJsonObject.get("cardId").toString());
                            perDishDetailNew.setExecName(resultJsonObject.get("executeCourtName").toString());
                            perDishDetailNew.setConcSitu(resultJsonObject.get("concreteSituation").toString());
                            perDishDetailNew.setObligation(resultJsonObject.get("obligation").toString());
                            perDishDetailNew.setAge(Integer.parseInt(resultJsonObject.get("age").toString()));
                            perDishDetailNew.setExecName(resultJsonObject.get("executedName").toString());
                            perDishDetailNew.setProvince(resultJsonObject.get("province").toString());
                            perDishDetailNew.setCaseNum("");
                            perDishDetailNew.setPublishTime(resultJsonObject.get("publishTime").toString());
                            perDishDetailNew.setDataType(resultJsonObject.get("judicialType").toString());
                            perDishDetailNew.setIsDeleted(true);
                            perDishDetailNew.setCreateTime(nowDate);
                            perDishDetailNew.setLastModifyTime(nowDate);
                            perDishDetailNew.setOprationUser(0);
                            try {
                                perDishDetailDao.insertSelective(perDishDetailNew);
                            } catch (Exception e) {
                                logger.error("个人法院失信（详情）有效数据结果存入数据库异常" + e.getMessage());
                            }
                        } else {
                            // 监听写日志
                            operationLogTbl.setIsSucceed(false);
                            operationLogTbl.setMessage(res);
                            applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                            return Result.result(dataJsonObject.get("statusMsg"),"0");
                        }
                    }
                    // 监听写日志
                    operationLogTbl.setIsSucceed(true);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                    return Result.success(resultJsonObject,"");
                } else {
                    // 监听写日志
                    operationLogTbl.setIsSucceed(false);
                    operationLogTbl.setMessage(res);
                    applicationContext.publishEvent(new LogEvent(this,operationLogTbl));
                    return Result.result(jsonObject.get("resMsg"),"0");
                }
            } catch (Exception e) {
                logger.error("个人法院失信（详情）接口调用异常" + e.getMessage());
                return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
            }
        }
        return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
    }
}
