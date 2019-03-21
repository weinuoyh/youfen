package com.moerlong.youfen.controller;

import com.moerlong.youfen.Utils.ApplicationContextUtil;
import com.moerlong.youfen.Utils.CommonUtil;
import com.moerlong.youfen.config.AcedataConfig;
import com.moerlong.youfen.constant.ErrorMessageConstants;
import com.moerlong.youfen.constant.state.Result;
import com.moerlong.youfen.service.RemoteInvokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(tags = {"controller接口类"})
@Controller
@RequestMapping(value = "/acedata")
public class YouFenController {

    @Value(value = "${spring.oreo.account}")
    private String ACCOUNT;

    private Logger log = LoggerFactory.getLogger(YouFenController.class);

    @Autowired
    AcedataConfig acedataConfig;

    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    @ApiOperation(value = "优分接口")
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public Result acedata(@RequestBody Map map, HttpServletRequest request) {
        String businessParams = map.get("businessParams").toString();
        Result res = null;
        try {
            this.log.info("###### /api request params:{}",businessParams);
            //获取基本参数
            String apiCode = map.get("apiCode").toString();
            String version = map.get("version")!=null ? map.get("version").toString() : "v1";

            //获取服务类和方法
            String config = acedataConfig.getMethodAndServiceMaps().get(apiCode + "." + version).toString();
            Map<String, Object> configMap = CommonUtil.json2map(config);
            String method = configMap.get("method").toString();
            String serviceName = configMap.get("serviceName").toString();

            //调用
            RemoteInvokeService service = (RemoteInvokeService) ApplicationContextUtil.getApplicationContext().getBean(serviceName);
            return service.invoke(apiCode+"."+version,method, (Map) map.get("businessParams"));
        } catch (Throwable e) {
            e.printStackTrace();
            return Result.fail(ErrorMessageConstants.ABNORMAL_ERROR);
        }

    }
    /**
     * 返回页面
     * */
    @RequestMapping(value = "/sxt",method =RequestMethod.GET )
    public String index(){
        return "index";
    }


}
