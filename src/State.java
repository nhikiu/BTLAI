import java.util.ArrayList;

public interface State {
    //Kiểm tra xem trạng thái hiện tại có phải là một trạng thái đích hay không.
    boolean isGoal();

    //Tạo ra các trạng thái kế tiếp từ trạng thái hiện tại.
    ArrayList<State> genSuccessors();

    // Xác định chi phí từ trạng thái ban đầu đến trạng thái hiện tại.
    double findCost();

    // In ra trạng thái hiện tại
    void printState();

    // So sánh dữ liệu trạng thái thực tế của đối tượng này với một đối tượng trạng thái khác.
    boolean equals(State s);
}
