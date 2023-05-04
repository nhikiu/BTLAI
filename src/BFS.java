import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS {

    //lấy một mảng chứa trạng thái ban đầu của bàn cờ, khởi tạo nút gốc
    // và hàng đợi (queue) để lưu trữ các nút tìm kiếm.
    public static void search(int[] board) {
        SearchNode root = new SearchNode(new EightPuzzleState(board));
        Queue<SearchNode> queue = new LinkedList<>();
        queue.add(root);
        performSearch(queue);
    }

    private static boolean checkRepeats(SearchNode n) {
        boolean retValue = false;
        SearchNode checkNode = n;

        while (n.getParent() != null && !retValue) {
            if (n.getParent().getCurState().equals(checkNode.getCurState())) {
                retValue = true;
            }
            n = n.getParent();
        }
        return retValue;
    }

    //thực hiện quá trình tìm kiếm theo chiều rộng bằng cách lấy ra nút đầu tiên từ hàng đợi và
    // kiểm tra xem nó có phải là trạng thái mục tiêu hay không. Nếu không phải, hàm tạo ra các
    // nút con của nút hiện tại bằng cách thực hiện tất cả các hoán vị có thể của ô trống và các
    // ô xung quanh nó. Nếu nút con này chưa được xét đến trước đó, nó được thêm vào hàng đợi để
    // xét đến sau này. Hàm cũng kiểm tra xem nút mới được tạo ra có trùng lặp với nút nào đã được
    // duyệt trước đó không.
    public static void performSearch(Queue<SearchNode> q) {
        int searchCount = 1;

        while (!q.isEmpty()) {
            SearchNode tempNode = q.poll();

            if (!tempNode.getCurState().isGoal()) {
                ArrayList<State> tempSuccessors = tempNode.getCurState()
                        .genSuccessors();

                for (int i = 0; i < tempSuccessors.size(); i++) {
                    SearchNode newNode = new SearchNode(tempNode,
                            tempSuccessors.get(i), tempNode.getCost()
                            + tempSuccessors.get(i).findCost(), 0);

                    if (!checkRepeats(newNode)) {
                        q.add(newNode);
                    }
                }
                searchCount++;
            } else {
                Stack<SearchNode> solutionPath = new Stack<>();
                solutionPath.push(tempNode);
                tempNode = tempNode.getParent();

                while (tempNode.getParent() != null) {
                    solutionPath.push(tempNode);
                    tempNode = tempNode.getParent();
                }
                solutionPath.push(tempNode);

                int loopSize = solutionPath.size();

                for (int i = 0; i < loopSize; i++) {
                    tempNode = solutionPath.pop();
                    tempNode.getCurState().printState();
                    System.out.println();
                    System.out.println();
                }
                System.out.println("The cost was: " + tempNode.getCost());
                System.out.println("Loop Size: " + loopSize);
                System.out.println("Search count: " + searchCount);
                System.exit(0);
            }
        }
        //Nếu nút đầu tiên trong hàng đợi là trạng thái mục tiêu, chương trình sẽ truy xuất ngược
        // từ nút này để xác định dãy các bước di chuyển để đạt được trạng thái mục tiêu. Sau đó,
        // nó in ra trạng thái cuối cùng, số bước đi và số lần tìm kiếm đã thực hiện. Nếu không
        // tìm thấy giải pháp nào, chương trình sẽ in ra thông báo lỗi.

        // This should never happen with our current puzzles.
        System.out.println("Error! No solution found!");
    }
}
