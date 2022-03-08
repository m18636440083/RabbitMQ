package org.yandong.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.yandong.util.ConnectionUtil;

/**
 * 路由模式
 * 生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明路由（路由名，路由类型）
        // direct：根据路由键进行定向分发消息
        channel.exchangeDeclare("test_exchange_direct", "direct");

        String message = "消费者2";
        // 此处insert代表路由键
        channel.basicPublish("test_exchange_direct", "query", null, message.getBytes());

        System.out.println("用户系统" + message);
        channel.close();
        connection.close();
    }
}
