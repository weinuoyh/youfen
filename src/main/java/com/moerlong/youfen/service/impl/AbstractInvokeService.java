package com.moerlong.youfen.service.impl;

import com.moerlong.youfen.Utils.HelpUtils;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.service.RemoteInvokeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public abstract class AbstractInvokeService implements RemoteInvokeService {

    private Logger log = LoggerFactory.getLogger(AbstractInvokeService.class);

    @Value(value = "${sysparams.acedata.serverHost}")
    private String serverHost;

    @Value(value = "${spring.oreo.account}")
    private String account;



    @Override
    public Result invoke(String apiCode,String method, Map<String, String> params) throws Exception {

        // 获取请求url
        String url=serverHost+method+"?account="+account;
        String getUrl = HelpUtils.getUrl(url, params);
        return this.invokeAfter(apiCode,getUrl, method,params);
    }

    public abstract Result invokeAfter(String apiCode,String getUrl, String method,Map<String,String>params) throws Exception;

}
