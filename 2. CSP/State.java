import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 각 체스판의 상태를 나타내는 클래스
public class State {
	private int [] board;
	private ArrayList<Integer> [] domain;
	private ArrayList<Integer> variable;
	private int currentColumn;
	private int N;
	
	@SuppressWarnings("unchecked")
	// 생성자 n은 체스판의 크기
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
	// 생성자 State b를 받아서 그와 똑같은 상태를 만든다.
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
	
	// 이 함수는 arraylist.clone의 기능을 하는 함수이다.
	ArrayList<Integer> listCopy(ArrayList<Integer> list) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		Integer tempInteger;
		for(Integer listItem: list) {
				tempInteger = new Integer(listItem.intValue());
				temp.add(tempInteger);
		}
		return temp;
	}
	 
	// State.clone의 구현
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
	
	// State.toString 의 구현
	public String toString(){
		String ret = "";
		for(int i = 0; i < N; i++) {
			ret += Integer.toString(board[i]) + " ";
		}
		ret = ret.substring(0, ret.length()-1);
		return ret;
	}
	
	// 이 함수에서 n-queens 게임의 제약 조건을 검사한다.
	public boolean stateCheck() {
		for(int i = 0; i < N - 1; i++) {
			if(board[i] == -1)
				return false;
			for(int j = i + 1;j < N; j++) {
				// 두 가지 말의 관계를 가지고 가로, 대각의 선에서 잡아먹을수 있는 상태인지를 판단.
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
	
	// MRV를 이용한 다음 state의 판별
	public int MRV() {
		int min = 0;
		//가장 적은 domain을 가진 variable이 선택된다.
		for (Integer col:variable) {
			if(domain[col].isEmpty())
				continue;
			if(domain[col].size() < domain[min].size())
				min = col;
		}

		return min;
	}
	
	// domain을 update하는데 이용 (foward checking에서 활용된다)
	public void domainUpdate(int column, int row) {
		ArrayList<Integer> tempList;
		// 각 단계에서 variable에 어떤 domain의 값이 할당될 경우, 나머지 domain을 update해준다.
		for(Integer col:variable) {
			// 각 variable들을 선택
			int columnValue = col.intValue();
			tempList = listCopy(domain[columnValue]);
			if (tempList.isEmpty()){
				return ;
			}
			for (Integer value:domain[columnValue]) {
				// 이제 사용할 수 없는 domain value를 update
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
	
	//arc consistency에서 구현
	public void arcConsistency(int column, int row) {
		// 이용되는 자료구조의 선언 queue를 이용한다.
		Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
		ArrayList<Integer> tempList;
		
		//기본적으로 foward checking과 유사하게, 어떤 variable에 domain value가 할당될 경우 다른 variable들의 domain을 update해준다.
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
			// foward checking과 다른 점은, update가 발생할 경우 queue에 해당 variable을 집어넣어준다.
			if(someRemoved == true) {
				queue.add(new ArrayList<Integer>(Arrays.asList(columnValue, column)));
			}
			domain[columnValue] = listCopy(tempList);
		}
		
		// 위에서 만든 queue를 가지고 arc consistency를 수행한다.
		ArrayList<Integer> domainI;
		ArrayList<Integer> domainJ;
		ArrayList<Integer> tempDomain;
		boolean removed;
		int count = 0;
		// queue가 비어 있지 않으면 아래의 코드를 수행.
		while(!queue.isEmpty()) {
			tempList = queue.poll();
			// column 1과 column 2가 있을 때, domain value의 할당으로 인해, 제약조걱을 어기게 되면 계속 update해 나가는 알고리즘
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
				//만약 한 column의 domain value가 다른 column의 domain value를 모두 충족시키지 못하면, 그 domain value는 제거도니다.
					tempDomain.remove(new Integer(valuei));
					if(tempDomain.isEmpty()) {
						continue;
					}
					removed = true;
				}
			}
			// 그렇게 제거가 되면, 이웃 노드들의 domain을 다시 update 할 필요가 있으므로, queue에 이웃 노드들을 넣어준다.(n-queens에서는 남아있는 variable들)
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


