//Lớp SearchNode này đóng vai trò là một bao bọc (wrapper)
// cho một trạng thái (State), và lưu trữ chi phí để đến trạng
// thái đó và nút cha của trạng thái đó.
public class SearchNode {
    private State curState;//Trạng thái hiện tại của nút.
    private SearchNode parent; //Nút cha của nút hiện tại.
    private double cost; // Chi phí để đến nút hiện tại từ nút cha của nó.
    private double hCost; // Chi phí heuristics để đến trạng thái đích từ trạng thái hiện tại.
    private double fCost; // Tổng chi phí (cost + hCost) để đến trạng thái đích từ trạng thái hiện tại.

    //khởi tạo nút gốc
    public SearchNode(State s) {
        curState = s;
        parent = null;
        cost = 0;
        hCost = 0;
        fCost = 0;
    }

    //khởi tạo nút
    public SearchNode(SearchNode prev, State s, double c, double h) {
        parent = prev;
        curState = s;
        cost = c;
        hCost = h;
        fCost = cost + hCost;
    }

    public State getCurState() {
        return curState;
    }

    public SearchNode getParent() {
        return parent;
    }

    public double getCost() {
        return cost;
    }

    public double getHCost() {
        return hCost;
    }

    public double getFCost() {
        return fCost;
    }

    public void setFCost(double fCost) {
        this.fCost = fCost;
    }
}
