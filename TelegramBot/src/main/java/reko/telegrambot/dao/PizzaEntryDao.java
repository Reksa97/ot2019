package reko.telegrambot.dao;

import java.sql.*;
import java.util.ArrayList;
import reko.telegrambot.domain.PizzaEntry;

public class PizzaEntryDao {

    private Database db;

    public PizzaEntryDao(Database db) {
        this.db = db;
    }

    /**
     * Saves a pizza entry to database
     * 
     * @param pizza PizzaEntry to be saved
     * @return saved PizzaEntry
     * @throws SQLException 
     */
    public PizzaEntry save(PizzaEntry pizza) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO pizza_entries (pizza_name, restaurant_name, date_eaten, user_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, pizza.getPizzaName());
        stmt.setString(2, pizza.getRestaurantName());
        stmt.setDate(3, new Date(pizza.getDateEaten().getTime()));
        stmt.setInt(4, pizza.getUserId());
        
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        pizza.setId(rs.getInt(1));
        
        stmt.close();
        conn.close();
        return pizza;
    }

    /**
     * 
     * 
     * @param id Id of pizza
     * @param userId Id of user trying to delete pizza
     * @return True if deleted, false if not
     * @throws SQLException 
     */
    public boolean delete(Integer id, Integer userId) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM pizza_entries WHERE id = ? AND user_id = ?");
        stmt.setInt(1, id);
        stmt.setInt(2, userId);
        
        int deleted = stmt.executeUpdate();
        stmt.close();
        conn.close();
        
        return deleted != 0;
    }

    /**
     * Gets all pizza entries of a single user
     * 
     * @param key User id
     * @return Users pizza entries
     * @throws SQLException 
     */
    public ArrayList<PizzaEntry> findAllByUserId(Integer key) throws SQLException {
        ArrayList<PizzaEntry> pizzas = new ArrayList<>();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pizza_entries WHERE user_id = (?)");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            pizzas.add(new PizzaEntry(rs.getInt("id"), rs.getString("pizza_name"), rs.getString("restaurant_name"), rs.getDate("date_eaten"), rs.getInt("user_id")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return pizzas;
    }

}
