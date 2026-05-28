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
           'Andrii',
           'Fedelesh',
           'admin@gmail.com',
           '+380501234567',
           'ADMIN',
           '$2a$10$pIzodOhNAmZeQJBFGrhCPud4YVACfB84hFvpg2k4RdDC/BV6/WGkC'
       );

-- =========================
-- FLOWERS
-- =========================
INSERT INTO Flowers (flower_id, name, color, price, stock_quantity, image_path, created_by)
VALUES
    ('a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6', 'Троянда', 'червоний', 50.00, 100, '/images/flowers/rose.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b1', 'Біла троянда', 'білий', 55.00, 80, '/images/flowers/rose_white.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b2', 'Рожевий тюльпан', 'рожевий', 45.00, 120, '/images/flowers/tulip_pink.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b3', 'Біла лілія', 'білий', 75.00, 40, '/images/flowers/lily.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b4', 'Жовта гербера', 'жовтий', 40.00, 90, '/images/flowers/herbera.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b5', 'Фіолетова еустома', 'фіолетовий', 65.00, 60, '/images/flowers/austoma.png', '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4');

-- =========================
-- ACCESSORIES
-- =========================
INSERT INTO Accessories (accessory_id, name, type, color, price, stock_quantity, image_path)
VALUES
    ('f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5', 'Червоний бантик', 'DECOR', 'червоний', 15.00, 50, '/images/accessories/bow_red.png'),
    ('c21a9c10-4b8d-4a33-9c11-7d8e2f91a6b1', 'Білий бантик', 'DECOR', 'білий', 20.00, 50, '/images/accessories/bow_white.png'),
    ('c21a9c10-4b8d-4a33-9c11-7d8e2f91a6b2', 'Рожевий бантик', 'DECOR', 'рожевий', 20.00, 50, '/images/accessories/bow_pink.png');

-- =========================
-- BOUQUETS
-- =========================
INSERT INTO Bouquets (bouquet_id, name, description, price, is_custom, image_path, created_at, created_by)
VALUES
    ('e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4', 'ROMANTIC', 'Букет з червоних троянд', 465.00, FALSE, '/images/bouquets/romantic.png', CURRENT_TIMESTAMP, '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('d11f2a33-8c91-4d77-9b10-2a6f91c3d8e1', 'WHITE_PRESENT', 'Букет білих троянд', 515.00, FALSE, '/images/bouquets/white_present.png', CURRENT_TIMESTAMP, '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('d11f2a33-8c91-4d77-9b10-2a6f91c3d8e2', 'PINK_TULIP', 'Букет рожевих тюльпанів', 515.00, FALSE, '/images/bouquets/pink_tulip.png', CURRENT_TIMESTAMP, '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4'),
    ('d11f2a33-8c91-4d77-9b10-2a6f91c3d8e3', 'PURPLE_AUSTOMA', 'Букет фіолетових еустом', 475.00, FALSE, '/images/bouquets/purple_austoma.png', CURRENT_TIMESTAMP, '9f3c2a11-6d8e-4c5a-9c12-8f1a2b7d91e4');

-- =========================
-- BOUQUET ITEMS
-- =========================
INSERT INTO Bouquet_Items (bouquet_item_id, bouquet_id, flower_id, quantity)
VALUES
    ('e5d8f3c2-1a4b-4e91-9f22-1c7a8b2d4e01', 'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4', 'a11c9e33-7b2d-4f10-9d88-3c5f91a2e7b6', 9),
    ('e5d8f3c2-1a4b-4e91-9f22-1c7a8b2d4e02', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e1', 'b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b1', 9),
    ('e5d8f3c2-1a4b-4e91-9f22-1c7a8b2d4e03', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e2', 'b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b2', 11),
    ('e5d8f3c2-1a4b-4e91-9f22-1c7a8b2d4e04', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e3', 'b12c9e33-7b2d-4f10-9d88-3c5f91a2e7b5', 7);

-- =========================
-- BOUQUET ACCESSORIES
-- =========================
INSERT INTO Bouquet_Accessories (bouquet_accessory_id, bouquet_id, accessory_id, quantity)
VALUES
    ('f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b01', 'e11f2a33-8c91-4d77-9b10-2a6f91c3d8e4', 'f21a9c10-4b8d-4a33-9c11-7d8e2f91a6b5', 1),
    ('f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b02', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e1', 'c21a9c10-4b8d-4a33-9c11-7d8e2f91a6b1', 1),
    ('f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b03', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e2', 'c21a9c10-4b8d-4a33-9c11-7d8e2f91a6b2', 1),
    ('f3a91c22-6d8e-4b10-9c77-1a2f5d8e3b04', 'd11f2a33-8c91-4d77-9b10-2a6f91c3d8e3', 'c21a9c10-4b8d-4a33-9c11-7d8e2f91a6b2', 1);