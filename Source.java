//This is a source file
import java.util.ArrayList;
import java.util.Scanner;

public class Source  
{
	private Intelligence intel;
	private int firstPlayer; // 0 for computer 1 for human
	private ArrayList<ArrayList<Integer>> board; // 0 for unoccupied; 1 for X; 2 for O

	public Source(){
		intel = new Intelligence();
		firstPlayer = 0;

		board = new ArrayList<ArrayList<Integer>>(3);
		for(int i = 0; i < 3; i++)  {
			board.add(new ArrayList<Integer>(3));
			for(int j = 0; j < 3; j++){
				board.get(i).add(0);
			}
		}
	}

	public Intelligence getIntel(){
		return intel;
	}

	private void print_board(){
		System.out.println("------");
		for(int i = 0; i < 3; i++)  {
			for(int j = 0; j < 3; j++){
				int num = this.board.get(i).get(j);
				if(num == 1)
					System.out.print("X");
				else if(num == 2)
					System.out.print("O");
				else System.out.print(" ");

				if(j==0 || j ==1)
					System.out.print("|");
			}

			System.out.println();
		}
		System.out.println("------");
	}

	public static void main (String args [])
	{
		Source source = new Source();
		System.out.println("Welcome to 3DTicTacToe");
		System.out.println("Enter 1 for playing first or 2 for playing second");
		Scanner sc = new Scanner(System.in);
		int input_num = sc.nextInt();
		if(input_num == 1){
			source.firstPlayer = 1;
			System.out.println("You play X");
		}
		else
			System.out.println("You play O");

		source.print_board();
		int move_count = 0;
		while(move_count < 10){
			move_count += 1;

			if (source.getIntel().getGameStatus() == 1){
				System.out.println("Computer Wins");
				return;
			}

			if (source.getIntel().getGameStatus() == 2 || move_count == 10){
				System.out.println("Game Tied");
				return;
			}

			if((source.firstPlayer == 1 && move_count % 2 ==1) || (source.firstPlayer == 0 && move_count % 2 ==0)){
				// Human Turn
				System.out.println("Your Turn: Enter row and column separated by space");
				int row_num = sc.nextInt() - 1;
				int col_num = sc.nextInt() - 1;

				while(source.board.get(row_num).get(col_num)!=0 || row_num > 3 || row_num < 1 || col_num > 3 || col_num < 1){
					System.out.println("Enter valid coordinates");
					row_num = sc.nextInt() - 1;
					col_num = sc.nextInt() - 1;
				}

				int XorO = 0;
				if (source.firstPlayer == 1)
					XorO = 1;
				else XorO = 2;
				source.board.get(row_num).set(col_num,XorO);

				int index_bool_list = source.getIntel().getMagicCube().getCubeArray().get(row_num).get(col_num);
				source.getIntel().append_player_moves(index_bool_list);
				source.getIntel().set_magicSquareArray(index_bool_list);
				source.print_board();
				continue;
			}

			else{
				// Computer's Turn

				int move_index = source.getIntel().play_a_move();
				source.getIntel().append_comp_moves(move_index);
				ArrayList<Integer> row_col = source.getIntel().getBoardCoordi(move_index);

				int row_num = row_col.get(0);
				int col_num = row_col.get(1);
				int XorO = 0;
				if (source.firstPlayer == 1)
					XorO = 2;
				else XorO = 1;
				source.board.get(row_num).set(col_num,XorO);
				System.out.println("Computer Plays");
				source.print_board();
				continue;
			}
		}

	}
}
