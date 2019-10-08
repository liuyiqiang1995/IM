package com.qdcares.smart.mq.listeners;

public interface ExceptionHandleListener {
	void operationException(Throwable exception);
}
