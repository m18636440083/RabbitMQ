package org.yandong.ps;

import com.rabbitmq.client.*;
import org.yandong.util.ConnectionUtil;

import java.io.IOException;

/**
 * 发布订阅模式
 * 订阅者1
 */
public class Recer1 {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 生命队列
        channel.queueDeclare("test_exchange_fanout_queue_1", false, false, false, null);

        // 绑定路由（关注）
        /*
            参数1：队列名
            参数2：交换器名称
            参数3：路由key（暂时无用，""即可）
        */
        channel.queueBind("test_exchange_fanout_queue_1", "test_exchange_fanout", "");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        channel.basicConsume("test_exchange_fanout_queue_1", true,consumer);
    }
}
