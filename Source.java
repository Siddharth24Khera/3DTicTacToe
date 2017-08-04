//This is a source file
import java.util.ArrayList;

public class Source  
{
	public static void main (String args [])
	{
		Intelligence intel = new Intelligence();
		MagicCube magic = new MagicCube(3);
		ArrayList<ArrayList<Integer>> lis = magic.getMagic_cube();

	}
}
