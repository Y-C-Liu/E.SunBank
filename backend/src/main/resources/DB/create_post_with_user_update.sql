DELIMITER //
CREATE PROCEDURE create_post_with_user_update(
    IN userId BIGINT,
    IN content VARCHAR(255),
    IN image VARCHAR(255)
)
BEGIN
    -- 在 post 表中插入新帖子
    INSERT INTO post (user_id, content, image, created_at)
    VALUES (userId, content, image, NOW());

    -- 可以选择性地更新其他表，例如 user 表（如果需要）
    -- UPDATE user SET last_post_at = NOW() WHERE user_id = userId;
END //
DELIMITER ;
