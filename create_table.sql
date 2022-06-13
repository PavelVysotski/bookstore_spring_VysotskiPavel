--DROP TABLE IF EXISTS books;
--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS roles;
--DROP TYPE IF EXISTS type_cover;

CREATE TABLE IF NOT EXISTS books(
   id SERIAL PRIMARY KEY,
   isbn VARCHAR (50) UNIQUE NOT NULL,
   title VARCHAR (100) NOT NULL,
   author VARCHAR (50) NOT NULL,
   cover VARCHAR(25) NOT NULL,
   price DECIMAL NOT NULL,
   deleted BOOLEAN DEFAULT false NOT NULL
);

INSERT INTO books (isbn, title, author, cover, price) VALUES ('001-100-1', 'Oliver Twist', 'Charles Dickens', 'HARD', 13.50),
('002-200-2', 'Anne of Green Gables', 'Lucy Montgomery', 'SOFT', 7.34),
('003-300-3', 'To Kill a Mockingbird', 'Harper Lee', 'HARD', 8.56),
('004-400-4', 'One Flew Over the Cuckoo`s Nest', 'Ken Kesey', 'SOFT', 12.44),
('005-500-5', 'And Then There Were None', 'Agatha Christie', 'HARD', 10.12),
('006-600-6', 'The Magician', 'William Somerset', 'SOFT', 11.08),
('007-700-7', 'Lord of the Flies', 'William Golding', 'HARD', 12.12),
('008-800-8', 'An Ideal Husband', 'Oscar Wilde', 'SOFT', 5.18),
('009-900-9', 'The Picture of Dorian Gray', 'Oscar Wilde', 'SOFT', 14.10),
('010-100-10', 'Dumb Witness', 'Agatha Christie', 'HARD', 12.43),
('011-110-11', 'Romeo and Juliet', 'William Shakespeare', 'HARD', 11.74),
('012-120-12', 'Peter Pan', 'James Barrie', 'SOFT', 9.15),
('013-130-13', 'Nightwork', 'Irwin Shaw', 'HARD', 9.24),
('014-140-14', 'The Financier', 'Theodore Dreiser', 'HARD', 8.16),
('015-150-15', 'Titan', 'Theodore Dreiser', 'HARD', 8.30),
('016-160-16', 'The Stoic', 'Theodore Dreiser', 'HARD', 7.34),
('017-170-17', 'Crookes House', 'Agatha Christie', 'SOFT', 7.18),
('018-180-18', 'Dubliners', 'James Joyce', 'SOFT', 10.57),
('019-190-19', 'Dead Souls', 'Nikolai Gogol', 'HARD', 10.78),
('020-200-20', 'The Chimes', 'Charles Dickens', 'SOFT', 12.07),
('021-210-21', 'Mansfield Park', 'Jane Austen', 'HARD', 10.87);

CREATE TABLE IF NOT EXISTS users(
   id SERIAL PRIMARY KEY,
   "name" VARCHAR (50) NOT NULL,
   second_name VARCHAR (50) NOT NULL,
   email VARCHAR (50) UNIQUE NOT NULL,
   "password" VARCHAR (100) UNIQUE NOT NULL,
   "role" VARCHAR(25) NOT NULL,
   activity BOOLEAN DEFAULT true NOT NULL
);

INSERT INTO users (name, second_name, email, password, role) VALUES ('Pavel', 'Vysotski', 'pavel@gmail.com', '+375-29-111-22-33', 'ADMIN'),
('Igor', 'Ignatiev', 'igorIgnatiev@gamail.com', '+375-29-222-33-44', 'MANAGER'),
('Oleg', 'Pavlov', 'olegPavlov@gmail.com', '+375-29-333-44-55', 'CUSTOMER'),
('Aleksandr', 'Minskiy', 'aleksandr@gmail.com', '+375-29-444-55-66', 'MANAGER'),
('Olga', 'Ivanova', 'olgaIvanova@gmail.com', '+375-29-555-66-77', 'MANAGER'),
('Tatiana', 'Sosedova', 'tnyaSosedova@gmail.com', '+375-29-666-77-88', 'CUSTOMER'),
('Slava', 'Pushkin', 's_pushkin@gmail.com', '+375-29-777-88-99', 'CUSTOMER'),
('Vladimir', 'Stolyar', 'stolyarV@mail.ru', '+375-29-888-99-00', 'CUSTOMER'),
('Oksana', 'Pushkina', 'OPushka@mail.ru', '+375-29-999-00-10', 'CUSTOMER'),
('Svetlana', 'Pushkina', 'svetaP@mail.ru', '+375-29-000-11-11', 'CUSTOMER'),
('Sergey', 'Stanilevich', 'stanok@mail.ru', '+375-29-001-12-12', 'CUSTOMER'),
('Olga', 'Pevchih', 'Opevchih@gmail.com', '+375-29-002-13-13', 'CUSTOMER'),
('Tonya', 'Vasilieva', 'vasilieva@mail.ru', '+375-29-003-14-14', 'MANAGER'),
('Tatiana', 'Zayceva', 'zaycevaT@gmail.com', '+375-44-004-15-15', 'MANAGER'),
('Vera', 'Antonova', 'Vantonova@mail.ru', '+375-44-005-16-16', 'CUSTOMER'),
('Veronika', 'Minskaya', 'minskaya@mail.ru', '+375-44-006-17-17', 'CUSTOMER'),
('Olga', 'Ivanova', 'olgaIvanova@mail.ru', '+375-44-007-18-18', 'CUSTOMER'),
('Oleg', 'Pavlov', 'PavlovO@mail.ru', '+375-44-008-19-19', 'CUSTOMER'),
('Aleksandr', 'Starovoitov', 'AleksStar@mail.ru', '+375-44-009-20-20', 'CUSTOMER'),
('Kirill', 'Semenov', 'semenK@gmail.com', '+375-44-010-21-21', 'CUSTOMER'),
('Victor', 'Nosevich', 'nos@mail.ru', '+375-44-011-22-23', 'ADMIN');

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (ID) NOT NULL,
    total_cost DECIMAL NOT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP,
    status VARCHAR(25) NOT NULL
    );

INSERT INTO orders (user_id, total_cost, status) VALUES (3, 30.22, 'RESERVED'),
                                                         (7, 21.74, 'RESERVED'),
                                                        (8, 38.34, 'CONFIRMED'),
                                                        (9, 35.22, 'CONFIRMED'),
                                                        (11, 34.03, 'RESERVED'),
                                                        (12, 10.57, 'RESERVED'),
                                                        (15, 21.56, 'CANCELED'),
                                                        (17, 23.80, 'CONFIRMED');

CREATE TABLE IF NOT EXISTS order_item (
    id SERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders (ID) NOT NULL,
    book_id BIGINT REFERENCES books (ID) NOT NULL,
    quantity BIGINT NOT NULL,
    price DECIMAL NOT NULL
    );

INSERT INTO order_item (order_id, book_id, quantity, price) VALUES (1, 2, 2, 7.34),
                                                         (1, 8, 3, 5.18),
                                                         (2, 21, 2, 10.87),
                                                         (3, 9, 1, 14.10),
                                                         (3, 7, 2, 12.12),
                                                         (4, 11, 3, 11.74),
                                                         (5, 3, 1, 8.56),
                                                         (5, 14, 2, 8.16),
                                                         (5, 12, 1, 9.15),
                                                         (6, 18, 1, 10.57),
                                                         (7, 19, 2, 10.78),
                                                         (8, 14, 1, 8.16),
                                                         (8, 15, 1, 8.30),
                                                         (8, 16, 1, 7.34);
