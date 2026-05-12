-- =========================
-- USERS
-- =========================
INSERT INTO Users (user_id,
                   first_name,
                   last_name,
                   email,
                   phone,
                   role,
                   password_hash)
VALUES ('9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4',
        'Іван',
        'Петренко',
        'ivan@example.com',
        '+380501234567',
        'ADMIN',
        '$2a$10$7QJ8K8qYtW8g1sYw6kY7UOQz0lKX2vYp8FZ9dK2J5h3G7c9N2m8eO'),
       ('c8a91f44-2d6b-4f91-8a3c-1f9e7b2d5a11',
        'Марія',
        'Коваль',
        'maria@example.com',
        '+380671112233',
        'CLIENT',
        '$2a$10$kP7H8jL2wQ9mT4sX8yZ1bOeF3cD5aV7uY2N6rH1K8mQ3wT9pL0sXa');

-- =========================
-- FLOWERS
-- =========================
INSERT INTO Flowers (flower_id, name, color, price, stock_quantity, created_by)
VALUES ('a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6', 'Троянда', 'червоний', 50.00, 100,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
       ('b77d1f20-91a3-4c88-8e21-9a2b7c3d5f10', 'Тюльпан', 'жовтий', 30.00, 200,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
       ('c11a9e22-1d7b-4f10-9a88-3c5f91a2e7b1', 'Лілія', 'білий', 60.00, 80,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),

       ('d22b8f33-2e9c-4a11-8d77-4b6c91a3e8c2', 'Півонія', 'рожевий', 80.00, 60,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),

       ('e33c7d44-3f1a-4b22-9c66-5a7d91b4f9d3', 'Гербера', 'помаранчевий', 35.00, 150,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),

       ('f44d6c55-4a2b-4c33-8b55-6b8e91c5a0e4', 'Орхідея', 'фіолетовий', 120.00, 40,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),

       ('a55e5b66-5b3c-4d44-9a44-7c9f91d6b1f5', 'Хризантема', 'жовтий', 25.00, 200,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4');

-- =========================
-- ACCESSORIES
-- =========================
INSERT INTO Accessories (accessory_id, name, type, color, price, stock_quantity)
VALUES ('f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5', 'Стрічка', 'DECOR', 'червоний', 15.00, 50),
       ('d91b2c44-6f10-4e88-8a2c-1b7d9f33a5e2', 'Папір', 'FLOWER_WRAP', 'білий', 10.00, 100),
       ('b11f9a22-1c7d-4a10-9c88-3a5d91e2f7b1', 'Сітка декоративна', 'FLOWER_WRAP', 'золота', 20.00,
        70),
       ('c22e8b33-2d8e-4b11-8d77-4b6c91a3f8c2', 'Крафт-папір', 'FLOWER_WRAP', 'коричневий', 18.00,
        120),
       ('d33f7c44-3e9f-4c22-9c66-5a7d91b4a9d3', 'Сизаль', 'DECOR', 'зелений', 12.00, 90),
       ('e44a6d55-4f1a-4d33-8b55-6b8e91c5b0e4', 'Бант атласний', 'DECOR', 'червоний', 8.00, 200),
       ('f55b5e66-5a2b-4c44-9a44-7c9f91d6c1f5', 'Флористична сітка', 'FLOWER_WRAP', 'білий', 22.00,
        60);

-- =========================
-- BOUQUETS
-- =========================
INSERT INTO Bouquets (bouquet_id, name, description, is_custom, created_at, created_by)
VALUES ('e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4', 'ROMANTIC', 'Букет з троянд і тюльпанів', TRUE,
        CURRENT_TIMESTAMP,
        '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4');

-- =========================
-- BOUQUET ITEMS (FLOWERS)
-- =========================
INSERT INTO Bouquet_Items (bouquet_item_id,
                           bouquet_id,
                           flower_id,
                           quantity)
VALUES ('5d8f3c21-7a4b-4e91-9f22-1c7a8b2d4e11',
        'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
        'a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6',
        5),
       ('8c2a91f4-3d7b-46e1-8f10-2b7d9c5a1e33',
        'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
        'b77d1f20-91a3-4c88-8e21-9a2b7c3d5f10',
        3);

-- =========================
-- BOUQUET ACCESSORIES
-- =========================
INSERT INTO Bouquet_Accessories (bouquet_accessory_id,
                                 bouquet_id,
                                 accessory_id,
                                 quantity)
VALUES ('f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b44',
        'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
        'f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5',
        1),
       ('b7d2e44f-1c9a-48e3-8f21-6a3d9c2b5e10',
        'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
        'd91b2c44-6f10-4e88-8a2c-1b7d9f33a5e2',
        1);

-- =========================
-- ORDERS
-- =========================
INSERT INTO Orders (order_id, user_id, customer_first_name, customer_last_name, phone, budget,
                    style, preferred_color)
VALUES ('7c91a2f4-3d8e-4b10-9a77-1f2c9e33b6d8',
        'c8a91f44-2d6b-4f91-8a3c-1f9e7b2d5a11',
        'Марія',
        'Коваль',
        '+380671112233',
        500.00,
        'романтичний',
        'червоний');

-- =========================
-- ORDER ITEMS
-- =========================
INSERT INTO Order_Items (order_item_id,
                         order_id,
                         item_type,
                         flower_id,
                         bouquet_id,
                         accessory_id,
                         quantity,
                         price_snapshot)
VALUES ('3a91f2c8-7d10-4e33-9b88-2f1a7c9d5e11',
        '7c91a2f4-3d8e-4b10-9a77-1f2c9e33b6d8',
        'bouquet',
        NULL,
        'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
        'f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5',
        1,
        300.00);