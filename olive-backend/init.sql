CREATE TABLE recipes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    instructions TEXT,
    difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
    creator_name VARCHAR(100),
    created_date DATETIME,
    image_url VARCHAR(512)
);

CREATE TABLE ingredients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL,
    recipe_id BIGINT,
    FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

-- Seed data for recipes
INSERT INTO recipes (title, instructions, difficulty, creator_name, created_date, image_url)
VALUES
('Spaghetti Bolognese', 'Cook pasta and mix with sauce.', 'Easy', 'John Doe', NOW(), 'https://example.com/spaghetti.jpg'),
('Beef Wellington', 'Wrap beef in puff pastry and bake.', 'Hard', 'Jane Smith', NOW(), 'https://example.com/beef.jpg');

-- Seed data for ingredients
INSERT INTO ingredients (ingredient_name, recipe_id)
VALUES
('Spaghetti', 1),
('Tomato Sauce', 1),
('Ground Beef', 1),
('Beef Fillet', 2),
('Puff Pastry', 2),
('Mushroom Duxelles', 2);
