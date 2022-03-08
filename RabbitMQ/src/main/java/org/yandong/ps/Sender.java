package org.yandong.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.yandong.util.ConnectionUtil;

/**
 * 发布订阅模式
 * 生产者
 */
public class Sender {

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明路由(路由名，路由类型)
        // fanout：不处理路由键（只需要将队列绑定到路由上，发送到路由的消息都会被转发到与该 路由绑定的所有队列上）
        channel.exchangeDeclare("test_exchange_fanout", "fanout");
        String message = "大家好";
        channel.basicPublish("test_exchange_fanout", "", null, message.getBytes());
        System.out.println("生产者" + message);
        channel.close();
        connection.close();

    }
}
