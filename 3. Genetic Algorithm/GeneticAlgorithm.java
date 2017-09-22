import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class GeneticAlgorithm {
	private int numberOfQueens;
	private int population;
	private double crossoverRate;
	private double mutationRate;
	private int endCondition;
	private Status [] parents;
	private TreeSet<Status> epoch = new TreeSet<Status>(new SetComparator<Status>());
	Random random = new Random();
	
	public GeneticAlgorithm(int N, int p, double CR, double MR) {
		numberOfQueens = N;
		population = p;
		crossoverRate = CR;
		mutationRate = MR;
		endCondition = (N)*(N - 1)/2;
		int numberOfParents = (int) (population * (1-crossoverRate));
		parents = new Status[numberOfParents];
		
		Status initParents;
		for(int i = 0; i < population; i++) {
			initParents = new Status(numberOfQueens, "init");
			epoch.add(initParents.clone());
		}
		
		
	}
	
	public Status doGeneticAlgorithm() {
		int numberOfParents = (int) (population * (1-crossoverRate));
		int numberOfCrossover = (int) (population * crossoverRate);
		int numberOfMutation = (int) (population * mutationRate);
		int tempMutation = 0;
		int numberOfEpoch = 0;
		int father, mother;
		Status tempStatus;
		
		while(true) {
			if(epoch.first().getFitness() == endCondition) {
				return epoch.pollFirst();
			}
			for(int i = 0; i < numberOfParents; i++) {
				parents[i] = epoch.pollFirst();
			}
			epoch.clear();
			for(int i = 0; i < numberOfParents; i++) {
				epoch.add(parents[i]);
			}
			for(int i = 0; i < numberOfCrossover;i++) {
				father = random.nextInt(numberOfParents);
				mother = father;
				while(mother == father) {
					mother = random.nextInt(numberOfParents);
				}
				tempStatus = crossover(parents[father], parents[mother]);
				if(tempMutation < numberOfMutation) {
					mutation(tempStatus);
				}
				epoch.add(new Status(tempStatus));
			}
			numberOfEpoch++;
		}
		
	}
	
	public Status crossover(Status father, Status mother) {
		int [] fatherBoard, motherBoard, tempBoard = new int[numberOfQueens];
		int index = random.nextInt(numberOfQueens);
		Status nextStatus = new Status(numberOfQueens);
		
		fatherBoard = father.getBoard();
		motherBoard = mother.getBoard();
		
		for(int i = 0; i < index; i++) {
			tempBoard[i] = fatherBoard[i];
		}
		for(int i = index; i < numberOfQueens; i++) {
			tempBoard[i] = motherBoard[i];
		}
		nextStatus.setBoard(tempBoard);
		return new Status(nextStatus);
		
	}
	
	public void mutation(Status target) {
		int numberOfMutation = random.nextInt(numberOfQueens);
		ArrayList<Integer> buffer = new ArrayList<Integer>();
		int [] tempBoard = target.getBoard();
		int column, tempNumber;
		
		for(int i = 0; i < numberOfMutation; i++) {
			while(true) {
				column = random.nextInt(numberOfQueens);
				if(!buffer.contains(new Integer(column))){
					buffer.add(new Integer(column));
					break;
				}
			}
			tempNumber = tempBoard[column];
			while(tempNumber == tempBoard[column]) {
				tempBoard[column] = random.nextInt(numberOfQueens);	
			}
		}
		target.setBoard(tempBoard.clone());
	}
}
