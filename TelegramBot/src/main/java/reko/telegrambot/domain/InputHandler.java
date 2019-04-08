package reko.telegrambot.domain;

import reko.telegrambot.domain.User;
import reko.telegrambot.domain.PizzaEntry;
import reko.telegrambot.bot.PizzaCounterBot;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputHandler {

    public void handleInput(String input, User user, PizzaCounterBot bot) {
        String[] splitInput = input.split(" ", 2);
        String command = splitInput[0];
        String data = "noData";
        if (splitInput.length == 2) {
            data = splitInput[1];
        }

        switch (command) {
            case "list":
                listUserPizzaEntries(user, bot);
                break;
            case "add":
                addPizzaEntry(data, user, bot);
                break;
            case "help":
                bot.sendMessage(this.help(), user.getChatId());
                break;
            default:
                bot.sendMessage("command not recognized, type 'help' for help", user.getChatId());
        }
    }

    public void listUserPizzaEntries(User user, PizzaCounterBot bot) {
        String message = user.getPizzaEntries().toString();
        bot.sendMessage(message, user.getChatId());
    }

    public void addPizzaEntry(String data, User user, PizzaCounterBot bot) {
        PizzaEntry pizza = parsePizzaEntry(data);
        if (pizza == null) {
            bot.sendMessage("To add a pizza use the format 'add pizzaname, restaurant, date(dd.mm.yyyy)'", user.getChatId());
        } else {
            user.addPizzaEntry(pizza);
            bot.sendMessage("added pizza: " + pizza.toString(), user.getChatId());
        }
    }

    public PizzaEntry parsePizzaEntry(String data) {
        String[] pizzaData = data.split(", ");
        try {
            String pizzaName = pizzaData[0];
            String restaurantName = pizzaData[1];
            Date dateEaten;
            if (pizzaData.length == 2) {
                dateEaten = new Date();
            } else {
                dateEaten = new SimpleDateFormat("dd.mm.yyyy").parse(pizzaData[2]);
            }
            return new PizzaEntry(pizzaName, restaurantName, dateEaten);

        } catch (Exception e) {
            System.out.println("could not parse data");
            return null;
        }
    }
    
    public String help() {
        return "List all your pizzas by typing 'list'\n"
                + "Add a pizza by typing 'add pizzaname, restaurant, date(dd.mm.yyyy)', no date means current date";
    }
}
