package com.qdcares.smartmq.client;

import lombok.Getter;

/**
 * @Description:
 * @Author: LiuYiQiang
 * @Date: 11:14 2019/10/10
 */
public class ConnectOptions {

    /**
     * 会话心跳时间 （单位为秒 默认为60s）
     */
    @Getter private final int keepAliveInterval;
    /**
     * 连接超时时间 （单位为秒 默认为30s）
     */
    @Getter private final int connectionTimeout;
    /**
     * 设置是否清空session （默认为true）
     * false表示服务器会保留客户端的连接记录；重启客户端、服务器或连接消息传递也将可靠地满足指定的QOS；服务器会将订阅视为持久订阅
     * true表示每次连接到服务器都以新的身份连接；重启客户端、服务器或连接则无法保持指定QOS的消息传递；服务器会将订阅视为非持久订阅
     */
    @Getter private final boolean cleanSession;
    /**
     * 设置是否自动重连（默认为false）
     * false表示不会自动重连，true表示自动重连
     * 最初等待1秒钟，然后再尝试重新连接，每次失败后尝试重新连接延迟将加倍，直到2分钟为止，此后延迟将保持在2分钟。
     */
    @Getter private boolean automaticReconnect;

    /**
     * 设置当QOS为 1 或 2 时，可以同时传输的消息数量（默认为10）
     */
    @Getter private int maxInflight;

    public static class Builder{
        private int keepAliveInterval = 60;
        private int connectionTimeout = 30;
        private boolean cleanSession = true;
        private boolean automaticReconnect = false;
        private int maxInflight = 10;

        public Builder keepAliveInterval(int keepAliveInterval){
            this.keepAliveInterval = keepAliveInterval;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout){
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder cleanSession(boolean cleanSession){
            this.cleanSession = cleanSession;
            return this;
        }

        public Builder automaticReconnect(boolean automaticReconnect){
            this.cleanSession = cleanSession;
            return this;
        }

        public Builder maxInflight(int maxInflight){
            this.maxInflight = maxInflight;
            return this;
        }

        public ConnectOptions build(){
            return new ConnectOptions(this);
        }

    }

    private ConnectOptions(Builder builder){
        keepAliveInterval = builder.keepAliveInterval;
        connectionTimeout = builder.connectionTimeout;
        cleanSession = builder.cleanSession;
        automaticReconnect = builder.automaticReconnect;
        maxInflight = builder.maxInflight;
    }

}
