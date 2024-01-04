// 회원가입 dto
export const signinResponseDTO = (user) => {
    console.log("signinResponseDTO clear");
    return {"loginId": user[0].login_id, "name": user[0].pwd, "nick": user[0].nick};
}

// 로그인 dto
export const loginResponseDTO = (user) => {
    console.log("loginResponseDTO clear");
    return {"loginId": user[0].login_id, "name": user[0].pwd, "nick": user[0].nick};
}


// post 수정 필요
export const postResponseDTO = (review) => {
    console.log("postResponseDTO clear");
    return {"score": review[0].score, "content": review[0].content, "review_image_path": review[0].review_image_path};
}


export const missionResponseDTO = (mission) => {
    console.log("missionResponseDTO clear");
    return;
}

// 미션 진행 상태 반환
export const missionStatusResponseDTO = (missionStatus) => {
    console.log("missionStatusResponseDTO clear");
    return {"status" : missionStatus};
}

export const previewReviewResponseDTO = (data) => {

    const reviews = [];

    for (let i = 0; i < data.length; i++) {
        reviews.push({
           "user_name": data[i].name,
            "score": data[i].score,
            "review_body": data[i].content,
            "create_date": formatDate(data[i].created_at)
        })
    }
    return {"reviewData": reviews, "cursorId": data[data.length-1].id};
}

// 한국 날짜 형식으로 변경
const formatDate = (date) => {
    return new Intl.DateTimeFormat('kr').format(new Date(date)).replaceAll(" ", "").slice(0, -1);
}

// 특정 가게 미션 조회
export const previewMissionResponseDTO = (data) => {

    const missions = [];

    for (let i = 0; i < data.length; i++) {
        missions.push({
           "store_name": data[i].name,
            "reward": data[i].reward,
            "deadline": data[i].deadline,
            "mission_body": data[i].content,
            "create_date": formatDate(data[i].created_at)
        })
    }
    return {"MissionData": missions, "cursorId": data[data.length-1].id};
}

// 진행중인 미션 조회
export const previewProgressingMissionResponseDTO = (data) => {

    const progressing_missions = [];

    for (let i = 0; i < data.length; i++) {
        progressing_missions.push({
           "store_name": data[i].name,
            "reward": data[i].reward,
            "deadline": data[i].deadline,
            "mission_body": data[i].content,
            "create_date": formatDate(data[i].created_at)
        })
    }
    return {"MissionData": progressing_missions, "cursorId": data[data.length-1].id};
}

