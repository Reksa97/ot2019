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

public class InputHandlerTest {

    private InputHandler handler;
    private User user;
    private PizzaCounterBot bot;
    private Database db;

    @Before
    public void setUp() throws SQLException {
        this.handler = new InputHandler();
        this.user = new User(new Long(1234), "Jukka", 1);
        this.bot = mock(PizzaCounterBot.class);
        this.db = new Database("jdbc:sqlite:test.db");
        
        PizzaEntryDao pizzaEntryDao = new PizzaEntryDao(db);
        when(this.bot.getPizzaEntryDao()).thenReturn(pizzaEntryDao);
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
        verify(bot).sendMessage("To add a pizza use the format 'add pizzaname, restaurant, date(dd.mm.yyyy)'",
                user.getChatId());
    }

    @Test
    public void addAddsPizza() {
        String pizzaString = "pizza, restaurant, 1.1.2019";
        handler.handleInput("add " + pizzaString, user, bot);
        PizzaEntry pizza = handler.parsePizzaEntry(pizzaString, user);
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
}
