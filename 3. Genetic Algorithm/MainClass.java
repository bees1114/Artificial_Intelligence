import java.io.*;

public class MainClass {
	//main function
	public static void main(String [] args) throws IOException {
		int N;
		String filePath;
		Status answer;
		// N �� ���� ��, 0��° argument
		N = Integer.parseInt(args[0]);
		filePath = args[1]; // output file path ���� 1��° argument
		FileWriter outFile = new FileWriter(filePath + "\\Result" + N + ".txt");
		double startTime = System.currentTimeMillis();		
		
		// �˰��� ��ü �ʱ�ȭ, 0.9�� 0.1�� ���� crossover rate�� mutation rate�̴�. 
		// parameter (���� ��, �� ������ ��, crossover rate, mutation rate)
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(N, N*N*N, 0.9, 0.1);
		//genetic algorithm execution
		answer = geneticAlgorithm.doGeneticAlgorithm();
		// ��� ���
		outFile.write(">Genetic Algorithm\r\n");
		outFile.write(answer.toString());
		double endTime = System.currentTimeMillis() - startTime;
		outFile.write("\r\nTotal Elasped Time : " + endTime/1000.0);
		outFile.close();
	}
	
	
	
}
