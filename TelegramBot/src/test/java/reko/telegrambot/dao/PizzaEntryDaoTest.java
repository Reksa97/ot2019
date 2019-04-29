/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reko.telegrambot.dao;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import reko.telegrambot.domain.PizzaEntry;

/**
 *
 * @author xrexrexr
 */
public class PizzaEntryDaoTest {

    private Database db;
    private PizzaEntryDao dao;

    @Before
    public void setUp() {
        this.db = new Database("jdbc:sqlite:test.db");
        this.dao = new PizzaEntryDao(db);
    }

    @Test
    public void editingNonExistentPizzaReturnsFalse() {
        PizzaEntry pizza = new PizzaEntry(1, "pizza", "restaurant", new Date(), 1);
        try {
            assertFalse(dao.editPizzaEntry(pizza, 2));
        } catch (Exception e) {
            fail("editing pizza not in db should not throw an error");
        }

    }
}
