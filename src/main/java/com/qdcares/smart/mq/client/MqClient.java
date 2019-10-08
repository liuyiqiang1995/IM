package com.qdcares.smart.mq.client;

import com.qdcares.smart.mq.listeners.ConnectSuccessListener;
import com.qdcares.smart.mq.listeners.ExceptionHandleListener;
import javafx.beans.binding.LongExpression;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 16:41 2019/10/8
 */
@Slf4j
public class MqClient {

    private static String HOST = "tcp://192.168.163.95:1884";
    private static String clientId;
    private MqttServerUtil mqttServerUtil;
    private static Long quiesceTimeout;

    private ExceptionHandleListener exceptionHandleListener;
    private ConnectSuccessListener connectSuccessListener;

    public MqClient(String HOST, String clientId) {
        this.HOST = HOST;
        this.clientId = clientId;
    }

    public void connect(){
        try {
            mqttServerUtil = new MqttServerUtil(HOST,clientId);
            mqttServerUtil.doConnnect();
            //调用callback listener
            if(connectSuccessListener != null){
                connectSuccessListener.onMessage();
            }
        } catch (MqttException e) {
            //丢给调用方处理
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
            log.error("mqttClient initialization failed...",e);
        }
    }

    /**
     * 发送聊天消息
     * @param msgType
     * @param message
     */
    public void sendChatMessage(Integer msgType, String message){
        try{
            switch (msgType){
                case 1:
                    break;
                case 2:
                    break;
                default:
                    log.error("type {} id not found",msgType);
                    break;
            }
        }catch (Exception e){
            if(exceptionHandleListener != null){
                exceptionHandleListener.operationException(e);
            }
        }
    }

    public void disConnect(){
        mqttServerUtil.disConnect(quiesceTimeout);
    }

    public void addExceptionHandleListener(ExceptionHandleListener exceptionHandleListener) {
        this.exceptionHandleListener = exceptionHandleListener;
    }

    public void addConnectSuccessListener(ConnectSuccessListener connectSuccessListener){
        this.connectSuccessListener = connectSuccessListener;
    }

}
