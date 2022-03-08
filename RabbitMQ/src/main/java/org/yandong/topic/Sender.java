package org.yandong.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.yandong.util.ConnectionUtil;

/**
 * 通配符模式
 * 生产者
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明路由(路由名，路由类型，持久化)
        // topic：模糊匹配的定向分发
        channel.exchangeDeclare("test_exchange_topic", "topic", true);
        String message = "消息通知";
        // 发送消息(第三个参数作用是让消息持久化)
        channel.basicPublish("test_exchange_topic", "user.price", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        System.out.println("消息通知" + message);
        channel.close();
        connection.close();

    }
}
