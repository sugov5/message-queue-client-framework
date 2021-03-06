/*
 * Copyright 2015-2016 Dark Phoenixs (Open-Source Organization).
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
package org.darkphoenixs.kafka.listener;

import org.darkphoenixs.mq.consumer.Consumer;
import org.darkphoenixs.mq.exception.MQException;

/**
 * <p>Title: MessageConsumerListener</p>
 * <p>Description: 消费者监听器</p>
 *
 * @author Victor.Zxy
 * @version 1.0
 * @see KafkaMessageListener
 * @since 2015-06-01
 */
public class MessageConsumerListener<K, V> extends KafkaMessageListener<K, V> {

    /**
     * messageConsumer
     */
    private Consumer<V> consumer;

    /**
     * @return the consumer
     */
    public Consumer<V> getConsumer() {
        return consumer;
    }

    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(Consumer<V> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onMessage(V message) throws MQException {

        if (consumer != null)

            consumer.receive(message);
        else
            throw new MQException("Consumer is null !");

    }
}
