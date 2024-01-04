// models/user.sql.js

// signIn, login
export const insertUserSql = "INSERT INTO user (login_id, pwd, nick) VALUES (?, ?, ?);";

export const getUserID = "SELECT * FROM user WHERE id = ?";

export const getUserIDfromLoginId = "SELECT id FROM user WHERE login_id = ?";

export const confirmId = "SELECT EXISTS(SELECT 1 FROM user WHERE login_id = ?) as isExistId";

export const getUserPassword = "SELECT pwd FROM user WHERE login_id = ?";


// nick 조회
export const getUserNick = "SELECT nick FROM user WHERE id = ?"

// law
export const insertPostSql = "INSERT INTO post (title, nick, view, likes, content, image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// interior
export const insertInteriorSql = "INSERT INTO post (title, nick, view, likes, content, image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// cook
export const insertCookSql = "INSERT INTO post (title, nick, view, likes, content, image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// clean
export const insertCleanSql = "INSERT INTO post (title, nick, view, likes, content, image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// storage
export const insertStorageSql = "INSERT INTO post (title, nick, view, likes, content, image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

export const getPostID = "SELECT * FROM post WHERE id = ?";

export const getTagId = "SELECT id FROM hash h WHERE tag = ?";

export const getPostIdfromHashId = "SELECT post_id FROM post_hash ph WHERE hash_id = ?";

