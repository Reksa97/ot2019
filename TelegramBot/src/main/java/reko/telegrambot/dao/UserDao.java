
package reko.telegrambot.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import reko.telegrambot.domain.User;

public class UserDao {
    private Database db;
    
    public UserDao(Database db) {
        this.db = db;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection conn = this.db.getConnection();
            User user = new User(1234l, "name");
            users.add(user);
            System.out.println("got connection");
        } catch (SQLException ex) {
            System.out.println("Couldn't get connection");
        }
        return users;
    }
}
