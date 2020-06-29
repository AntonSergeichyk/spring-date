INSERT INTO company(name)
VALUES ('checkPoint'),
       ('netCracker');

INSERT INTO employee(first_name, second_name, age, company_id)
VALUES ('Petr', 'Kyharenko', 32, (SELECT c.id FROM company c WHERE name = 'checkPoint')),
       ('Anton', 'Sergeichyk', 28, (SELECT c.id FROM company c WHERE name = 'netCracker'));

INSERT INTO address(city, street, employee_id)
VALUES ('Minsk', 'Odincova', (SELECT e.id FROM employee e WHERE first_name = 'Anton')),
       ('Minsk', 'Partizansky_pr', (SELECT e.id FROM employee e WHERE first_name = 'Petr'));