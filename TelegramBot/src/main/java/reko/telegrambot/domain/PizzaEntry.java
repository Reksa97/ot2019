package reko.telegrambot.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PizzaEntry {

    private String pizzaName;
    private String restaurantName;
    private Date dateEaten;
    private int userId;
    private int id;

    /**
     * For creating a new PizzaEntry from user input, when id is not known yet
     *
     * @param pizza Name of the pizza
     * @param restaurant Name of the restaurant
     * @param eaten Date when the pizza was eaten
     * @param userId Id of the user who added the pizza entry
     */
    public PizzaEntry(String pizza, String restaurant, Date eaten, int userId) {
        this.pizzaName = pizza;
        this.restaurantName = restaurant;
        this.dateEaten = eaten;
        this.userId = userId;
        this.id = -11;
    }

    /**
     * For fetching pizza entries from database, when you know the id
     *
     * @param id Id of pizza
     * @param pizza Name of the pizza
     * @param restaurant Name of the restaurant
     * @param eaten Date when the pizza was eaten
     * @param userId Id of the user who added the pizza entry
     */
    public PizzaEntry(int id, String pizza, String restaurant, Date eaten, int userId) {
        this.id = id;
        this.pizzaName = pizza;
        this.restaurantName = restaurant;
        this.dateEaten = eaten;
        this.userId = userId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Date getDateEaten() {
        return dateEaten;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setDateEaten(Date dateEaten) {
        this.dateEaten = dateEaten;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Used for formatting PizzaEntry to a message
     *
     * @return
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return "\nname: " + this.pizzaName + "\nrestaurant: " + this.restaurantName + "\neaten on: " + dateFormat.format(this.dateEaten) + "\nid: " + this.id ;
    }

}
