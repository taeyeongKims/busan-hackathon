import { BaseError } from "../../config/error.js";
import { status } from "../../config/response.status.js";
import { signinResponseDTO, loginResponseDTO, postResponseDTO} from "./../dtos/user.response.dto.js"
import { addUser, getUser, confirmUser, addLawData, addInteriorData, addCookData, addCleanData, addStorageData } from "../models/user.dao.js";

export const joinUser = async (body) => {

    const joinUserData = await addUser({
        'login_id': body.login_id,
        'pwd': body.pwd,
        'nick': body.nick,
    });
    console.log("joinUserData : " + joinUserData);
    if(joinUserData == -1){
        throw new BaseError(status.ID_ALREADY_EXIST);
    }
    return signinResponseDTO(await getUser(joinUserData));
}

 export const loginUser = async (body) => {
    const loginUserData = await confirmUser({
        'login_id': body.login_id,
        'pwd' : body.pwd
    });
    console.log("loginUserData: " ,loginUserData);

    if (loginUserData == -1) {
        return loginUserData;
    } else if((loginUserData == -2)){
        return loginUserData;
    } else {
        return loginResponseDTO(await getUser(loginUserData));
    }
 }

export const AddLaw = async(body) => {
    const joinLawDataId = await addLawData ({
        'user_id' : body.user_id,
        'title' : body.title,
        'content' : body.content,
        'type' : body.type,
        'image_path' : body.image_path
    });
    return postResponseDTO(await getPost(joinLawDataId));
}

export const AddInterior = async(body) => {
    const joinInteriorDataId = await addInteriorData ({
        'user_id' : body.user_id,
        'title' : body.title,
        'content' : body.content,
        'type' : body.type,
        'image_path' : body.image_path
    });
    return postResponseDTO(await getPost(joinInteriorDataId));
}

export const AddCook = async(body) => {
    const joinCookDataId = await addCookData ({
        'user_id' : body.user_id,
        'title' : body.title,
        'content' : body.content,
        'type' : body.type,
        'image_path' : body.image_path
    });
    return postResponseDTO(await getPost(joinCookDataId));
}

export const AddClean = async(body) => {
    const joinCleanDataId = await addCleanData ({
        'user_id' : body.user_id,
        'title' : body.title,
        'content' : body.content,
        'type' : body.type,
        'image_path' : body.image_path
    });
    return postResponseDTO(await getPost(joinCleanDataId));
}

export const AddStorage = async(body) => {
    const joinStorageDataId = await addStorageData ({
        'user_id' : body.user_id,
        'title' : body.title,
        'content' : body.content,
        'type' : body.type,
        'image_path' : body.image_path
    });
    return postResponseDTO(await getPost(joinStorageDataId));
}