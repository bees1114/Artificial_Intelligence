import java.util.*;

public class HillClimbing {
	//hill climbing algorithm
	public Board doHillClimbing(Board board) {
		Board tempBoard;
		PriorityQueue<Board> frindge = new PriorityQueue<Board>();
		//use priority queue for board status
		int tempColumn;
		int N = board.getNumberOfQueen();
		int tempHeuristic;
		// get board and calculate heuristic of the board
		tempBoard = new Board(board);
		tempBoard.calculateHeuristic();
		
		frindge.offer(tempBoard);
		// do while until queue empty or heuristic is zero
		while(!frindge.isEmpty()) {
			tempBoard = frindge.poll();
			tempHeuristic = tempBoard.getHeuristic();
			tempColumn = tempBoard.getStepOfColumn();
			// �� �÷����� �� �ο캰�� ���� �ְ�, �޸���ƽ�� ���
			if(tempHeuristic == 0) {
				return tempBoard;
			}
			
			for (int i = 0;i < N; i++) {
				tempBoard.setValueOfColumn(tempColumn, i);
				if(tempBoard.getHeuristic() > tempHeuristic || tempColumn == N - 1) {
					// heuristic �� �� �þ�ų�, ������ ������ continue
					continue;
				}
				tempColumn = (tempColumn + 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
				frindge.offer(new Board(tempBoard));
				//queue�� ���� state�� �߰�
				tempColumn = (tempColumn - 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
			}
		}
		return tempBoard;// hill climbing�� �Ϸ�Ǹ� �ش� ���� ����
	}
	
	/* heuristic�� �� ���º��� �� �������� ã�ư��� hill climbing���� ���� ã�� �� ������ 
	 * 
	 * */
	public Board doHillClimbing2(Board board) {
		Board tempBoard;
		PriorityQueue<Board> frindge = new PriorityQueue<Board>();
		//use priority queue for board status
		int tempColumn;
		int N = board.getNumberOfQueen();
		int tempHeuristic;
		// get board and calculate heuristic of the board
		tempBoard = new Board(board);
		tempBoard.calculateHeuristic();
		
		frindge.offer(tempBoard);
		// do while until queue empty or heuristic is zero
		while(!frindge.isEmpty()) {
			tempBoard = frindge.poll();
			tempHeuristic = tempBoard.getHeuristic();
			tempColumn = tempBoard.getStepOfColumn();
			// �� �÷����� �� �ο캰�� ���� �ְ�, �޸���ƽ�� ���
			if (tempHeuristic == 0) {
				return tempBoard;
			}
			
			for (int i = 0;i < N; i++) {
				tempBoard.setValueOfColumn(tempColumn, i);
				if(tempColumn == N - 1) {
					// ������ ������ continue
					continue;
				}
				tempColumn = (tempColumn + 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
				frindge.offer(new Board(tempBoard));
				//queue�� ���� state�� �߰�
				tempColumn = (tempColumn - 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
			}
		}
		return tempBoard;// hill climbing�� �Ϸ�Ǹ� �ش� ���� ����
	}
}