package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.DeliverCallback;

public class NewsListener {

    public NewsListener() {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            System.out.println(Thread.currentThread().getName());
        };
    }


}
