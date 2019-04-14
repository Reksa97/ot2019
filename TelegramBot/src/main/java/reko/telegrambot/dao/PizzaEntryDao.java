package reko.telegrambot.dao;

import java.sql.*;
import java.util.ArrayList;
import reko.telegrambot.domain.PizzaEntry;

public class PizzaEntryDao implements Dao<PizzaEntry, Long> {

    private Database db;

    public PizzaEntryDao(Database db) {
        this.db = db;
    }

    @Override
    public PizzaEntry findOne(Long key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PizzaEntry> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PizzaEntry save(PizzaEntry pizza) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO pizza_entries (pizza_name, restaurant_name, date_eaten, user_id) VALUES (?, ?, ?, ?)");
        stmt.setString(1, pizza.getPizzaName());
        stmt.setString(2, pizza.getRestaurantName());
        stmt.setDate(3, new Date(pizza.getDateEaten().getTime()));
        stmt.setInt(4, pizza.getUserId());
        
        stmt.executeUpdate();
        stmt.close();
        conn.close();
        return pizza;
    }

    @Override
    public void delete(Long key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<PizzaEntry> findAllByUserId(int key) throws SQLException {
        ArrayList<PizzaEntry> pizzas = new ArrayList<>();
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pizza_entries WHERE user_id = (?)");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            pizzas.add(new PizzaEntry(rs.getString("pizza_name"), rs.getString("restaurant_name"), rs.getDate("date_eaten"), rs.getInt("user_id")));
        }
        rs.close();
        stmt.close();
        conn.close();
        return pizzas;
    }

}
