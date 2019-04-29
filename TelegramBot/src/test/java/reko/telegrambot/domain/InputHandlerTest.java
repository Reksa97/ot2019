package reko.telegrambot.domain;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import reko.telegrambot.bot.PizzaCounterBot;
import reko.telegrambot.dao.Database;
import reko.telegrambot.dao.PizzaEntryDao;
import reko.telegrambot.dao.UserDao;

public class InputHandlerTest {

    private InputHandler handler;
    private User user;
    private User anotherUser;
    private PizzaCounterBot bot;
    private UserDao userDao;
    private PizzaEntryDao pizzaEntryDao;
    private Database db;

    @Before
    public void setUp() throws SQLException {
        
        this.user = new User(1234l, "Jukka");
        this.anotherUser = new User(100l, "Joku muu");
        this.bot = mock(PizzaCounterBot.class);
        
        this.db = new Database("jdbc:sqlite:test.db");

        this.pizzaEntryDao = new PizzaEntryDao(db);
        this.userDao = new UserDao(db);
        
        this.user = userDao.save(user);
        this.anotherUser = userDao.save(anotherUser);
        
        this.handler = new InputHandler(pizzaEntryDao);
    }

    @After
    public void tearDown() throws SQLException {
        File dbFile = new File("test.db");
        dbFile.delete();
    }

    @Test
    public void methodHelpReturnsHelp() {
        String help = handler.help();
        assertTrue(help.contains("list"));
        assertTrue(help.contains("add"));
    }

    @Test
    public void helpSendsHelp() {
        handler.handleInput("help", user, bot);
        verify(bot).sendMessage(handler.help(), user.getChatId());
    }

    @Test
    public void listSendsListToRightChat() {
        handler.handleInput("list", user, bot);
        verify(bot).sendMessage(handler.pizzasToString(new ArrayList<>()), user.getChatId());
    }

    @Test
    public void addRecognizesWrongFormat() {
        handler.handleInput("add pizza, restaurant, 1/1/2019", user, bot);
        verify(bot).sendMessage("To add a pizza use the format 'add pizza name, restaurant, date(dd.mm.yyyy)'",
                user.getChatId());
    }

    @Test
    public void addAddsPizza() {
        String pizzaString = "pizza, restaurant, 1.1.2019";
        handler.handleInput("add " + pizzaString, user, bot);
        PizzaEntry pizza = handler.parsePizzaEntry(pizzaString, user);
        pizza.setId(1);
        ArrayList<PizzaEntry> pizzas = new ArrayList<>();
        pizzas.add(pizza);

        verify(bot).sendMessage(anyString(), anyLong());
        handler.handleInput("list", user, bot);
        verify(bot).sendMessage(handler.pizzasToString(pizzas), user.getChatId());
    }

    @Test
    public void addAddsPizzaWithoutSpecifyingDate() {
        String pizzaString = "pizza, restaurant";
        handler.handleInput("add " + pizzaString, user, bot);
        PizzaEntry pizza = handler.parsePizzaEntry(pizzaString, user);
        pizza.setId(1);
        ArrayList<PizzaEntry> pizzas = new ArrayList<>();
        pizzas.add(pizza);

        verify(bot).sendMessage(anyString(), anyLong());
        handler.handleInput("list", user, bot);
        verify(bot).sendMessage(handler.pizzasToString(pizzas), user.getChatId());
    }

    @Test
    public void nonExistentCommandInformsUser() {
        handler.handleInput("this is not a working command", user, bot);

        verify(bot).sendMessage(anyString(), anyLong());
        verify(bot).sendMessage("Command not recognized, type 'help' for help", user.getChatId());
    }

    @Test
    public void meSendsInfoAboutUser() {
        handler.handleInput("me", user, bot);
        verify(bot).sendMessage(anyString(), anyLong());
        verify(bot).sendMessage(contains(user.getChatId().toString()), eq(user.getChatId()));
    }

    @Test
    public void deleteDeletesPizza() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        verify(bot).sendMessage(contains("1"), eq(user.getChatId()));

        handler.handleInput("delete 1", user, bot);
        verify(bot, atLeastOnce()).sendMessage(contains("Deleted pizza 1"), eq(user.getChatId()));
        
        handler.handleInput("list", user, bot);
        verify(bot, atLeastOnce()).sendMessage("No pizzas found", user.getChatId());

    }
    
    @Test
    public void deleteRecognizesIncorrectParam() {
        handler.handleInput("delete abc", user, bot);
        verify(bot).sendMessage(contains("type 'delete id'"), eq(user.getChatId()));
    }
    
    @Test
    public void cantDeletePizzasYouHaventAdded() throws Exception {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        verify(bot).sendMessage(contains("Added pizza"), eq(user.getChatId()));

        handler.handleInput("delete 1", anotherUser, bot);
        verify(bot).sendMessage(contains("You can only delete pizzas added by you"), eq(anotherUser.getChatId()));
    }
    
    @Test
    public void canEditName() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 name kinkku pizza", user, bot);
        verify(bot).sendMessage(contains("Edited pizza 1"), eq(user.getChatId()));
        handler.handleInput("list", user, bot);
        
        verify(bot).sendMessage(contains("name: kinkku pizza"), eq(user.getChatId()));
    }
    
    
    @Test
    public void canEditRestaurant() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 restaurant barbarossa", user, bot);
        verify(bot).sendMessage(contains("Edited pizza 1"), eq(user.getChatId()));
        handler.handleInput("list", user, bot);
        
        verify(bot).sendMessage(contains("restaurant: barbarossa"), eq(user.getChatId()));
    }
    
    @Test
    public void canEditDate() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 date 5.6.2008", user, bot);
        verify(bot).sendMessage(contains("Edited pizza 1"), eq(user.getChatId()));
        handler.handleInput("list", user, bot);
        
        verify(bot, atLeastOnce()).sendMessage(contains("eaten on: 05.06.2008"), eq(user.getChatId()));
    }
    
    @Test
    public void editRecognizesInvalidDate() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 date 5-6-2008", user, bot);
        verify(bot).sendMessage(contains("'dd.mm.yyyy"), eq(user.getChatId()));
    }
    
    @Test
    public void editRecognizesWrongField() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 asodijasodi kinkku pizza", user, bot);
        
        verify(bot, atLeastOnce()).sendMessage(contains("Field should be"), eq(user.getChatId()));
    }
    
    @Test
    public void editRecognizesInvalidId() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1a name kinkku pizza", user, bot);
        
        verify(bot, atLeastOnce()).sendMessage(contains("To edit a pizza"), eq(user.getChatId()));
    }
    
    @Test
    public void cantEditPizzasYouHaventAdded() {
        handler.handleInput("add margarita, day to day", user, bot);
        verify(bot).sendMessage(contains("margarita"), eq(user.getChatId()));
        handler.handleInput("edit 1 name kinkku pizza", anotherUser, bot);
        
        verify(bot).sendMessage(contains("You can only edit pizzas added by you"), eq(anotherUser.getChatId()));
    }
}
