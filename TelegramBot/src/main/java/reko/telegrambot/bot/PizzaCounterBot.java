package reko.telegrambot.bot;

import java.util.ArrayList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reko.telegrambot.dao.Database;
import reko.telegrambot.dao.PizzaEntryDao;
import reko.telegrambot.dao.UserDao;
import reko.telegrambot.domain.InputHandler;
import reko.telegrambot.domain.User;

/**
 * This class is used for communicating with Telegram api.
 * 
 * @author xrexrexr
 */
public class PizzaCounterBot extends TelegramLongPollingBot {

    private InputHandler cmdHandler;
    private ArrayList<User> users;
    private Database db;
    private UserDao userDao;
    private PizzaEntryDao pizzaEntryDao;

    /**
     * Constructs a pizza counter bot. Gets a connection to database, and gets 
     * the users in it.
     */
    public PizzaCounterBot() {
        this.users = new ArrayList<>();
        try {
            this.db = new Database();
            this.userDao = new UserDao(this.db);
            this.users = this.userDao.findAll();
            System.out.println("Connected to database " + this.db.getDbUrl());
            System.out.println("Got " + this.users.size() + " users from database");
            System.out.println("Ready to receive messages");
            
            this.pizzaEntryDao = new PizzaEntryDao(this.db);
            this.cmdHandler = new InputHandler(pizzaEntryDao);
        } catch (Exception e) {
            System.out.println("Couldn't connect to database");
            System.out.println("App doesn't work properly, exiting program");
            System.exit(1);
        }
        
    }

    /**
     * Adds user to a private array used by the bot.
     * 
     * @param user The user to be added
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Handles the messages sent to the bot
     * 
     * @param update Contains info of sender and the message
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String input = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            String userFirstName = update.getMessage().getFrom().getFirstName();
            User user = null;
            for (User u : this.users) {
                if (u.getChatId().equals(chatId)) {
                    user = u;
                    System.out.print("Found user: ");
                }
            }
            if (user == null) {
                try {
                    user = new User(chatId, userFirstName);
                    user = this.userDao.save(user);
                    this.users.add(user);
                    System.out.print("New user: ");
                } catch (Exception e) {
                    System.out.println("Couldn't save user");
                }
            }

            this.cmdHandler.handleInput(input, user, this);
        }
    }

    /**
     * Sends a message to a specific chat
     * 
     * @param text Message to be sent
     * @param chatId Message is sent to this chat
     */
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

    /**
     * @return Bot username which is specified in config.properties
     */
    @Override
    public String getBotUsername() {
        return this.db.getBotName();
    }

    /**
     * @return Bot token which is specified in config.properties
     */
    @Override
    public String getBotToken() {
        return this.db.getBotToken();
    }
    
    /**
     * @return Instance of PizzaEntryDao which is used by the bot
     */
    public PizzaEntryDao getPizzaEntryDao() {
        return this.pizzaEntryDao;
    }

    /**
     * 
     * @return Instance of UserDao used by the bot
     */
    public UserDao getUserDao() {
        return this.userDao;
    }
}
