import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// �� ü������ ���¸� ��Ÿ���� Ŭ����
public class State {
	private int [] board;
	private ArrayList<Integer> [] domain;
	private ArrayList<Integer> variable;
	private int currentColumn;
	private int N;
	
	@SuppressWarnings("unchecked")
	// ������ n�� ü������ ũ��
	public State(int n) {
		this.N = n;
		currentColumn = 0;
		board = new int[N];
		domain = (ArrayList<Integer>[])new ArrayList[N];
		variable = new ArrayList<Integer>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < N; i++) {
			temp.add(i);
			board[i] = -1;
		}
		
		for(int i = 0;i < N; i++) {
			domain[i] = listCopy(temp);
			variable.add(i);
		}
		
	}
	// ������ State b�� �޾Ƽ� �׿� �Ȱ��� ���¸� �����.
	public State(State b) {
		currentColumn = b.currentColumn;
		this.board = b.board.clone();
		for(int i = 0;i < N; i++) {
			this.domain[i] = listCopy(b.domain[i]);
		}
		this.variable = listCopy(b.variable);
		this.N = b.N;
		
	}
	
	//setter for variables
	public void setCurrentColumn(int column) {
		currentColumn = column;
	}

	//setter for variables
	public void setVariable(ArrayList<Integer> variable) {
		this.variable = listCopy(variable);
	}

	//setter for variables
	public void setBoard(int[] board) {
		this.board = board.clone();
	}
	
	//getter for variables
	public ArrayList<Integer> getVariable() {
		return variable;
	}

	//getter for variables
	public int getCurrentColumn() {
		return currentColumn;
	}

	//getter for variables
	public ArrayList<Integer> getDomainOfColumn(int Column) {
		return domain[Column];
	}

	//getter for variables
	public int[] getBoard() {
		return board;
	}
	
	// �� �Լ��� arraylist.clone�� ����� �ϴ� �Լ��̴�.
	ArrayList<Integer> listCopy(ArrayList<Integer> list) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		Integer tempInteger;
		for(Integer listItem: list) {
				tempInteger = new Integer(listItem.intValue());
				temp.add(tempInteger);
		}
		return temp;
	}
	 
	// State.clone�� ����
	public State clone() {
		State ret;
		ret = new State(N);
		ret.board = board.clone();
		for(int i = 0;i < N; i++) {
			ret.domain[i] = listCopy(domain[i]);
		}
		ret.variable = listCopy(variable);
		ret.N = N;
		ret.currentColumn = currentColumn;
		return ret;
	}
	
	// State.toString �� ����
	public String toString(){
		String ret = "";
		for(int i = 0; i < N; i++) {
			ret += Integer.toString(board[i]) + " ";
		}
		ret = ret.substring(0, ret.length()-1);
		return ret;
	}
	
	// �� �Լ����� n-queens ������ ���� ������ �˻��Ѵ�.
	public boolean stateCheck() {
		for(int i = 0; i < N - 1; i++) {
			if(board[i] == -1)
				return false;
			for(int j = i + 1;j < N; j++) {
				// �� ���� ���� ���踦 ������ ����, �밢�� ������ ��Ƹ����� �ִ� ���������� �Ǵ�.
				if(board[j] == -1)
					return false;
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
	
	// MRV�� �̿��� ���� state�� �Ǻ�
	public int MRV() {
		int min = 0;
		//���� ���� domain�� ���� variable�� ���õȴ�.
		for (Integer col:variable) {
			if(domain[col].isEmpty())
				continue;
			if(domain[col].size() < domain[min].size())
				min = col;
		}

		return min;
	}
	
	// domain�� update�ϴµ� �̿� (foward checking���� Ȱ��ȴ�)
	public void domainUpdate(int column, int row) {
		ArrayList<Integer> tempList;
		// �� �ܰ迡�� variable�� � domain�� ���� �Ҵ�� ���, ������ domain�� update���ش�.
		for(Integer col:variable) {
			// �� variable���� ����
			int columnValue = col.intValue();
			tempList = listCopy(domain[columnValue]);
			if (tempList.isEmpty()){
				return ;
			}
			for (Integer value:domain[columnValue]) {
				// ���� ����� �� ���� domain value�� update
				if (value.intValue() == row) {
					tempList.remove(value);
				}
				if (Math.abs(columnValue - column) == Math.abs(value.intValue() - row)) {
					tempList.remove(value);
				}
				if (Math.abs(columnValue - column) == Math.abs(value.intValue() - row)) {
					tempList.remove(value);
				}
			}
			if (tempList.isEmpty()){
				return ;
			}
			domain[columnValue] = listCopy(tempList);
			
		}
	}
	
	//arc consistency���� ����
	public void arcConsistency(int column, int row) {
		// �̿�Ǵ� �ڷᱸ���� ���� queue�� �̿��Ѵ�.
		Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
		ArrayList<Integer> tempList;
		
		//�⺻������ foward checking�� �����ϰ�, � variable�� domain value�� �Ҵ�� ��� �ٸ� variable���� domain�� update���ش�.
		for(Integer col:variable) {
			int columnValue = col.intValue();
			tempList = listCopy(domain[columnValue]);
			if (tempList.isEmpty()){
				return ;
			}
			boolean someRemoved = false;
			for (Integer value:domain[columnValue]) {
				if (value.intValue() == row) {
					tempList.remove(value);
					someRemoved = true;
				}
				if (Math.abs(columnValue - column) == Math.abs(value.intValue() - row)) {
					tempList.remove(value);
					someRemoved = true;
				}
				if (Math.abs(columnValue - column) == Math.abs(value.intValue() - row)) {
					tempList.remove(value);
					someRemoved = true;
				}
			}
			
			if (tempList.isEmpty()){
				return ;
			}
			// foward checking�� �ٸ� ����, update�� �߻��� ��� queue�� �ش� variable�� ����־��ش�.
			if(someRemoved == true) {
				queue.add(new ArrayList<Integer>(Arrays.asList(columnValue, column)));
			}
			domain[columnValue] = listCopy(tempList);
		}
		
		// ������ ���� queue�� ������ arc consistency�� �����Ѵ�.
		ArrayList<Integer> domainI;
		ArrayList<Integer> domainJ;
		ArrayList<Integer> tempDomain;
		boolean removed;
		int count = 0;
		// queue�� ��� ���� ������ �Ʒ��� �ڵ带 ����.
		while(!queue.isEmpty()) {
			tempList = queue.poll();
			// column 1�� column 2�� ���� ��, domain value�� �Ҵ����� ����, ���������� ���� �Ǹ� ��� update�� ������ �˰���
			domainI = domain[tempList.get(0)];
			domainJ = domain[tempList.get(1)];
			tempDomain = listCopy(domainI);
			removed = false;
			
			for(int valuei:domainI) {
				count = 0;
				for(int valuej:domainJ) {
					if(valuei == valuej 
							|| Math.abs(valuei - valuej) == Math.abs(tempList.get(0)- tempList.get(1) )
							|| Math.abs(valuei - valuej) == -1 * Math.abs(tempList.get(0)- tempList.get(1))
							) {
						count++;
					}
				}
				if(count == domainJ.size()) {
				//���� �� column�� domain value�� �ٸ� column�� domain value�� ��� ������Ű�� ���ϸ�, �� domain value�� ���ŵ��ϴ�.
					tempDomain.remove(new Integer(valuei));
					if(tempDomain.isEmpty()) {
						continue;
					}
					removed = true;
				}
			}
			// �׷��� ���Ű� �Ǹ�, �̿� ������ domain�� �ٽ� update �� �ʿ䰡 �����Ƿ�, queue�� �̿� ������ �־��ش�.(n-queens������ �����ִ� variable��)
			if(removed == true) {
				for(Integer neighbor:variable) {
					if (neighbor.intValue() != tempList.get(0)) {
						queue.add(new ArrayList<Integer>(Arrays.asList(neighbor.intValue(), tempList.get(0))));
					}
				}
				domain[tempList.get(0)] = tempDomain;
			}
		}
		
	}
}


