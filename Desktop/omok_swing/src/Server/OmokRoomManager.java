package Server;

import java.util.ArrayList;
import java.util.List;

public class OmokRoomManager {

    private List<OmokRoom> roomList;

    public OmokRoomManager() {
        roomList = new ArrayList<>();
    }

    // 새로운 룸 생성
    public void createRoom(String roomName) {
        OmokRoom newRoom = new OmokRoom(roomName);
        roomList.add(newRoom);
    }

    // 룸 이름으로 룸 찾기
    public OmokRoom findRoomByName(String roomName) {
        for (OmokRoom room : roomList) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    // 룸 삭제
    public void removeRoom(String roomName) {
        OmokRoom roomToRemove = findRoomByName(roomName);
        if (roomToRemove != null) {
            roomList.remove(roomToRemove);
        }
    }

    // 현재 존재하는 모든 룸 리스트 반환
    public List<OmokRoom> getAllRooms() {
        return new ArrayList<>(roomList);
    }
}
