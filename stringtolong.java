
//It handles positive as well as negative numbers
public class stringtolong {

	public static void main(String[] args) {

		long a = convert("2380");
		System.out.println(a);

	}

	public static long convert(String s) {

		long num = 0,sign;
		int j;
		if (s.charAt(0) == '-') {
			sign = -1;
			j=1;
		} else {
			sign=1;
			j=0;
		}
		
		for (int i = j; i < s.length(); i++) {
			
				if (((int) s.charAt(i) >= 48) && ((int) s.charAt(i) <= 57)) {

					num = num * 10 + ((int) s.charAt(i) - 48);

				} else {
					throw new NumberFormatException();
				}
			}
		
		return num * sign;

	}
}
