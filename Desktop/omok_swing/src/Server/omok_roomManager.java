package Server;

import java.util.ArrayList;
import java.util.List;

public class omok_roomManager {

    private List<omok_room> roomList;

    public omok_roomManager() {
        roomList = new ArrayList<>();
    }

    // 새로운 룸 생성
    public void createRoom(String roomName) {
        omok_room newRoom = new omok_room(roomName);
        roomList.add(newRoom);
    }

    // 룸 이름으로 룸 찾기
    public omok_room findRoomByName(String roomName) {
        for (omok_room room : roomList) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    // 룸 삭제
    public void removeRoom(String roomName) {
        omok_room roomToRemove = findRoomByName(roomName);
        if (roomToRemove != null) {
            roomList.remove(roomToRemove);
        }
    }

    // 현재 존재하는 모든 룸 리스트 반환
    public List<omok_room> getAllRooms() {
        return new ArrayList<>(roomList);
    }
}
