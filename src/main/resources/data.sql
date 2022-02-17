
INSERT INTO USER (username, password, mail, main_sector, location, education, age, gender)
VALUES ('Crystalis', 'pw1', 'test@gmail.com', 'IT', 'Paris', 'Masters', 24, 'MALE'),
       ('Random', 'pw2', 'test2@gmail.com', 'Economics', 'London', 'No education', 34, 'FEMALE');

INSERT INTO SALARY_HISTORY (user_id, salary_currency, total_years_of_experience)
VALUES (1, 'USD', 7.0),
       (2, 'CHF', '8.0');

INSERT INTO SALARY_INFO (job_level, job_name, base_salary)
VALUES ('Junior', 'Software Engineer', 40000.0), ('Junior', 'Software Engineer', 40000.0);
