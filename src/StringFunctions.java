import java.util.ArrayList;

public class StringFunctions {
	
	public static ArrayList<String> addIfNew(ArrayList<String> list, String entry) {
		
		for (String s : list) {
			if (s.equals(entry)) {
				return list;
			}
		}
		
		list.add(entry);
		
		return list;
	}

}
