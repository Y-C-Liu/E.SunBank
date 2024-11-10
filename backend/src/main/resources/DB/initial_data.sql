INSERT INTO user (user_name, email, password, biography, phone_number) VALUES
('Alice', 'alice@example.com', 'hashed_password_1', 'Hello, I am Alice', '1234567890'),
('Bob', 'bob@example.com', 'hashed_password_2', 'Hey, this is Bob', '0987654321');

INSERT INTO post (user_id, content, image) VALUES
(1, 'Alice的第一篇帖子', 'https://example.com/image1.jpg'),
(2, 'Bob的第一篇帖子', 'https://example.com/image2.jpg');

INSERT INTO comment (post_id, user_id, content) VALUES
(1, 2, '这是Bob对Alice帖子的评论'),
(2, 1, '这是Alice对Bob帖子的评论');