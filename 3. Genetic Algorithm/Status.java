import java.util.Random;

public class Status {
	private int [] board;
	private int fitness;
	
	public Status(int N) {
		board = new int[N];
		fitness = 0;
	}
	
	public Status(int N, String args) {
		board = new int[N];
		Random random = new Random();
		for(int i = 0; i < N; i++) {
			board[i] = random.nextInt(N);
		}
		this.calculateFitness();
	}
	public Status(Status temp) {
		board = temp.board.clone();
		fitness = temp.fitness;
	}
	
	public void setBoard(int [] board) {
		this.board = board.clone();
		this.calculateFitness();
	}
	
	public int[] getBoard() {
		return board;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public String toString() {
		String ret = String.valueOf(board[0]) + " ";
		for(int i = 1; i < board.length; i++) {
			ret += board[i] + " ";
		}
		return ret;
	}
	
	public void calculateFitness() {
		int tempFitness = 0;
		boolean constraint = false;
		for(int i = 0; i < board.length - 1; i++) {
			for(int j = i; j < board.length;j++) {
				constraint = false;
				if(board[i] == board[j]) {
					constraint = true;
				}
				if((j - i) == Math.abs(board[i] - board[j])) {
					constraint = true;
				}
				if(constraint == false) {
					tempFitness++;
				}
			}
		}
		fitness = tempFitness;
	}
	
	public Status clone() {
		Status retStatus = new Status(board.length);
		retStatus.setBoard(board.clone());
		return retStatus;
	}
}
