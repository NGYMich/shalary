/*h2*/

DROP TABLE IF EXISTS USERS;

CREATE TABLE IF NOT EXISTS USERS (
    id SERIAL PRIMARY KEY,
    username VARCHAR(150),
    password VARCHAR(100),
    mail VARCHAR(250),
    main_sector VARCHAR(3000),
    location VARCHAR(500),
    education VARCHAR(500),
    age FLOAT,
    gender VARCHAR(20),
);

DROP TABLE IF EXISTS SALARY_HISTORIES;

CREATE TABLE IF NOT EXISTS SALARY_HISTORIES (
    id SERIAL PRIMARY KEY,
    user_id INT,
    salary_currency VARCHAR(10),
    total_years_of_experience FLOAT,
    FOREIGN KEY (user_id) references USERS(id)
);


