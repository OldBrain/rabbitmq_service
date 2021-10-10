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

//        routingKeyList.add("prog.java");
        subscriptionMenu();
//        routingKeyList.add();
        try {
            newsListener.subscribe(routingKeyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("NewsClient start ok!");
    }

    private static void subscriptionMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите команду 'set_topic <key>, где <key> раздел новостей например  <<set_topic php>> ");
        String key = scanner.nextLine();

        System.out.println(key.split(" ")[0]);
        System.out.println(key.split(" ")[1]);


        if (key.split(" ")[0].equals("set_topic")) {
            routingKeyList.add(key.split("_")[1]);
        }
        if (key.split(" ")[1].equals("del_topic")) {
            routingKeyList.remove(key.split("_")[1]);
            newsListener.unsubscribe(key.split("_")[1]);
        }

//        routingKeyList.add("prog.java");

    }
}
