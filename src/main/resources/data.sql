INSERT INTO USER (username, validated, password, mail, main_sector, location, education, age, gender)
VALUES ('Crystalis', false, 'pw1', 'test@gmail.com', 'IT', 'Paris', 'Masters', 24, 'Male'),
       ('Random', false, 'pw2', 'test2@gmail.com', 'Economics', 'London', 'No education', 34, 'Female');

INSERT INTO SALARY_HISTORY (user_id, salary_currency, total_years_of_experience)
VALUES (1, '$', 7.0),
       (2, '€', '8.0');

INSERT INTO SALARY_INFO (job_level, job_name, base_salary)
VALUES ('Junior', 'Software Engineer', 40000.0),
       ('Junior', 'Software Engineer', 40000.0);

INSERT INTO COMPANY (name, sector, size)
VALUES ('Company1', 'IT', '50000'),
       ('Company2', 'FASTFOOD', '25000');
