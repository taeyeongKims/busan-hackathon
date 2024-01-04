import {postResponseDTO } from "./../dtos/user.response.dto.js";
import {getPostfromTag  } from "./../models/user.dao.js";


export const getLawPost = async (params) => {
    console.log(params);
    const user_id  = params.userId;
    const tag  = params.lawTag;
    return postResponseDTO(await getPostfromTag(tag));
}
