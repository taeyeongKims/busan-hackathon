import { response } from '../../config/response.js';
import { status } from '../../config/response.status.js';
import { StatusCodes } from "http-status-codes";
import { joinUser, loginUser,  AddLaw, AddInterior, AddCook, AddClean, AddStorage } from './../services/user.service.js';
import { getLawPost, getInteriorPost, getCookPost, getCleanPost, getStoragePost } from './../providers/user.provider.js';

export const userSignin = async (req, res, next) => {
    const signIn = req.body;
    console.log("회원가입을 요청하였습니다!");
    console.log("body:", signIn); // 값이 잘 들어오는지 테스트

    const signIndata = await joinUser(req.body);
    
    if(signIndata == -1){
        console.log("아이디가 중복 됩니다.");
        return res.status(StatusCodes.BAD_REQUEST).json({
            message: "회원가입 실패",
          }); 
    } else {
        console.log("회원 가입 성공");
        return res.status(StatusCodes.OK).json({
            message: "회원가입 성공",
        });
    }
}

export const userLogin = async (req, res, next) => {
    const logIn = req.body;
    console.log("로그인을 요청하였습니다!");
    const loginUserData = await loginUser(logIn);

    console.log(loginUserData);

    if (loginUserData == -1) {
        console.log("존재하지 않는 유저입니다");
        return res.status(StatusCodes.BAD_REQUEST).json({ message: "존재하지 않는 유저입니다" });
    } 
    else if((loginUserData == -2)){
        console.log("비밀번호가 틀렸습니다.");
        return res.status(StatusCodes.BAD_REQUEST).json({ message: "비밀번호가 틀렸습니다." });
    } 
    else {
        console.log("로그인에 성공하였습니다.");
        return res.status(StatusCodes.OK).send(loginUserData);
    }
}

// 법률 게시글 추가
export const userAddLaw = async (req, res, next) => {
    const lawData = req.body;
    console.log("법류 게시글을 작성합니다.");
    res.send(response(status.SUCCESS, await AddLaw(req.params, lawData)));
}

// 인테리어 게시글 추가
export const userAddInterior = async (req, res, next) => {
    const interiorData = req.body;
    console.log("인테리어 게시글을 작성합니다.");
    res.send(response(status.SUCCESS, await AddInterior(req.params, interiorData)));
}

// 요리 게시글 추가
export const userAddCook = async (req, res, next) => {
    const cookData = req.body;
    console.log("요리 게시글을 작성합니다.");
    res.send(response(status.SUCCESS, await AddCook(req.params, cookData)));
}

// 청소 게시글 추가
export const userAddClean = async (req, res, next) => {
    const cleanData = req.body;
    console.log("청소 게시글을 작성합니다.");
    res.send(response(status.SUCCESS, await AddClean(req.params, cleanData)));
}

// 수납 게시글 추가
export const userAddStorage = async (req, res, next) => {
    const storageData = req.body;
    console.log("수납 게시글을 작성합니다.");
    res.send(response(status.SUCCESS, await AddStorage(req.params, storageData)));
}

// 법률 게시글 조회

export const userReadLaw = async (req, res, next) => {
    return res.send(response(status.SUCCESS, await getLawPost(req.params)));
}

// 인테리어 게시글 조회

export const userReadInterior = async (req, res, next) => {
    return res.send(response(status.SUCCESS, await getInteriorPost(req.params)));
}

// 요리 게시글 조회

export const userReadCook = async (req, res, next) => {
    return res.send(response(status.SUCCESS, await getCookPost(req.params)));
}

// 청소 게시글 조회

export const userReadClean = async (req, res, next) => {
    return res.send(response(status.SUCCESS, await getCleanPost(req.params)));
}

// 수납 게시글 조회

export const userReadStorage = async (req, res, next) => {
    return res.send(response(status.SUCCESS, await getStoragePost(req.params)));
}

