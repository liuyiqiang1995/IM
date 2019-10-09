package com.qdcares.smart.mq.listeners;

import com.qdcares.smart.mq.dto.ChatMessage;

public interface MessageReceiverListener {
	void onMessage(ChatMessage message);
}
