/*
 * Copyright 2014-2024 Dark Phoenixs (Open-Source Organization).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.darkphoenixs.activemq.listener;

import java.util.concurrent.ExecutorService;

import org.darkphoenixs.mq.consumer.Consumer;
import org.darkphoenixs.mq.exception.MQException;
import org.darkphoenixs.mq.factory.ConsumerFactory;
import org.darkphoenixs.mq.listener.MessageListener;
import org.darkphoenixs.mq.message.AbstractMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: MessageFactoryConsumerListener</p>
 * <p>Description: 消费者工厂监听器</p>
 *
 * @since 2015-06-01
 * @author Victor.Zxy
 * @see MessageListener
 * @version 1.0
 */
public class MessageFactoryConsumerListener implements MessageListener<AbstractMessageBean> {

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(MessageFactoryConsumerListener.class);
	
	/** consumerFactory */
	private ConsumerFactory consumerFactory;

	/** threadPool */
	private ExecutorService threadPool;

	/**
	 * @return the consumerFactory
	 */
	public ConsumerFactory getConsumerFactory() {
		return consumerFactory;
	}

	/**
	 * @param consumerFactory
	 *            the consumerFactory to set
	 */
	public void setConsumerFactory(ConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
	}

	/**
	 * @return the threadPool
	 */
	public ExecutorService getThreadPool() {
		return threadPool;
	}

	/**
	 * @param threadPool
	 *            the threadPool to set
	 */
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	@Override
	public void onMessage(final AbstractMessageBean message) throws MQException {

		if (consumerFactory == null)	
			throw new MQException("ConsumerFactory is null !");

		if (message == null)	
			throw new MQException("Message is null !");
		
		final String messageType = message.getMessageType();
		
		if (messageType == null)	
			throw new MQException("Message Type is null !");
		
		final Consumer<AbstractMessageBean> consumer = consumerFactory.getConsumer(messageType);
		
		if (consumer == null)
			throw new MQException("Consumer is null !");
			
		if (threadPool == null)

			consumer.receive(message);
		
		else
			threadPool.execute(new Runnable() {

				@Override
				public void run() {

					try {
						consumer.receive(message);
					} catch (MQException e) {
						logger.error(e.getMessage());
					}
				}
			});
	}
}
