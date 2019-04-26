package reko.telegrambot.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PizzaEntry {

    private String pizzaName;
    private String restaurantName;
    private Date dateEaten;
    private int userId;

    public PizzaEntry(String pizza, String restaurant, Date eaten, int userId) {
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

    /**
     * Used for formatting PizzaEntry to a message
     * 
     * @return 
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return "\nname: " + this.pizzaName + "\nrestaurant: " + this.restaurantName + "\neaten on: " + dateFormat.format(this.dateEaten);
    }

}
