
INSERT INTO USERS (username, password, mail, main_sector, location, education, age, gender)
VALUES ('Mich', 'Mich', 'Mich', 'Mich', 'Mich', 'Mich', 30, 'MALE'),
       ('asd', 'asd', 'asd', 'asd', 'asd', 'asd', 30, 'FEMALE');

INSERT INTO SALARY_HISTORIES (salary_currency, total_years_of_experience)
VALUES ('USD', 7.0), ('CHF', '8.0'), ('TEST', 1.0);

/*
INSERT INTO ETAPE (recette_id, etape)
VALUES (1, 'Saupoudrer de 2 cuillères de farine. Bien remuer.'),
       (1, 'Ajouter 2 ou 3 verres d''eau, les cubes de bouillon, le vin et remuer. Ajouter de l''eau si nécessaire pour couvrir.'),
       (1, 'Couper les carottes en rondelles et émincer les oignons puis les incorporer à la viande, ainsi que les champignons.'),
       (1, 'Laisser mijoter à feu très doux environ 1h30 à 2h00 en remuant.'),
       (1, 'Si nécessaire, ajouter de l''eau de temps en temps.'),
       (1, 'Dans un bol, bien mélanger la crème fraîche, le jaune d’oeuf et le jus de citron. Ajouter ce mélange au dernier moment, bien remuer et servir tout de suite.');
*/

INSERT INTO SALARY_INFOS (job_level, job_name, base_salary)
VALUES ('JUNIOR', 'Software Engineer', 40000.0);
