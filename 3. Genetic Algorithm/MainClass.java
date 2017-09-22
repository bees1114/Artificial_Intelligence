import java.io.*;

public class MainClass {
	//main function
	public static void main(String [] args) throws IOException {
		int N;
		String filePath;
		Status answer;
		// N 은 퀸의 수, 0번째 argument
		N = Integer.parseInt(args[0]);
		filePath = args[1]; // output file path 지정 1번째 argument
		FileWriter outFile = new FileWriter(filePath + "\\Result" + N + ".txt");
		double startTime = System.currentTimeMillis();		
		
		// 알고리즘 객체 초기화, 0.9와 0.1은 각각 crossover rate와 mutation rate이다. 
		// parameter (퀸의 수, 한 세대의 수, crossover rate, mutation rate)
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(N, N*N*N, 0.9, 0.1);
		//genetic algorithm execution
		answer = geneticAlgorithm.doGeneticAlgorithm();
		// 결과 출력
		outFile.write(">Genetic Algorithm\r\n");
		outFile.write(answer.toString());
		double endTime = System.currentTimeMillis() - startTime;
		outFile.write("\r\nTotal Elasped Time : " + endTime/1000.0);
		outFile.close();
	}
	
	
	
}
