// models/user.dao.js

import { pool } from "../../config/db.config.js";
import { BaseError } from "../../config/error.js";
import { status } from "../../config/response.status.js";
import { confirmId, insertUserSql, getUserID, getUserPassword, getUserNick, getPostID, insertPostSql, getUserIDfromLoginId } from "./../models/user.sql.js";

export const addUser = async (data) => {
    //try{
        const conn = await pool.getConnection();
        console.log("data.login_id " + data.login_id);
        const [confirm] = await pool.query(confirmId, data.login_id);

        if(confirm[0].isExistId){
            conn.release();
            return -1;
        }

        const result = await pool.query(insertUserSql, [data.login_id, data.pwd, data.nick]);

        conn.release();
        return result[0].insertId;
        
    //}catch (err) {
    //    throw new BaseError(status.PARAMETER_IS_WRONG);
    //}
}

export const getUser = async (userId) => {
    try {
        const conn = await pool.getConnection();
        const [user] = await pool.query(getUserID, userId);

        console.log(user);

        if(user.length == 0){
            return -1;
        }

        conn.release();
        return user;
        
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

export const confirmUser = async (data) => {
    try{
        const conn = await pool.getConnection();
        
        const [confirm] = await pool.query(confirmId, data.login_id);
        
        if(confirm[0].isExistId == 0){
            conn.release();
            return -1;
        }
        
        const [pwd] = await pool.query(getUserPassword, data.login_id);
        conn.release();
        

        if(pwd[0].pwd != data.pwd)
            return -2;
        else {
            const user_id = await pool.query(getUserIDfromLoginId, data.login_id);
            return user_id[0][0].id;
        }
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

// 법률 게시판 추가

export const addLawData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "law";
        
        const result = await pool.query(insertPostSql, [data.title, nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

// 인테리어 게시판 추가

export const addInteriorData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "interior";
        
        const result = await pool.query(insertPostSql, [data.title, nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

// 요리 게시판 추가

export const addCookData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "cook";
        
        const result = await pool.query(insertPostSql, [data.title, nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

// 청소 게시판 추가

export const addCleanData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "clean";
        
        const result = await pool.query(insertPostSql, [data.title, nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

// 수납 게시판 추가

export const addStorageData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "storage";
        
        const result = await pool.query(insertPostSql, [data.title, nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    }catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}


export const getPost = async (postId) => {
    try {
        const conn = await pool.getConnection();
        const [post] = await pool.query(getPostID, postId);

        if(post.length == 0){
            return -1;
        }

        conn.release();
        return post;
        
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

export const addMission = async (data) => {
    try{
        const conn = await pool.getConnection();
        const [confirm] = await pool.query(confirmMission, [data.user_id, data.mission_id]);

        if(confirm[0].isExistMission){
            conn.release();
            return -1;
        }

        const result = await pool.query(insertMissionSql, [data.user_id, data.mission_id, data.status]);
        console.log(10);
        conn.release();
        return result[0].insertId;
        
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

export const getMission = async (missionId) => {
    try {
        const conn = await pool.getConnection();
        const [mission] = await pool.query(getMissionID, missionId);

        console.log(mission);

        if(mission.length == 0){
            return -1;
        }

        conn.release();
        return mission;
        
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}


export const getPreviewReview = async (cursorId, size, userId) => {
    try {
        const conn = await pool.getConnection();
        if(cursorId == "undefined" || typeof cursorId == "undefined" || cursorId == null){
            const [reviews] = await pool.query(getReviewByReviewIdAtFirst, [parseInt(userId), parseInt(size)]);
            conn.release();
            return reviews;
        }else{
            const [reviews] = await pool.query(getReviewByReviewId, [parseInt(userId), parseInt(cursorId), parseInt(size)]);
            conn.release();
            return reviews;    
        }
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

export const getPreviewMission = async (cursorId, size, storeId) => {
    try {
        const conn = await pool.getConnection();
        if(cursorId == "undefined" || typeof cursorId == "undefined" || cursorId == null){
            const [missions] = await pool.query(getMissionByMissionIdAtFirst, [parseInt(storeId), parseInt(size)]);
            conn.release();
            return missions;
        }else{
            const [missions] = await pool.query(getMissionByMissionId, [parseInt(storeId), parseInt(cursorId), parseInt(size)]);
            conn.release();
            return missions;    
        }
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}

export const getPreviewProgressingMission = async (cursorId, size, userId) => {
    try {
        const conn = await pool.getConnection();
        if(cursorId == "undefined" || typeof cursorId == "undefined" || cursorId == null){
            const [progressing_missions] = await pool.query(getProgressingMissionByMissionIdAtFirst, [parseInt(userId), parseInt(size)]);
            conn.release();
            return progressing_missions;
        }else{
            const [progressing_missions] = await pool.query(getProgressingMissionByMissionId, [parseInt(userId), parseInt(cursorId), parseInt(size)]);
            conn.release();
            return progressing_missions;    
        }
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}


export const completeMission = async (userId, missionId) => {
    try {
        const conn = await pool.getConnection();
        await conn.query(missionComplete, [parseInt(userId), parseInt(missionId)]);
        const mission_status = await conn.query(missionStatus, [parseInt(userId), parseInt(missionId)]);
        conn.release();
        return mission_status;    
    } catch (err) {
        throw new BaseError(status.PARAMETER_IS_WRONG);
    }
}