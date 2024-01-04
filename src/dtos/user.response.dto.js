// 회원가입 dto
export const signinResponseDTO = (user) => {
    console.log("signinResponseDTO clear");
    return {"loginId": user[0].login_id, "name": user[0].pwd, "nick": user[0].nick};
}

// 로그인 dto
export const loginResponseDTO = (user) => {
    console.log("loginResponseDTO clear");
    console.log(user[0].id);
    return {"user_id": user[0].id, "name": user[0].pwd, "nick": user[0].nick};
}


// post 수정 필요
export const postResponseDTO = (review) => {
    console.log("postResponseDTO clear");
    return {"score": review[0].score, "content": review[0].content, "review_image_path": review[0].review_image_path};
}