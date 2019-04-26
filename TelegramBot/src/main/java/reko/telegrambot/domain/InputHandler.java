package reko.telegrambot.domain;

import java.sql.SQLException;
import reko.telegrambot.bot.PizzaCounterBot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InputHandler {

    /**
     * Reads input of user and reacts to it
     * 
     * @param input Message sent by user
     * @param user User which sent the message
     * @param bot Current bot
     */
    public void handleInput(String input, User user, PizzaCounterBot bot) {
        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];
        String params = "noData";
        if (splitInput.length == 2) {
            params = splitInput[1];
        }
        System.out.println(user.toString() + " sent message: " + input);
        switch (command) {
            case "list":
                listUserPizzaEntries(user, bot);
                break;
            case "add":
                addPizzaEntry(params, user, bot);
                break;
            case "help":
                bot.sendMessage(this.help(), user.getChatId());
                break;
            case "me":
                bot.sendMessage("Your chat id: " + user.getChatId() + "\nYour id: " + user.getId() + "\nYour name: " + user.getFirstName(), user.getChatId());
                break;
            default:
                bot.sendMessage("Command not recognized, type 'help' for help", user.getChatId());
        }
    }

    /**
     * Sends pizza entries to user
     * 
     * @param user Receiver of the message
     * @param bot Current bot
     */
    public void listUserPizzaEntries(User user, PizzaCounterBot bot) {
        ArrayList<PizzaEntry> pizzas;
        try {
            pizzas = bot.getPizzaEntryDao().findAllByUserId(user.getId());
            String message = pizzasToString(pizzas);
            bot.sendMessage(message, user.getChatId());
            System.out.println("Listed " + pizzas.size() + " pizzas for user " + user.toString());
            
        } catch (SQLException ex) {
            System.out.println(ex);
            bot.sendMessage("Couldn't get pizzas from database", user.getChatId());
            System.out.println("Couldn't list pizzas for user " + user.toString());
        }
        
    }
    
    /**
     * Formats pizza entries to a single String which can be sent to user
     * 
     * @param pizzas Pizza entries
     * @return String to be sent
     */
    public String pizzasToString(ArrayList<PizzaEntry> pizzas) {
        if (pizzas.isEmpty()) {
            return "No pizzas found";
        } 
        
        String s = "List of pizzas: \n _________\n";
        
        for (PizzaEntry pizza : pizzas) {
            s += pizza.toString() + "\n_________\n";
        }
        
        return s;
    }

    /**
     * Parses a pizza entry from the users message and saves it
     * 
     * @param data Part of the message after the 'add'
     * @param user Owner of the pizza entry
     * @param bot Current bot
     */
    public void addPizzaEntry(String params, User user, PizzaCounterBot bot) {
        PizzaEntry pizza = parsePizzaEntry(params, user);
        if (pizza == null) {
            bot.sendMessage("To add a pizza use the format 'add pizza name, restaurant, date(dd.mm.yyyy)'", user.getChatId());
        } else {
            try {
                bot.getPizzaEntryDao().save(pizza);
                bot.sendMessage("Added pizza: " + pizza.toString(), user.getChatId());
                System.out.println("Added pizza for user " + user.toString());
            } catch (SQLException ex) {
                System.out.println(ex);
                bot.sendMessage("Couldn't save pizza", user.getChatId());
            }
        }
    }

    /**
     * Tries to parse a PizzaEntry from data 
     * 
     * @param data Should be in format "name, restaurant, dd.mm.yyyy" or "name, restaurant"
     * @param user User which wants to add the pizza entry
     * @return PizzaEntry or null
     */
    public PizzaEntry parsePizzaEntry(String params, User user) {
        String[] pizzaParams = params.split(", ");
        try {
            String pizzaName = pizzaParams[0];
            String restaurantName = pizzaParams[1];
            Date dateEaten;
            if (pizzaParams.length == 2) {
                dateEaten = new Date();
            } else {
                dateEaten = new SimpleDateFormat("dd.mm.yyyy").parse(pizzaParams[2]);
            }
            
            return new PizzaEntry(pizzaName, restaurantName, dateEaten, user.getId());

        } catch (Exception e) {
            System.out.println("Could not parse pizza entry");
            return null;
        }
    }
    
    public String help() {
        return "List all your pizzas by typing 'list'\n"
                + "Add a pizza by typing 'add pizzaname, restaurant, date(dd.mm.yyyy)', no date means current date";
    }
}
