import java.io.FileWriter;
import java.io.IOException;

public class MainClass {
	// main function
	public static void main(String [] args) throws IOException {
		int N;
		String filePath;
		String outputString;
		// ���� �Լ��� ���ڸ� �޾ƿ��� �κ�
		N = Integer.parseInt(args[0]);
		filePath = args[1] + "\\result" + Integer.toString(N) + ".txt";
		
		// ü�� ���� ��Ÿ���� ��ü��, �˰����� ������ �ִ� Ŭ������ ��ü ����
		State board = new State(N);
		CSP csp = new CSP();
		
		// ��� ����� ���� ����
		FileWriter output = new FileWriter(filePath);
		
		// ��� ���� �� ��ºκ�
		//standard CSP
		output.write(">Standard CSP\r\n");
		long start = System.currentTimeMillis();
		board = csp.doStandardCSP(board);
		if(board == null) {
			outputString = "No solution";
		} else {
			outputString = board.toString();
		}
		long end = System.currentTimeMillis() - start;
		output.write("Location : " + outputString + "\r\n");
		output.write("Total Elapsed Time : " + end/1000.0 + "\r\n\r\n");
		board = new State(N);
		
		// CSP Foward checking
		output.write(">CSP with Forward Checking\r\n");
		start = System.currentTimeMillis();
		board = csp.doCSPFowardChecking(board);
		if(board == null) {
			outputString = "No solution";
		} else {
			outputString = board.toString();
		}
		end = System.currentTimeMillis()- start;
		output.write("Location : " + outputString + "\r\n");
		output.write("Total Elapsed Time : " + end/1000.0 + "\r\n\r\n");
		
		
		//CSP arc consistency
		output.write(">CSP with Arc Consistency\r\n");
		board = new State(N);
		start = System.currentTimeMillis();
		board = csp.doCSPArcConsistency(board);
		if(board == null) {
			outputString = "No solution";
		} else {
			outputString = board.toString();
		}
		end = System.currentTimeMillis() - start;
		output.write("Location : " + outputString + "\r\n");
		output.write("Total Elapsed Time : " + end/1000.0);
		
		output.close();
	}
}
