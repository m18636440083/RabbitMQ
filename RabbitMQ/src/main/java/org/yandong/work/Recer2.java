package org.yandong.work;

import com.rabbitmq.client.*;
import org.yandong.util.ConnectionUtil;

import java.io.IOException;

/**
 * 工作队列模式
 * 接收者2
 */
public class Recer2 {
    static int i = 1; // 记录执行次数

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列（此处为消费者，不是声明创建队列，而且获取，二者代码相同）出餐口排队
        channel.queueDeclare("test_work_queue", false, false, false, null);
        // 可以理解为：快递一个一个送，送完一个再送下一个，速度快的送件就多
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("【顾客1】吃掉 " + msg + " ! 共吃【" + i++ + "】串");
                // 撸一会，有延迟
                try {
                    Thread.sleep(900);
                }catch (InterruptedException e){

                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume("test_work_queue", false, consumer);
    }
}
