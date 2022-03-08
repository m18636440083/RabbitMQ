package org.yandong.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.yandong.util.ConnectionUtil;

import java.nio.charset.StandardCharsets;

/**
 * 工作队列模式
 * 发送者
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列（此处为生产者，创建队列）注明出餐口位置，通知大家来排队
        channel.queueDeclare("test_work_queue", false, false, false, null);
        for (int i=1; i<=100; i++) {
            String message = "羊肉串 ---->" + i;
            channel.basicPublish("", "test_work_queue", null, message.getBytes());
            System.out.println("烤好了" + message);
        }
        channel.close();
        connection.close();
    }
}
