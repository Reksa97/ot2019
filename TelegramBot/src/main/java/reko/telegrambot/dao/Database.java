package reko.telegrambot.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String botToken;
    private String botName;
    
    public Database() throws Exception {
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        
        this.dbUrl = prop.getProperty("db");
        this.dbUser = prop.getProperty("dbuser");
        this.dbPassword = prop.getProperty("dbpassword");
        this.botToken = prop.getProperty("bottoken");
        this.botName = prop.getProperty("botname");

        input.close();
                
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }
    
    public String getBotToken() {
        return this.botToken;
    }
    
    public String getBotName() {
        return this.botName;
    }
    
}
