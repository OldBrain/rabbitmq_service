package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import ru.avbugorov.robbitmqtest.domain.newsconnection.*;

import java.io.IOException;
import java.util.Set;

public class NewsSubscribers {
    private Channel channel;
    private String queueName;

    public NewsSubscribers(Channel channel) {

        this.channel = channel;
        try {
            channel.exchangeDeclare(ExchangeName.NEWS_EXCHANGE, BuiltinExchangeType.TOPIC);
            this.queueName = channel.queueDeclare().getQueue();
            newsListener();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void newsListener() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }


    public void subscribe(Set<String> routingKeySet) throws IOException {
        for (String rKey : routingKeySet) {
            channel.queueBind(queueName, ExchangeName.NEWS_EXCHANGE, rKey + ".#");
        }
    }

    public void unsubscribe(String routingKey) {
        try {
            channel.queueUnbind(queueName, ExchangeName.NEWS_EXCHANGE, routingKey + ".#");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
