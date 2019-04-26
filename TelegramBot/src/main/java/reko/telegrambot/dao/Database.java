package reko.telegrambot.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Used for initializing a database and loading properties from config.properties
 * 
 * @author xrexrexr
 */
public class Database {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String botToken;
    private String botName;

    /**
     * Tries to load config.properties. If database url is not defined, uses a local 
     * sqlite database
     */
    public Database() {
        try {
            loadPropertiesFromConfig();
        } catch (Exception e) {
            this.dbPassword = null;
            this.dbUser = null;
        }
        
        if (this.dbUrl == null) {
            this.dbUrl = "jdbc:sqlite:database.db";
            System.out.println("Failed to load config.properties\nUsing default db (" + this.dbUrl + ")");
        }

        init();
    }

    /**
     * Loads properties inside config.properties to private variables.
     * 
     * @throws Exception If something goes wrong while loading (e.g. file doesn't exist)
     */
    private void loadPropertiesFromConfig() throws Exception {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream("config.properties");

        prop.load(input);
        this.botToken = prop.getProperty("bottoken");
        this.botName = prop.getProperty("botname");
        this.dbUrl = prop.getProperty("db");
        this.dbUser = prop.getProperty("dbuser");
        this.dbPassword = prop.getProperty("dbpassword");

        input.close();
    }

    /**
     * Creates an instance of Database for a address
     * 
     * @param address Address of the database (e.g. "jdbc:sqlite:database.db")
     */
    public Database(String address) {
        this.dbUrl = address;
        init();
    }

    /**
     * Returns a connection to the database. If dbUser or dbPassword are not defined 
     * it tries to get connection without them.
     * 
     * @return Connection to database
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        if (this.dbUser == null || this.dbPassword == null) {
            return DriverManager.getConnection(this.dbUrl);
        }
        return DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }

    /**
     * Tries to initialize database and create tables.
     */
    private void init() {
        try {
            Connection conn = getConnection();
            PreparedStatement initUsers = conn.prepareStatement(createTableUsers);
            initUsers.execute();
            initUsers.close();

            PreparedStatement initPizzaEntries = conn.prepareStatement(createTablePizzaEntries);
            initPizzaEntries.execute();
            initPizzaEntries.close();
        } catch (SQLException e) {
            System.out.println("Failed to initialize database");
        }
    }
    
    private String createTableUsers = "CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL PRIMARY KEY,"
                    + "chat_id BIGINT,"
                    + "first_name VARCHAR(64)"
                    + ");";
    
    private String createTablePizzaEntries = "CREATE TABLE IF NOT EXISTS pizza_entries ("
                    + "	id SERIAL PRIMARY KEY,"
                    + "	pizza_name VARCHAR(64),"
                    + "	restaurant_name VARCHAR(64),"
                    + "	date_eaten DATE,"
                    + "	user_id INT,"
                    + "	FOREIGN KEY (user_id) REFERENCES users(id)"
                    + ");";

    public String getBotToken() {
        return this.botToken;
    }

    public String getBotName() {
        return this.botName;
    }
    
    public String getDbUrl() {
        return this.dbUrl;
    }

}
