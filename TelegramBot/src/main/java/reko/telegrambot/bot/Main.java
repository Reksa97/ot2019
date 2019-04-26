package reko.telegrambot.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    /**
     * Registers and starts the Telegram bot
     * 
     * @param args 
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        try {
            botsApi.registerBot(new PizzaCounterBot());
        } catch (TelegramApiException e) {
            System.out.println("Couldn't register bot, check your config.properties file");
        }
    }
}
