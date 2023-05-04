import java.util.ArrayList;
import java.util.Arrays;

/*
Lớp EightPuzzleState định nghĩa trạng thái của bảng trò chơi 8-puzzle,
trong đó bảng được biểu diễn bằng một mảng 1 chiều. Bảng có kích thước 3x3
và có 8 ô còn trống. Trạng thái của bảng được định nghĩa bởi mảng curBoard
và hai giá trị outOfPlace và manDist.
* */
public class EightPuzzleState implements State {
    private final int PUZZLE_SIZE = 9;
    private int outOfPlace = 0; //đếm số lượng ô còn sai vị trí so với trạng thái đích,
    private int manDist = 0; //tổng khoảng cách Manhattan giữa các ô và vị trí của chúng trong trạng thái hiện tại so với trạng thái đích.
    private final int[] GOAL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};

    private int[] curBoard;

    public EightPuzzleState(int[] board) {
        curBoard = board;
        setOutOfPlace();
        setManDist();
    }

    //tính giá trị chi phí để di chuyển đến trạng thái hiện tại từ trạng thái bắt đầu.
    @Override
    public double findCost() {
        int cost = 0;
        for (int i = 0; i < curBoard.length; i++) {
            int goalNumber = GOAL[i] == 0 ? 9 : GOAL[i];
            cost += Math.abs(curBoard[i] - goalNumber);
        }
        return cost;
    }

    //tìm số phần tử của trạng thái hiện tại không đúng so với trạng thái đích
    private void setOutOfPlace() {
        for (int i = 0; i < curBoard.length; i++) {
            if (curBoard[i] != GOAL[i]) {
                outOfPlace++;
            }
        }
    }

    //set khoảng cách manhattan hiện tại
    private void setManDist() {
        // xác định khoảng cách từ trạng thái hiện tại đến trạng thái mục tiêu
        int index = -1;

        /*Phương thức này lặp qua mảng curBoard và tính toán Khoảng cách Manhattan cho mỗi ô không ở đúng vị trí.
        Thuật toán tính toán Khoảng cách Manhattan cho mỗi ô bằng cách tìm sự khác biệt giữa vị trí hiện tại của
        ô và vị trí mục tiêu của nó. Tổng của những khác biệt này được lưu trữ trong biến manDist.*/
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                index++;

                int val = (curBoard[index] - 1);
                /*Mã này sử dụng tìm kiếm tuyến tính để lặp qua mảng curBoard và đối với mỗi ô xếp không
                ở đúng vị trí, mã này sẽ tính toán vị trí mục tiêu của nó bằng cách sử dụng phép chia số
                nguyên và số học mô-đun. Sau đó, nó tìm thấy sự khác biệt giữa vị trí hiện tại của ô và
                vị trí mục tiêu của nó theo cả chiều ngang và chiều dọc bằng cách sử dụng phương thức
                Math.abs() và thêm tổng của những khác biệt này vào manDist.*/
                if (val != -1) {
                    int horiz = val % 3;
                    int vert = val / 3;

                    manDist += Math.abs(vert - (y)) + Math.abs(horiz - (x));
                }
            }
        }
    }

    //tìm chỉ số của ô trống (hole - 0) trên bảng.
    private int getHole() {
        int holeIndex = -1;
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            if (curBoard[i] == 0)
                holeIndex = i;
        }
        return holeIndex;
    }

    public int getOutOfPlace() {
        return outOfPlace;
    }

    public int getManDist() {
        return manDist;
    }

    private int[] copyBoard(int[] state) {
        int[] ret = new int[PUZZLE_SIZE];
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            ret[i] = state[i];
        }
        return ret;
    }

    //tạo ra tất cả các trạng thái kế tiếp từ trạng thái hiện tại
    @Override
    public ArrayList<State> genSuccessors() {
        //tạo và trả về tất cả các trạng thái kế tiếp có thể có của trạng thái hiện tại trong câu đố.
        // Nó thực hiện điều này bằng cách tìm chỉ số của ô vuông trống trên bàn cờ bằng phương thức getHole().
        ArrayList<State> successors = new ArrayList<State>();
        int hole = getHole();

        //ó kiểm tra xem ô trống có thể được di chuyển sang trái, phải, lên hoặc xuống mà
        // không vượt quá giới hạn hay không bằng cách kiểm tra chỉ số của nó dựa trên một
        // tập hợp các điều kiện. Nếu có thể di chuyển, nó hoán đổi hình vuông trống với ô
        // liền kề theo hướng đó bằng cách sử dụng phương thức swapAndStore(), phương thức
        // này tạo ra một trạng thái mới bằng cách sao chép bảng hiện tại và thực hiện hoán đổi.
        // Trạng thái mới này sau đó được thêm vào danh sách những trạng thái kế vị.
        if (hole != 0 && hole != 3 && hole != 6) {
            swapAndStore(hole - 1, hole, successors);
        }

        if (hole != 6 && hole != 7 && hole != 8) {
            swapAndStore(hole + 3, hole, successors);
        }

        if (hole != 0 && hole != 1 && hole != 2) {
            swapAndStore(hole - 3, hole, successors);
        }
        if (hole != 2 && hole != 5 && hole != 8) {
            swapAndStore(hole + 1, hole, successors);
        }

        return successors;
    }

    private void swapAndStore(int d1, int d2, ArrayList<State> s) {
        //tạo một bản sao của bảng hiện tại bằng cách gọi phương thức copyBoard()
        // và lưu trữ nó trong một mảng mới cpy.
        int[] cpy = copyBoard(curBoard);
        //hoán đổi các giá trị tại các chỉ số d1 và d2 trong mảng cpy bằng cách sử dụng một biến tạm thời temp.
        int temp = cpy[d1];
        cpy[d1] = curBoard[d2];
        cpy[d2] = temp;
        //Đầu tiên, nó tạo một bản sao của bảng hiện tại bằng cách gọi phương thức copyBoard() và lưu trữ nó trong một mảng mới cpy.
        //
        //Tiếp theo, nó hoán đổi các giá trị tại các chỉ số d1 và d2 trong mảng cpy bằng cách sử dụng một biến tạm thời temp.
        //
        //Sau đó, nó tạo một đối tượng EightPuzzleState mới với mảng cpy được cập nhật và thêm nó vào ArrayList s.
        //
        //Về cơ bản, phương pháp này tạo và lưu trữ tất cả các trạng thái có thể có được bằng cách hoán đổi vị trí của ô trống (0)
        // với một trong các ô liền kề của nó trong bảng xếp hình.
        s.add((new EightPuzzleState(cpy)));
    }

    @Override
    public boolean isGoal() {
        return Arrays.equals(curBoard, GOAL);
    }


    @Override
    public void printState() {
        System.out.println(curBoard[0] + " | " + curBoard[1] + " | " + curBoard[2]);
        System.out.println("---------");
        System.out.println(curBoard[3] + " | " + curBoard[4] + " | " + curBoard[5]);
        System.out.println("---------");
        System.out.println(curBoard[6] + " | " + curBoard[7] + " | " + curBoard[8]);
    }

    @Override
    public boolean equals(State s) {
        return Arrays.equals(curBoard, ((EightPuzzleState) s).getCurBoard());
    }

    public int[] getCurBoard() {
        return curBoard;
    }
}
