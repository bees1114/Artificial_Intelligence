import java.util.ArrayList;


public class CSP {
	//standard CSP를 구현한 함수.
	public State doStandardCSP(State currentState) {
		// 변수 선언부
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard();
		
		// 답을 찾았는지 check한다.
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		//더이상 사용할 variable이 없으면 null반환
		if (var.isEmpty())
			return null;
		int currentColumn = var.get(0);
		var.remove(0);
		// 현재 값을 할당해야 하는 column에 domain에서 가능한 value들을 할당하며 정답을 찾아감
		for(Integer temp:currentState.getDomainOfColumn(currentColumn)) {
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//DFS
				retState = doStandardCSP(tempState);
				// retState가 null이 아니면 반환하고, null이라면 다시 currentState에서 다른 domain value를 할당하며 계속 진행.
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		//실패할 경우 null
		return null;
		
	}
	
	// foward checking 을 활용한 CSP알고리즘
	public State doCSPFowardChecking(State currentState) {
		// 변수선언부
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard().clone();
		// 답을 찾았는지 체크
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		// variable이 비어있으면 null 리턴
		if (var.isEmpty())
			return null;
		// MRV를 통한 다음 column 선택
		int currentColumn = tempState.MRV();
		var.remove(new Integer(currentColumn));
		if(tempState.getDomainOfColumn(currentColumn).isEmpty())
			return null;
		// 현재 값을 할당해야 하는 column에 domain에서 가능한 value들을 할당하며 정답을 찾아감
		for(Integer temp:tempState.getDomainOfColumn(currentColumn)) {
			
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//standard csp와의 다른점은 바로 각 variable 에 domain value가 할당될 때마다, 다른 node를 미리미리 체크하는것
				//이 과정을 fowardchecking이라고 하며, 이 프로그램에서는 domainupdate 함수로 구현.
				tempState.domainUpdate(currentColumn, temp.intValue());
				//DFS
				retState = doCSPFowardChecking(tempState);
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		// 실패할경우 null
		return null;
	}
	
	//ArcConsistency 를 이용한 CSP
	public State doCSPArcConsistency(State currentState) {
		//변수 선언부
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard();
		//답을 찾았는지 체크
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		//variable이 없으면 null리턴
		if (var.isEmpty())
			return null;
		int currentColumn = tempState.MRV();
		var.remove(new Integer(currentColumn));

		// 현재 값을 할당해야 하는 column에 domain에서 가능한 value들을 할당하며 정답을 찾아감
		for(Integer temp:currentState.getDomainOfColumn(currentColumn)) {
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//standard CSP와 다른 점은 arc Consistency도 variable에 domain value가 할당될 때마다 다른 노드들을 미리 체크한다는 것
				//이 프로그램에서는 arcConsistency 함수 내부에서 해당 기능이 구현되었다.
				tempState.arcConsistency(currentColumn, temp.intValue());
				//DFS
				retState = doCSPArcConsistency(tempState);
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		// 실패할경우 null리턴
		return null;
		
	}
	// 문제의 제약조건을 만족하는지 체크하는 함수
	public boolean checkBoard(int [] board, int currentColumn) {
		for(int i = 0;i < currentColumn - 1; i++) {
			if(board[i] == -1)
				continue;
			for(int j = i + 1; j < currentColumn; j++) {
				if(board[j] == -1)
					continue;
				if (board[i] == board[j]) {
					return false;
				}
				if (Math.abs(i - j) == Math.abs(board[i] - board[j])) {
					return false;
				}
				if (Math.abs(i - j) == -1 * Math.abs(board[i] - board[j])) {
					return false;
				}
			}
		}
		return true;
	}
}
