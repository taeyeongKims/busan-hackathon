import {postResponseDTO } from "./../dtos/user.response.dto.js";
import {getPostfromTag  } from "./../models/user.dao.js";


export const getLawPost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.lawTag;
    return await getPostfromTag(tag);
}

export const getInteriorPost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.interiorTag;
    return await getPostfromTag(tag);
}


export const getCookPost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.cookTag;
    return await getPostfromTag(tag);
}

export const getCleanPost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.cleanTag;
    return await getPostfromTag(tag);
}

export const getStoragePost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.storageTag;
    return await getPostfromTag(tag);
}
