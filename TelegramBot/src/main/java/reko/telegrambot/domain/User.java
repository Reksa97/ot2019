package reko.telegrambot.domain;

import java.util.ArrayList;

public class User {
    private final int id;
    private final Long chatId;
    private final String firstName;
    
    public User(Long chatId, String firstName, int id) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return "" + this.chatId + ", " + this.firstName;
    }
    
}
