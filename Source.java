//This is a source file
import java.util.ArrayList;
import java.util.Scanner;

public class Source  
{
	private Intelligence intel;
	private int firstPlayer; // 0 for computer 1 for human
	private ArrayList<ArrayList<ArrayList<Integer>>> board; // 0 for unoccupied; 1 for X; 2 for O

	public Source(){
		intel = new Intelligence();
		firstPlayer = 0;

		board = new ArrayList<ArrayList<ArrayList<Integer>>>(3);
		for(int i=0;i<3;i++){
			board.add(new ArrayList<ArrayList<Integer>>(3));
			for(int j=0;j<3;j++){
				board.get(i).add(new ArrayList<Integer>(3));
				for(int k=0;k<3;k++){
					board.get(i).get(j).add(0);
				}
			}
		}
	}

	public Intelligence getIntel(){
		return intel;
	}

	private void print_board(){
		for(int i=0;i<3;i++){
			System.out.println();
			System.out.println("------");
			for(int j = 0; j < 3; j++)  {
				for(int k = 0; k < 3; k++){
					int num = this.board.get(i).get(j).get(k);
					if(num == 1)
						System.out.print("X");
					else if(num == 2)
						System.out.print("O");
					else System.out.print(" ");

					if(k==0 || k ==1)
						System.out.print("|");
				}

				System.out.println();
			}
			System.out.println("------");

		}

	}

	public static void main (String args [])
	{
		Source source = new Source();
		source.getIntel().getMagicCube().print_cube();
		System.out.println(source.getIntel().getMagicCube().get_not_sum_good_list());
		System.out.println(source.getIntel().getMagicCube().getSum_bad_list());
		System.out.println(source.getIntel().getMagicCube().getSum_good_list());
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
		while(move_count < 28){
			move_count += 1;

			if (source.getIntel().getGameStatus() == 1){
				System.out.println("Computer Wins");
				return;
			}

			if (source.getIntel().getGameStatus() == 2 || move_count == 28){
				System.out.println("Game Tied");
				return;
			}

			if((source.firstPlayer == 1 && move_count % 2 ==1) || (source.firstPlayer == 0 && move_count % 2 ==0)){
				// Human Turn
				System.out.println("");
				System.out.println("Your Turn: Enter layer, row and column separated by space");
				int lay_num = sc.nextInt() - 1;
				int row_num = sc.nextInt() - 1;
				int col_num = sc.nextInt() - 1;

				while(lay_num>2||lay_num<0||row_num>2||row_num<0||col_num>2||col_num<0||source.board.get(lay_num).get(row_num).get(col_num)!=0){
					System.out.println("Enter valid coordinates");
					lay_num = sc.nextInt() - 1;
					row_num = sc.nextInt() - 1;
					col_num = sc.nextInt() - 1;
				}

				int XorO = 0;
				if (source.firstPlayer == 1)
					XorO = 1;
				else XorO = 2;
				source.board.get(lay_num).get(row_num).set(col_num,XorO);

				int index_bool_list = source.getIntel().getMagicCube().getMagic_cube().get(lay_num).get(row_num).get(col_num);
				source.getIntel().append_player_moves(index_bool_list);
				source.getIntel().set_magicSquareArray(index_bool_list);
				source.print_board();
				continue;
			}

			else{
				// Computer's Turn

				int move_index = source.getIntel().play_a_move();
				source.getIntel().append_comp_moves(move_index);
				ArrayList<Integer> layer_row_col = source.getIntel().getBoardCoordinates(move_index);

				int lay_num = layer_row_col.get(0);
				int row_num = layer_row_col.get(1);
				int col_num = layer_row_col.get(2);
				int XorO = 0;
				if (source.firstPlayer == 1){
					XorO = 2;
					System.out.println("Computer Plays O");
				}
				else{
					XorO = 1;
					System.out.println("Computer Plays X");
				}
				source.board.get(lay_num).get(row_num).set(col_num,XorO);

				source.print_board();
				continue;
			}
		}

	}
}
