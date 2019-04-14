package reko.telegrambot.domain;

import java.util.ArrayList;

public class User {
    private final int id;
    private final Long chatId;
    private final String firstName;
    private ArrayList<PizzaEntry> pizzat;
    
    public User(Long chatId, String firstName, int id) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
        this.pizzat = new ArrayList<>();
    }

    public Long getChatId() {
        return chatId;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void addPizzaEntry(PizzaEntry pizza) {
        this.pizzat.add(pizza);
    }
    
    public ArrayList<PizzaEntry> getPizzaEntries() {
        return this.pizzat;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return "" + this.chatId + ", " + this.firstName + ", pizzat: " + this.pizzat.toString();
    }
    
}
