import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import reko.telegrambot.InputHandler;
import reko.telegrambot.PizzaCounterBot;
import reko.telegrambot.PizzaEntry;
import reko.telegrambot.User;

public class InputHandlerTest {
    private InputHandler handler;
    private User user;
    private PizzaCounterBot bot;
            
    @Before
    public void setUp() {
        this.handler = new InputHandler();
        this.user = new User(new Long(1234), "Jukka");
        this.bot = mock(PizzaCounterBot.class);
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
        verify(bot).sendMessage("[]", user.getChatId());
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
        handler.handleInput("add "+pizzaString, user, bot);
        PizzaEntry pizza = handler.parsePizzaEntry(pizzaString);
        verify(bot).sendMessage(anyString(), anyLong());
        handler.handleInput("list", user, bot);
        verify(bot).sendMessage("["+pizza.toString()+"]", user.getChatId());
    }
}
