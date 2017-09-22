import java.util.ArrayList;


public class CSP {
	//standard CSP�� ������ �Լ�.
	public State doStandardCSP(State currentState) {
		// ���� �����
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard();
		
		// ���� ã�Ҵ��� check�Ѵ�.
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		//���̻� ����� variable�� ������ null��ȯ
		if (var.isEmpty())
			return null;
		int currentColumn = var.get(0);
		var.remove(0);
		// ���� ���� �Ҵ��ؾ� �ϴ� column�� domain���� ������ value���� �Ҵ��ϸ� ������ ã�ư�
		for(Integer temp:currentState.getDomainOfColumn(currentColumn)) {
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//DFS
				retState = doStandardCSP(tempState);
				// retState�� null�� �ƴϸ� ��ȯ�ϰ�, null�̶�� �ٽ� currentState���� �ٸ� domain value�� �Ҵ��ϸ� ��� ����.
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		//������ ��� null
		return null;
		
	}
	
	// foward checking �� Ȱ���� CSP�˰���
	public State doCSPFowardChecking(State currentState) {
		// ���������
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard().clone();
		// ���� ã�Ҵ��� üũ
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		// variable�� ��������� null ����
		if (var.isEmpty())
			return null;
		// MRV�� ���� ���� column ����
		int currentColumn = tempState.MRV();
		var.remove(new Integer(currentColumn));
		if(tempState.getDomainOfColumn(currentColumn).isEmpty())
			return null;
		// ���� ���� �Ҵ��ؾ� �ϴ� column�� domain���� ������ value���� �Ҵ��ϸ� ������ ã�ư�
		for(Integer temp:tempState.getDomainOfColumn(currentColumn)) {
			
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//standard csp���� �ٸ����� �ٷ� �� variable �� domain value�� �Ҵ�� ������, �ٸ� node�� �̸��̸� üũ�ϴ°�
				//�� ������ fowardchecking�̶�� �ϸ�, �� ���α׷������� domainupdate �Լ��� ����.
				tempState.domainUpdate(currentColumn, temp.intValue());
				//DFS
				retState = doCSPFowardChecking(tempState);
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		// �����Ұ�� null
		return null;
	}
	
	//ArcConsistency �� �̿��� CSP
	public State doCSPArcConsistency(State currentState) {
		//���� �����
		State tempState = currentState.clone();
		State retState;
		int [] tempBoard = currentState.getBoard();
		//���� ã�Ҵ��� üũ
		if (tempState.stateCheck() == true)
			return tempState;
		ArrayList<Integer> var = tempState.getVariable();
		//variable�� ������ null����
		if (var.isEmpty())
			return null;
		int currentColumn = tempState.MRV();
		var.remove(new Integer(currentColumn));

		// ���� ���� �Ҵ��ؾ� �ϴ� column�� domain���� ������ value���� �Ҵ��ϸ� ������ ã�ư�
		for(Integer temp:currentState.getDomainOfColumn(currentColumn)) {
			tempBoard[currentColumn] = temp.intValue();
			if (checkBoard(tempBoard, currentColumn) == true) {
				tempState.setBoard(tempBoard);
				tempState.setVariable(var);
				//standard CSP�� �ٸ� ���� arc Consistency�� variable�� domain value�� �Ҵ�� ������ �ٸ� ������ �̸� üũ�Ѵٴ� ��
				//�� ���α׷������� arcConsistency �Լ� ���ο��� �ش� ����� �����Ǿ���.
				tempState.arcConsistency(currentColumn, temp.intValue());
				//DFS
				retState = doCSPArcConsistency(tempState);
				if(retState != null)
					return retState;
				tempState = currentState.clone();
			}
		}
		// �����Ұ�� null����
		return null;
		
	}
	// ������ ���������� �����ϴ��� üũ�ϴ� �Լ�
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
