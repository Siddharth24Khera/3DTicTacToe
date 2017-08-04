//This is a source file
import java.util.ArrayList;
import java.util.Scanner;

public class Source  
{
	private Intelligence intel;
	private int firstPlayer; // 0 for computer 1 for human
	public Source(){
		Intelligence intel = new Intelligence();
		firstPlayer = 0;
	}

	public static void main (String args [])
	{
		Source source = new Source();
		System.out.println("Welcome to 3DTicTacToe");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

	}
}
