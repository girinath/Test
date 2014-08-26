package refer;

public class Palin {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int num = 12321;
		int orignum = num;
		int rem;
		int rev=0;
		
		for (int i=0;i<5;i++)
		{
		rem = num%10;
		rev = rev*10+rem;
		num = num/10;
		}
		System.out.println(rev);
		
		if (orignum == rev)
		{
			System.out.println("palindrome");
		}
		else 
		{
		System.out.println("not palindrome");	
		}
	}

}
