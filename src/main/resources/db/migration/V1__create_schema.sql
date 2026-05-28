-- НФ: 3NF
-- Тип: сильна сутність
CREATE TABLE Users (
                       user_id CHAR(36) PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       phone VARCHAR(20),
                       role VARCHAR(50) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- НФ: 3NF
-- Тип: сильна сутність
CREATE TABLE Flowers (
                         flower_id CHAR(36) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         color VARCHAR(50),
                         price DECIMAL(10,2) NOT NULL,
                         stock_quantity INT DEFAULT 0,
                         image_path VARCHAR(255),
                         created_by CHAR(36),
                         FOREIGN KEY (created_by) REFERENCES Users(user_id)
);

-- НФ: 3NF
-- Тип: сильна сутність
CREATE TABLE Accessories (
                             accessory_id CHAR(36) PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             type VARCHAR(50),
                             color VARCHAR(50),
                             price DECIMAL(10,2),
                             stock_quantity INT DEFAULT 0,
                             image_path VARCHAR(255)
);

-- НФ: 3NF
-- Тип: сильна сутність
CREATE TABLE Bouquets (
                          bouquet_id CHAR(36) PRIMARY KEY,
                          name VARCHAR(100),
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          is_custom BOOLEAN DEFAULT FALSE,
                          image_path VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          created_by CHAR(36),
                          FOREIGN KEY (created_by) REFERENCES Users(user_id)
);

-- НФ: 3NF
-- Тип: сильна сутність
CREATE TABLE Orders (
                        order_id CHAR(36) PRIMARY KEY,
                        user_id CHAR(36),
                        customer_first_name VARCHAR(50),
                        customer_last_name VARCHAR(50),
                        phone VARCHAR(20),
                        budget DECIMAL(10,2),
                        style VARCHAR(50),
                        preferred_color VARCHAR(50),
                        status VARCHAR(30) DEFAULT 'NEW',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- НФ: 3NF
-- Тип: слабка сутність (M:N)
CREATE TABLE Bouquet_Items (
                               bouquet_item_id CHAR(36) PRIMARY KEY,
                               bouquet_id CHAR(36),
                               flower_id CHAR(36),
                               quantity INT NOT NULL,
                               FOREIGN KEY (bouquet_id) REFERENCES Bouquets(bouquet_id),
                               FOREIGN KEY (flower_id) REFERENCES Flowers(flower_id)
);

-- НФ: 3NF
-- Тип: слабка сутність (M:N)
CREATE TABLE Bouquet_Accessories (
                                     bouquet_accessory_id CHAR(36) PRIMARY KEY,
                                     bouquet_id CHAR(36),
                                     accessory_id CHAR(36),
                                     quantity INT NOT NULL,
                                     FOREIGN KEY (bouquet_id) REFERENCES Bouquets(bouquet_id),
                                     FOREIGN KEY (accessory_id) REFERENCES Accessories(accessory_id)
);

-- НФ: 3NF
-- Тип: слабка сутність (поліморфний зв’язок)
CREATE TABLE Order_Items (
                             order_item_id CHAR(36) PRIMARY KEY,
                             order_id CHAR(36) NOT NULL,
                             item_type VARCHAR(20) NOT NULL,
                             flower_id CHAR(36) NULL,
                             bouquet_id CHAR(36) NULL,
                             accessory_id CHAR(36) NULL,
                             quantity INT NOT NULL,
                             price_snapshot DECIMAL(10,2) NOT NULL,
                             workspace_x DOUBLE DEFAULT 0,
                             workspace_y DOUBLE DEFAULT 0,
                             FOREIGN KEY (order_id) REFERENCES Orders(order_id),
                             FOREIGN KEY (flower_id) REFERENCES Flowers(flower_id),
                             FOREIGN KEY (bouquet_id) REFERENCES Bouquets(bouquet_id),
                             FOREIGN KEY (accessory_id) REFERENCES Accessories(accessory_id)
);