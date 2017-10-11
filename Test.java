public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Regexp a  = new Regexp("x*a");
		System.out.println(a.match("bxxxa"));
		System.out.println(a.start());
		System.out.println(a.end());
		
		
	}

}
