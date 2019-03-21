package com.moerlong.youfen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/*
package:com.moerlong.youfen.config
project:youfen
date:2018/10/9
name:shaxueting
*/
@Configuration
@ConfigurationProperties(prefix = AcedataConfig.CONF_PREFIX)
public class AcedataConfig {
    public static final String CONF_PREFIX = "sysparams.acedata";

    private Map<String,String> methodAndServiceMaps;

    public Map<String, String> getMethodAndServiceMaps() {
        return methodAndServiceMaps;
    }
    public void setMethodAndServiceMaps(Map<String, String> methodAndServiceMaps) {
        this.methodAndServiceMaps = methodAndServiceMaps;
    }
}
