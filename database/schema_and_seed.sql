CREATE TABLE IF NOT EXISTS users (
  user_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  email TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
  category_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS expenses (
  expense_id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER,
  category_id INTEGER,
  date TEXT NOT NULL,
  amount REAL NOT NULL,
  note TEXT,
  FOREIGN KEY(user_id) REFERENCES users(user_id),
  FOREIGN KEY(category_id) REFERENCES categories(category_id)
);

INSERT OR IGNORE INTO users(user_id, name, email)
VALUES (1, 'Demo User', 'demo@gmail.com');

INSERT OR IGNORE INTO categories(name) VALUES
('Food'),('Travel'),('Bills'),('Shopping');
