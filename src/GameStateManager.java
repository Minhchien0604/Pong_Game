public class GameStateManager {
    private static GameState currentState = GameState.MENU;
    // Biến tĩnh (dùng chung cho cả chương trình) lưu trạng thái hiện tại là MENU
    public  static GameState getState (){ // Hàm này cho phép lấy trạng thái từ bên ngoài
        return currentState;
    }

    public static void changeState (GameState newState){ // Dùng để thay đổi trạng thái mới.
        currentState = newState;
    }
}
