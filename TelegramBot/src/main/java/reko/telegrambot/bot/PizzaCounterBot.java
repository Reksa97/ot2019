package reko.telegrambot.bot;

import java.util.ArrayList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reko.telegrambot.domain.InputHandler;
import reko.telegrambot.domain.User;

public class PizzaCounterBot extends TelegramLongPollingBot {

    private InputHandler cmdHandler;
    private ArrayList<User> users;

    public PizzaCounterBot() {
        this.cmdHandler = new InputHandler();
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String input = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            String userFirstName = update.getMessage().getFrom().getFirstName();
            User user = null;
            for (User u : this.users) {
                if (u.getChatId().equals(chatId)) {
                    System.out.println("found user");
                    user = u;
                }
            }
            if (user == null) {
                user = new User(chatId, userFirstName);
                this.users.add(user);
            }

            this.cmdHandler.handleInput(input, user, this);
        }
    }

    public void sendMessage(String text, Long chatId) {
        try {
            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(text);

            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public String getBotUsername() {
        return "pizza_counter_bot";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}
