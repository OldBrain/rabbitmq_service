package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import ru.avbugorov.robbitmqtest.domain.newsconnection.*;

import java.io.IOException;
import java.util.List;

public class NewsListener {
    private Channel channel;

    public NewsListener(Channel channel) {
        this.channel = channel;
    }

    public void subscribe(List<String> routingKeyList) throws IOException {
        channel.exchangeDeclare(ExchangeName.NEWS_EXCHANGE, BuiltinExchangeType.TOPIC);
        for (String rKey : routingKeyList) {
            channel.queueBind(channel.queueDeclare().getQueue(), ExchangeName.NEWS_EXCHANGE, rKey + ".#");
        }
    }

    public void unsubscribe(String routingKey)  {
        try {
            channel.queueUnbind(channel.queueDeclare().getQueue(), ExchangeName.NEWS_EXCHANGE, routingKey + ".#");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
