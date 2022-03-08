package org.yandong.direct;

import com.rabbitmq.client.*;
import org.yandong.util.ConnectionUtil;

import java.io.IOException;

/**
 * 路由模式
 * 消费者1
 */
public class Recer1 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare("test_exchange_direct_queue_1", false, false, false, null);

        // 绑定路由（如果路由键的类型是 添加，删除，修改 的话，绑定到这个队列1上）
        channel.queueBind("test_exchange_direct_queue_1", "test_exchange_direct", "a");
        channel.queueBind("test_exchange_direct_queue_1", "test_exchange_direct", "b");
        channel.queueBind("test_exchange_direct_queue_1", "test_exchange_direct", "c");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };

        // 4.监听队列 true:自动消息确认
        channel.basicConsume("test_exchange_direct_queue_1",true,consumer);
    }
}
