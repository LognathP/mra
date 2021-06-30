import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	public static void main(String[] args) {
		List<String> s = new ArrayList<String>();
		s.add("1");
		s.add("2");
		System.out.println(s);
		System.out.println(String.join(",", s));
		
		String s1 = "";
		System.out.println(Integer.parseInt(s1));
	}
}
