package com.moerlong.youfen.event;

import com.moerlong.youfen.pojo.OperationLogTbl;
import org.springframework.context.ApplicationEvent;

/*
package:com.moerlong.youfen.event
project:youfen
date:2018/9/21
name:shaxueting
*/
/**
 * 日志监听
 */
public class LogEvent extends ApplicationEvent {
    private OperationLogTbl operationLogTbl;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LogEvent(Object source, OperationLogTbl operationLogTbl) {
        super(source);
        this.operationLogTbl = operationLogTbl;

    }

    public OperationLogTbl getOperationLogTbl() {
        return operationLogTbl;
    }

    public void setOperationLogTbl(OperationLogTbl operationLogTbl) {
        this.operationLogTbl = operationLogTbl;
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "operationLogTbl=" + operationLogTbl +
                '}';
    }
}
