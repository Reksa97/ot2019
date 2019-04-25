package reko.telegrambot.dao;

import java.sql.*;
import java.util.*;
import reko.telegrambot.domain.User;

public class UserDao implements Dao<User, Long> {

    private Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    /**
     * @return All users in database
     */
    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getLong("chat_id"), rs.getString("first_name"), rs.getInt("id")));
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Couldn't get connection (getUsers)");
        }
        return users;
    }

    /**
     * Saves user to database
     * 
     * @param user User to be saved
     * @return saved User
     * @throws SQLException 
     */
    @Override
    public User save(User user) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (first_name, chat_id) VALUES (?, ?)");
        stmt.setString(1, user.getFirstName());
        stmt.setLong(2, user.getChatId());
        stmt.executeUpdate();

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
    @Override
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

    @Override
    public void delete(Long chatId) throws SQLException {
        // TODO
    }
    
    /**
     * Gets user id belonging to the chat id
     * 
     * @param chatId id of chat
     * @return user id
     * @throws SQLException 
     */
    public int findUserId(Long chatId) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE chat_id = (?)");
        stmt.setLong(1, chatId);
        ResultSet rs = stmt.executeQuery();

        int id = -1;
        if (rs.next()) {
            id = rs.getInt("id");
        }

        rs.close();
        stmt.close();
        conn.close();

        return id;
    }
}
