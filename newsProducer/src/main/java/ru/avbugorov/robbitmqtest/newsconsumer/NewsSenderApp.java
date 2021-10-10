package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import ru.avbugorov.robbitmqtest.domain.newsconnection.ExchangeName;
import ru.avbugorov.robbitmqtest.domain.newsconnection.NewsConnection;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewsSenderApp {
    private NewsConnection newsConnection = new NewsConnection();
    private static NewsConnection newsSendConnection = new NewsConnection();
    private static Channel channel = newsSendConnection.getChannel();


    public static void main(String[] args) {
        try {
            channel.exchangeDeclare(ExchangeName.NEWS_EXCHANGE, BuiltinExchangeType.TOPIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        ImitationSendingNews(scheduledExecutorService);
//            scheduledExecutorService.shutdown();
    }

    private static void ImitationSendingNews(ScheduledExecutorService scheduledExecutorService) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            buildNewsMsg("php.developer.news", "News about PHP");
        }, 10, 5, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            buildNewsMsg("java.forum.news", "News about JAVA");
        }, 3, 5, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            buildNewsMsg("python.news", "News about Python");
        }, 6, 10, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            buildNewsMsg("vb6.news.grandfathers.language", "News about Microsoft Visual Basic 6.0");
        }, 6, 10, TimeUnit.SECONDS);

    }

    private static void buildNewsMsg(String routingKey, String newsMsg) {
        try {
            newsMsg = newsMsg + " in " + Thread.currentThread().getName();
            channel.basicPublish(ExchangeName.NEWS_EXCHANGE, routingKey, null, newsMsg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" [x] Sent '" + routingKey + "':'" + newsMsg + "'");
    }
}

