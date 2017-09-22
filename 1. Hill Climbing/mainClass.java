import java.io.*;

public class mainClass {
	static public void main(String[] argv) throws IOException {
		long start = System.currentTimeMillis(); // check program start time
		int N = Integer.parseInt(argv[0]);
		String outputPath = argv[1];
		// get arguments
		String outputFileName = "result" + N + ".txt";
		FileWriter outputFile = new FileWriter(outputPath +"\\" + outputFileName);
		//variables for fileoutput
		Board board = new Board(N);
		board.calculateHeuristic();
		Board returnBoard = new Board(board);
		HillClimbing hillClimbing = new HillClimbing();

		outputFile.write(">Hill Climbing\r\n");
		//do hill-climbing until find answer
		returnBoard = hillClimbing.doHillClimbing(board);
		if (returnBoard.getHeuristic() != 0)
			returnBoard = hillClimbing.doHillClimbing2(board);
		long end = System.currentTimeMillis() - start;
		// check end time
		outputFile.write(returnBoard.toString());
		outputFile.write("\r\nTotal Elapsed Time: " + (end)/1000.0);
		outputFile.close();
		// main end
	}
}