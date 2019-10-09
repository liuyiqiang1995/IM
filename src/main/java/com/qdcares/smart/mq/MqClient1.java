package com.qdcares.smart.mq;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 14:13 2019/10/8
 */
@Component
public class MqClient1 {

    private MqttServerUtil mqClient;

    @PostConstruct
    void init(){
        try {
            String ip = "tcp://192.168.163.95:1884";
            mqClient = new MqttServerUtil(ip,"client/lyq");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(initialDelay = 1000,fixedDelay = 6000)
    void send(){
        String a = "12345_______________________";
        try {
            mqClient.publish("lyq",a);
        } catch (MqttException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(initialDelay = 1000,fixedDelay = 1000)
    void rececive(){
        try {
            mqClient.subscribe("lyq");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
