package com.qdcares.smartmq.listeners;

import com.qdcares.smartmq.dto.ChatMessage;

public interface MessageReceiverListener {
	void onMessage(ChatMessage message);
}
