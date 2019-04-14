CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	chat_id BIGINT,
	first_name VARCHAR(64) 
);

CREATE TABLE pizza_entries (
	id SERIAL PRIMARY KEY,
	pizza_name VARCHAR(64),
	restaurant_name VARCHAR(64),
	date_eaten DATE,
	user_id INT,
	FOREIGN KEY (user_id) REFERENCES users(id)
);
