package com.qdcares.smart.mq.listeners;

public interface MessageReceiverListener {
	void onMessage(Object message, Integer msgType);
}
