// models/user.dao.js

import { pool } from "../../config/db.config.js";
import { BaseError } from "../../config/error.js";
import { status } from "../../config/response.status.js";
import { confirmId, insertUserSql, getUserID, getUserPassword, getUserNick, getPostID, insertPostSql, getUserIDfromLoginId,
    getPostIdfromHashId, getTagId } from "./../models/user.sql.js";

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
    //try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
    
        const type = "law";
        
        const result = await pool.query(insertPostSql, [data.title, nick[0][0].nick, data.content, data.image_path, type]);
       
        conn.release();
        return result[0].insertId;
        
    //}catch (err) {
    //    throw new BaseError(status.PARAMETER_IS_WRONG);
    //}
}

// 인테리어 게시판 추가

export const addInteriorData = async (data) => {
    try{
        const conn = await pool.getConnection();

        const nick = await pool.query(getUserNick, data.user_id);
        const type = "interior";
        
        const result = await pool.query(insertPostSql, [data.title, nick[0][0].nick, data.content, data.image_path, type]);
       
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
        
        const result = await pool.query(insertPostSql, [data.title, nick[0][0].nick, data.content, data.image_path, type]);
       
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
        
        const result = await pool.query(insertPostSql, [data.title, nick[0][0].nick, data.content, data.image_path, type]);
       
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
        
        const result = await pool.query(insertPostSql, [data.title, nick[0][0].nick, data.content, data.image_path, type]);
       
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

export const getPostfromTag = async (tag) => {
    //try {
        const conn = await pool.getConnection();
        console.log(tag);
        const tagId = await pool.query(getTagId, tag);
        const postId = await pool.query(getPostIdfromHashId, tagId[0][0].id);

        const posts = [];

        for (let i = 0; i < postId.length; i++) {
        const post = await pool.query(getPostID, postId[0][i].post_id);
        posts.push(post[0][0]);
        }


        if(posts.length == 0){
            return -1;
        }

        conn.release();
        return posts;
        
    //} catch (err) {
    //   throw new BaseError(status.PARAMETER_IS_WRONG);
    //}
}