package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.*;
import ru.avbugorov.robbitmqtest.domain.newsconnection.*;

import java.io.IOException;
import java.util.*;

public class StartNewsReaderClientApp {
    private static NewsConnection newsConnection = new NewsConnection();
    private static Channel channel = newsConnection.getChannel();

    private static List<String> routingKeyList = new LinkedList<>();
    private static Set<String> routingKeySet = new HashSet<>();
    private static NewsSubscribers newsSubscribers = new NewsSubscribers(channel);

    public static void main(String[] args) {


        try {


            while (1 < 2) {
                subscriptionMenu();
                newsSubscribers.subscribe(routingKeySet);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void subscriptionMenu() {
        String key;


        Scanner scanner = new Scanner(System.in);
        System.out.println("1. команда set_topic <key> для подписки");
        System.out.println("2. команда set_topic <key> для отписки");
        System.out.println("3. команда exit для завершения");

        System.out.print("Введите команду например  <<set_topic php>> ");
        key = scanner.nextLine();


        if (key.split(" ")[0].equals("set_topic")) {
            String setKey = key.split(" ")[1];
            routingKeySet.add(setKey);
            System.out.println("Вы подписаны на новости >" + setKey);
            System.out.println("Ваш список подписок >" + routingKeySet.toString());
        }
        if (key.split(" ")[0].equals("del_topic")) {
            String delKey = key.split(" ")[1];
            routingKeySet.remove(delKey);
            newsSubscribers.unsubscribe(delKey);
            System.out.println("Вы отписаны от новостей >" + delKey);
            System.out.println("Ваш список подписок >" + routingKeySet.toString());
        }
        if (key.equals("exit")) {
            System.exit(0);
        }
    }
}
