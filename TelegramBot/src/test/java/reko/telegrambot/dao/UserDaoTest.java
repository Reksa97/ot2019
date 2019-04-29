/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reko.telegrambot.dao;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import reko.telegrambot.domain.User;

/**
 *
 * @author xrexrexr
 */
public class UserDaoTest {

    private Database db;
    private UserDao dao;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        user1 = new User(111l, "eka");
        user2 = new User(222l, "toka");
        user3 = new User(333l, "kolmas");

        this.db = new Database("jdbc:sqlite:test.db");
        this.dao = new UserDao(db);
    }
    
    @After
    public void tearDown() throws SQLException {
        File dbFile = new File("test.db");
        dbFile.delete();
    }

    @Test
    public void canSaveAndFindUser() {
        try {
            user1 = dao.save(user1);
            assertEquals(1, user1.getId());
            
            User found = dao.findOne(user1.getChatId());
            assertEquals(user1.getChatId(), found.getChatId());
            assertEquals(user1.getId(), found.getId());
            assertEquals(user1.getFirstName(), found.getFirstName());
            
        } catch (Exception e) {
            fail("Saving and finding a user should not throw any exceptions");
        }
    }
    
    @Test
    public void tryToFindNonExistentUser() {
        try {
            User user = dao.findOne(191919l);
            assertEquals(null, user);
        } catch (Exception e) {
            fail("Trying to find nonexistent user shouldn't throw an error");
        }
    }
    
    @Test
    public void findsAllUsersAdded() {
        try {
            user1 = dao.save(user1);
            assertEquals(1, user1.getId());
            user2 = dao.save(user2);
            assertEquals(2, user2.getId());
            user3 = dao.save(user3);
            assertEquals(3, user3.getId());
            
            ArrayList<User> found = dao.findAll();
            assertEquals(3, found.size());
            
        } catch (Exception e) {
            fail("Saving and finding a user should not throw any exceptions");
        }

    }
}
