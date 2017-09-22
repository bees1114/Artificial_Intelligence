import java.util.Random;

public class Board implements Comparable<Board> {
	private int [] board;
	private int numberOfQueen;
	private int heuristic;
	private int stepOfColumn;
	//board 의 생성자, 랜덤으로 board 초기화
	public Board(int N) {
		Random random = new Random();
		numberOfQueen = N;
		stepOfColumn = 0;
		board = new int[N];
		//board initialize as random 
		
		for(int i = 0; i < numberOfQueen; i++) {
			board[i] = random.nextInt(N);	
		}
		/*board[0] = 7;
		board[1] = 1;
		board[2] = 1;
		board[3] = 3;
		board[4] = 6;
		board[5] = 0;
		board[6] = 4;
		board[7] = 3;
		*/
	}
	
	//this method operate like clone()
	public Board(Board inputBoard) {
		this.board = inputBoard.getBoard().clone();
		this.numberOfQueen = inputBoard.getNumberOfQueen();
		this.heuristic = inputBoard.getHeuristic();
		this.stepOfColumn = inputBoard.getStepOfColumn();
	}
	
	//increase variable
	public void increaseStepOfColumn() {
		stepOfColumn++;
	}
	
	//setter
	public void setStepOfColumn(int input) {
		stepOfColumn = input;
	}
	// check constraint (if other queen is in same row or diagonal, return false
	public boolean checkOtherQueen(int column, int value) {
		for(int i = 0; i < column; i++) {
			if(board[i] == value)
				return false;
			if(Math.abs(i - column) == Math.abs(board[i] - value))
				return false;
		}
		return true;
	}
	
	//get column and row as a parameter and set it in board
	public void setValueOfColumn(int column, int value) {
		if (column < numberOfQueen && checkOtherQueen(column, value)) {
			board[column] = value;//board updated
			this.calculateHeuristic(); // calculate heuristic
		}
	}
	//getter
	public int[] getBoard() {
		return board;
	}
	//getter
	public int getStepOfColumn() {
		return stepOfColumn;
	}
	//getter
	public int getNumberOfQueen() {
		return numberOfQueen;
	}
	//getter
	public int getHeuristic() {
		return heuristic;
	}
	
	/* implement toString this function return board as String*/
	public String toString() {
		String boardToString = "";
		
		for(int i = 0; i < numberOfQueen; i++) {
			boardToString += Integer.toString(board[i]);
			if(i + 1 != numberOfQueen) {
				boardToString += " ";
			}
		}
		
		return boardToString;
	}
	/* calculate heuristic
	 * in this case, heuristic value means number of queens that violate constraint condition
	 * */
	public void calculateHeuristic() {
		int count = 0;
		for(int i = 0;i < numberOfQueen - 1;i++) {
			for(int j = i + 1; j < numberOfQueen;j++) {
				if (board[i] == board[j]) {
					count++;
					continue;
				}
				if(Math.abs(j - i) == Math.abs(board[j] - board[i])) {
					count++;
					continue;
				}
			}
		}
		heuristic = count;
	}
	
	@Override //this function used in priority queue
	public int compareTo(Board target) {
		int targetHeuristic = target.getHeuristic();
		if(this.heuristic > targetHeuristic) {
			return 1;
		} else if (this.heuristic < targetHeuristic) {
			return -1;
		}
		return 0;
	}
}