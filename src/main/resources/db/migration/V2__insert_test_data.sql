-- =========================
-- USERS
-- =========================
INSERT INTO Users (
    user_id,
    first_name,
    last_name,
    email,
    phone,
    role,
    password_hash
)
VALUES (
           '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4',
           'Іван',
           'Петренко',
           'ivan@example.com',
           '+380501234567',
           'ADMIN',
           '$2a$10$7QJ8K8qYtW8g1sYw6kY7UOQz0lKX2vYp8FZ9dK2J5h3G7c9N2m8eO'
       );

-- =========================
-- FLOWERS
-- =========================
INSERT INTO Flowers (
    flower_id,
    name,
    color,
    price,
    stock_quantity,
    image_path,
    created_by
)
VALUES (
           'a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6',
           'Троянда',
           'червоний',
           50.00,
           100,
           '/images/flowers/rose.png',
           '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'
       );

-- =========================
-- ACCESSORIES
-- =========================
INSERT INTO Accessories (
    accessory_id,
    name,
    type,
    color,
    price,
    stock_quantity,
    image_path
)
VALUES (
           'f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5',
           'Стрічка',
           'DECOR',
           'червоний',
           15.00,
           50,
           '/images/accessories/ribbon.png'
       );

-- =========================
-- BOUQUETS
-- =========================
INSERT INTO Bouquets (
    bouquet_id,
    name,
    description,
    is_custom,
    image_path,
    created_at,
    created_by
)
VALUES (
           'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
           'ROMANTIC',
           'Букет з троянд',
           TRUE,
           '/images/bouquets/romantic.png',
           CURRENT_TIMESTAMP,
           '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'
       );

-- =========================
-- BOUQUET ITEMS
-- =========================
INSERT INTO Bouquet_Items (
    bouquet_item_id,
    bouquet_id,
    flower_id,
    quantity
)
VALUES (
           '5d8f3c21-7a4b-4e91-9f22-1c7a8b2d4e11',
           'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
           'a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6',
           7
       );

-- =========================
-- BOUQUET ACCESSORIES
-- =========================
INSERT INTO Bouquet_Accessories (
    bouquet_accessory_id,
    bouquet_id,
    accessory_id,
    quantity
)
VALUES (
           'f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b44',
           'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4',
           'f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5',
           1
       );