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
export const insertPostSql = "INSERT INTO post (title, nick, view, likes, content, review_image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// interior
export const insertInteriorSql = "INSERT INTO post (title, nick, view, likes, content, review_image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// cook
export const insertCookSql = "INSERT INTO post (title, nick, view, likes, content, review_image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// clean
export const insertCleanSql = "INSERT INTO post (title, nick, view, likes, content, review_image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

// storage
export const insertStorageSql = "INSERT INTO post (title, nick, view, likes, content, review_image_path, type) VALUES (?, ?, 0, 0, ?, ?, ?);";

export const getPostID = "SELECT * FROM post WHERE id = ?";



// mission

export const confirmMission = 
"SELECT EXISTS (SELECT 1 FROM user_mission WHERE (user_id = ? AND mission_id = ? AND status = '진행 중')) as isExistMission;";

export const insertMissionSql = "INSERT INTO user_mission (user_id, mission_id, status) VALUES (?, ?, ?);";

export const getMissionID = "SELECT * FROM user_mission WHERE id = ?";


// 리뷰 조회 sql

export const getReviewByReviewId = 
"SELECT u.name, r.id, r.score, r.content, r.created_at "
+ "FROM review r JOIN user u on r.user_id = u.id "
+ "WHERE r.user_id = ? AND r.id < ? "
+ "ORDER BY r.id DESC LIMIT ? ;"

export const getReviewByReviewIdAtFirst = 
"SELECT u.name, r.id, r.score, r.content, r.created_at "
+ "FROM review r JOIN user u on r.user_id = u.id "
+ "WHERE r.user_id = ? "
+ "ORDER BY r.id DESC LIMIT ? ;"

// 특정 가게 미션 조회
export const getMissionByMissionId = 
"SELECT st.name, m.id, m.reward, m.deadline, m.content, m.created_at "
+ "FROM mission m JOIN store st on m.store_id = st.id "
+ "WHERE m.store_id = ? AND m.id < ? "
+ "ORDER BY m.id DESC LIMIT ? ;"

export const getMissionByMissionIdAtFirst = 
"SELECT st.name, m.id, m.reward, m.deadline, m.content, m.created_at "
+ "FROM mission m JOIN store st on m.store_id = st.id "
+ "WHERE m.store_id = ? "
+ "ORDER BY m.id DESC LIMIT ? ;"

// 진행 중인 미션 조회
export const getProgressingMissionByMissionId = 
"SELECT um.id, st.name, m.reward, m.deadline, m.content, m.created_at "
+ "FROM user_mission um JOIN mission m on um.mission_id = m.id JOIN store st on m.store_id = st.id "
+ "WHERE um.user_id = ? AND um.status = '진행 중' AND um.id < ? "
+ "ORDER BY um.id DESC LIMIT ? ;"

export const getProgressingMissionByMissionIdAtFirst = 
"SELECT um.id, st.name, m.reward, m.deadline, m.content, m.created_at "
+ "FROM user_mission um JOIN mission m on um.mission_id = m.id JOIN store st on m.store_id = st.id "
+ "WHERE um.user_id = ? AND um.status = '진행 중' "
+ "ORDER BY um.id DESC LIMIT ? ;"

// 진행 중인 미션 진행 완료로 바꾸기
export const missionComplete = 
"UPDATE user_mission SET status = '진행 완료' WHERE user_id = ? AND mission_id = ?; "


// 미션 진행 상태 조회
export const missionStatus = 
"SELECT um.status "
+ "FROM user_mission as um "
+ "WHERE user_id = ? AND mission_id = ? ;"
