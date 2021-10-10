package ru.avbugorov.robbitmqtest.newsconsumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.*;
import ru.avbugorov.robbitmqtest.domain.newsconnection.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class StartNewsClientApp {
    private static NewsConnection newsConnection = new NewsConnection();
    private static Channel channel = newsConnection.getChannel();

    private static List<String> routingKeyList = new LinkedList<>();
    private static NewsListener newsListener = new NewsListener(channel);

    public static void main(String[] args) {


//** Подписчик при запуске должен ввести команду 'set_topic php',
// после чего начнет получать сообщения из
// очереди с соответствующей темой 'php'.
//
// Сделайте возможность подписчикам подписываться и
// отписываться от статей по темам в процессе работы приложения
// *
        while (1<2) {
            subscriptionMenu();
            try {
                newsListener.subscribe(routingKeyList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void subscriptionMenu() {
        String key;


        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите команду 'set_topic <key>, где <key> раздел новостей например  <<set_topic php>> ");
         key = scanner.nextLine();


        if (key.split(" ")[0].equals("set_topic")) {
            String setKey = key.split(" ")[1];
            routingKeyList.add(setKey);
            System.out.println("Вы подписаны на новости >" +setKey);
            System.out.println("Ваш список подписок >" + routingKeyList.toString());
        }
        if (key.split(" ")[0].equals("del_topic")) {
            String delKey = key.split(" ")[1];
            routingKeyList.remove(delKey);
            newsListener.unsubscribe(delKey);
            System.out.println("Вы отписаны от новостей >" +delKey);
            System.out.println("Ваш список подписок >" + routingKeyList.toString());
        }

//        routingKeyList.add("prog.java");

    }
}
