package Client;

// 게임방에 들어갔을 때, 리스너로 등록하고, IngameGui에 플레이어 번호를 세팅하게 만드는 리스너 인터페이스
public interface PlayerOrderListener {
    void onPlayerOrderUpdated(int playerOrder);
}