CREATE TABLE IF NOT EXISTS "user_entity" (
    id SERIAL PRIMARY KEY,
    validated BOOLEAN,
    username VARCHAR(150),
    password VARCHAR(100),
    mail VARCHAR(250),
    main_sector VARCHAR(3000),
    location VARCHAR(500),
    city VARCHAR(500),
    education VARCHAR(500),
    age FLOAT,
    gender VARCHAR(20),
    comment VARCHAR(10000),
    last_update_timestamp VARCHAR(200)
);


CREATE TABLE IF NOT EXISTS SALARY_HISTORY (
  id SERIAL PRIMARY KEY,
  user_id INT,
  salary_currency VARCHAR(10),
    total_years_of_experience FLOAT,
    FOREIGN KEY (user_id) references "user_entity"(id)
);


CREATE TABLE IF NOT EXISTS SALARY_INFO (

    id SERIAL PRIMARY KEY,
    salary_history_id INT,
    years_of_experience FLOAT,
    contract_type VARCHAR(150),
    job_level VARCHAR(50),
    job_name VARCHAR(150),
    base_salary FLOAT,
    stock_salary FLOAT,
    bonus_salary FLOAT,
    total_salary FLOAT,
    net_total_salary FLOAT,
    FOREIGN KEY (salary_history_id) references SALARY_HISTORY(id)
    );


CREATE TABLE IF NOT EXISTS COMPANY (
    id SERIAL PRIMARY KEY,
    salary_info_id INT,
    name VARCHAR(150),
    sector VARCHAR(50),
    size VARCHAR(150),
    FOREIGN KEY (salary_info_id) references SALARY_INFO(id)
);
