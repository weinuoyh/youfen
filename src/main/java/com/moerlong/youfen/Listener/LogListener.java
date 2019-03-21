package com.moerlong.youfen.Listener;

import com.moerlong.youfen.dao.OperationLogTblDao;
import com.moerlong.youfen.event.LogEvent;
import com.moerlong.youfen.pojo.OperationLogTbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
package:com.moerlong.youfen.Listener
project:youfen
date:2018/9/21
name:shaxueting
*/
@Component
public class LogListener implements ApplicationListener<LogEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OperationLogTblDao operationLogTblDao;
    @Async
    @Override
    public void onApplicationEvent(LogEvent event) {
        OperationLogTbl operationLogTbl = event.getOperationLogTbl();
        try{
            operationLogTblDao.insertSelective(operationLogTbl);
        }catch (Exception e){
            logger.error("第三方日志存储异常"+e.toString());
        }



    }
}
