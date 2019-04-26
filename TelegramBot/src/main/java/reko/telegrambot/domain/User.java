package reko.telegrambot.domain;

public class User {
    private int id;
    private final Long chatId;
    private final String firstName;
    
    public User(Long chatId, String firstName, int id) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
    }
    
    public User(Long chatId, String firstName) {
        this.id = -1;
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
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "(" + this.id + ", " + this.chatId + ", " + this.firstName + ")";
    }
}
