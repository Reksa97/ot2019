package reko.telegrambot.dao;

import java.sql.*;
import java.util.*;
import reko.telegrambot.domain.User;

public class UserDao {

    private Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    /**
     * @return All users in database
     */
    public ArrayList<User> findAll() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            users.add(new User(rs.getLong("chat_id"), rs.getString("first_name"), rs.getInt("id")));
        }
        rs.close();
        stmt.close();
        conn.close();

        return users;
    }

    /**
     * Saves user to database
     *
     * @param user User to be saved
     * @return saved User
     * @throws SQLException
     */
    public User save(User user) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (first_name, chat_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getFirstName());
        stmt.setLong(2, user.getChatId());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        user.setId(rs.getInt(1));

        stmt.close();
        conn.close();
        return user;
    }

    /**
     * Gets user from database by chat id
     *
     * @param chatId id of chat
     * @return User or null
     * @throws SQLException
     */
    public User findOne(Long chatId) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE chat_id = (?)");
        stmt.setLong(1, chatId);
        ResultSet rs = stmt.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User(rs.getLong("chat_id"), rs.getString("first_name"), rs.getInt("id"));
        }

        rs.close();
        stmt.close();
        conn.close();

        return user;
    }
}
