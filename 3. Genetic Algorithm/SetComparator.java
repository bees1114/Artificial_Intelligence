import java.util.Comparator;

public class SetComparator<T> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		Status first = (Status)o1;
		Status second = (Status)o2;
		int ret = first.getFitness() - second.getFitness();
		
		if(ret > 0) {
			return -1;
		} 
		else {
			return 1;
		}
	}
	
}
