package com.moerlong.youfen.service;

import com.moerlong.youfen.constant.state.Result;

import java.util.Map;

/**
 * @author  shaxueting
 */
public interface RemoteInvokeService {

    Result invoke(String apiCode,String method, Map<String, String> params) throws Exception;
}