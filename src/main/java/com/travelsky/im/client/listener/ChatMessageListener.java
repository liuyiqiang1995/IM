package com.travelsky.im.client.listener;

import com.travelsky.im.client.internal.dto.ChatMessage;

public interface ChatMessageListener {
	void onMessage(ChatMessage message);
}
