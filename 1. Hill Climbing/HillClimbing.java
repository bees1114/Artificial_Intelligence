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
			// 한 컬럼에서 각 로우별로 값을 넣고, 휴리스틱을 계산
			if(tempHeuristic == 0) {
				return tempBoard;
			}
			
			for (int i = 0;i < N; i++) {
				tempBoard.setValueOfColumn(tempColumn, i);
				if(tempBoard.getHeuristic() > tempHeuristic || tempColumn == N - 1) {
					// heuristic 이 더 늘어나거나, 범위를 넘으면 continue
					continue;
				}
				tempColumn = (tempColumn + 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
				frindge.offer(new Board(tempBoard));
				//queue에 현재 state를 추가
				tempColumn = (tempColumn - 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
			}
		}
		return tempBoard;// hill climbing이 완료되면 해당 보드 리턴
	}
	
	/* heuristic이 현 상태보다 더 낮은곳만 찾아가는 hill climbing으로 답을 찾을 수 없을때 
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
			// 한 컬럼에서 각 로우별로 값을 넣고, 휴리스틱을 계산
			if (tempHeuristic == 0) {
				return tempBoard;
			}
			
			for (int i = 0;i < N; i++) {
				tempBoard.setValueOfColumn(tempColumn, i);
				if(tempColumn == N - 1) {
					// 범위를 넘으면 continue
					continue;
				}
				tempColumn = (tempColumn + 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
				frindge.offer(new Board(tempBoard));
				//queue에 현재 state를 추가
				tempColumn = (tempColumn - 1)%N;
				tempBoard.setStepOfColumn(tempColumn);
			}
		}
		return tempBoard;// hill climbing이 완료되면 해당 보드 리턴
	}
}