package reko.telegrambot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PizzaEntry {

    private String pizzaName;
    private String restaurantName;
    private Date dateEaten;

    public PizzaEntry(String pizza, String restaurant, Date eaten) {
        this.pizzaName = pizza;
        this.restaurantName = restaurant;
        this.dateEaten = eaten;
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

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setDateEaten(Date dateEaten) {
        this.dateEaten = dateEaten;
    }

    @Override
    public String toString() {
        return this.pizzaName + ", " + this.restaurantName + ", " + this.dateEaten.toString();
    }

}
