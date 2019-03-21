package com.moerlong.youfen.Utils;

import java.util.Map;

/*
package:com.moerlong.youfen.Utils
project:youfen
date:2018/10/9
name:shaxueting
*/
public class HelpUtils {
    public static String getUrl(String url, Map<String,String > map){

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(url);
        for (String name : map.keySet()) {
            stringBuffer.append("&");
            String value = map.get(name);
            stringBuffer.append(name);
            stringBuffer.append("=");
            stringBuffer.append(value);

        }
        //getUrl=stringBuffer.deleteCharAt(stringBuffer.length()-1).toString();
        String getUrl=stringBuffer.toString();
        return getUrl;
    }
}
