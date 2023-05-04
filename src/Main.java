import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("What is Initial State (example : 0 1 2 3 4 5 6 7 8) : \n");

        int[] startingStateBoard = new int[9];
        for (int i = 0; i < 9; i++) {
            startingStateBoard[i] = reader.nextInt();
        }
        if (checkValid(startingStateBoard) == 0) {
            System.out.println("This is Final State. Please try with other Initial State!!!");
        } else if (checkValid(startingStateBoard) == -1) {
            System.out.println("Wrong State. Please try again!!!");
        } else {
            System.out.println("Which Algorithm ?\n" +
                                "1 - bfs\n" +
                                "2 - aso\n" );
            int choice = reader.nextInt();

            switch (choice) {
                case 1 -> BFS.search(startingStateBoard);
                case 2 -> AStar.search(startingStateBoard, 'o');
                default -> System.out.println("Try again!!!");
            }
        }
    }

    static boolean checkFinalState(int[] arr) {
        int[] finalState = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != finalState[i]) {
                return false;
            }
        }
        return true;
    }

    static int checkValid(int[] arr) {
        if (checkFinalState(arr)) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0 || arr[i] > 8) {
                return -1;
            }
            set.add(arr[i]);
        }
        if (set.size() < 9) {
            return -1;
        }
        return 1;
    }
}