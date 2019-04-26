
package reko.telegrambot.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xrexrexr
 */
public class UserTest {
    
    private User user;
    private Long chatId;
    private String name;
    private int id;
 
    @Before
    public void setUp() {
        this.user = new User(chatId, name, id);
    }

    @Test
    public void getChatIdWorks() {
        assertEquals(chatId, user.getChatId());
    }
    
    @Test
    public void getFirstNameWorks() {
        assertEquals(name, user.getFirstName());
    }
    
    @Test
    public void getIdWorks() {
        assertEquals(id, user.getId());
    }
    
    @Test
    public void setIdWorks() {
        int newId = 100;
        user.setId(newId);
        assertEquals(newId, user.getId());
    }
}
