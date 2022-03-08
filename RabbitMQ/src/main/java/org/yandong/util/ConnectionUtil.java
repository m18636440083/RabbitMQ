package org.yandong.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 在工厂对象中设置MQ的连接信息，（ip,port,vhost,username,password）

        factory.setHost("192.168.184.128");
        factory.setPort(5672);
        factory.setVirtualHost("/yandong");
        factory.setUsername("yandong");
        factory.setPassword("123456");

        // 通过工厂获得与MQ的连接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = getConnection();
        System.out.println("Connection" + connection);
        connection.close();
    }
}
