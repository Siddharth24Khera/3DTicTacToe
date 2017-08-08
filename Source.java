//This is a source file
import java.util.ArrayList;
import java.util.Scanner;

public class Source  
{
	private Intelligence intel;
	private int firstPlayer; // 0 for computer 1 for human
	private ArrayList<ArrayList<ArrayList<Integer>>> board; // 0 for unoccupied; 1 for X; 2 for O
	private int computerWins;
	private int playerWins;
	private int gameStrategy;

	public Source(){
		intel = new Intelligence();
		firstPlayer = 2;
		computerWins = 0;
		playerWins = 0;
		gameStrategy = 0;

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
//		System.out.println(source.getIntel().getMagicCube().get_not_sum_good_list());
//		System.out.println(source.getIntel().getMagicCube().getSum_bad_list());
//		System.out.println(source.getIntel().getMagicCube().getSum_good_list());
		System.out.println("Welcome to 3DTicTacToe");
		System.out.println();
		System.out.println("The game comes with three winning strategies");
		System.out.println("Strategy 1 : Generate a collinear line");
		System.out.println("Strategy 2 : Generate two collinear lines");
		System.out.println("Strategy 3 : Generate as many collinear line by the end of 20th move");
		System.out.println("Enter 1 for strategy 1, 2 for strategy 2 and 3 for strategy 3");
		Scanner sc = new Scanner(System.in);
		source.gameStrategy = sc.nextInt();
		if(source.gameStrategy<1 || source.gameStrategy>3){
			System.out.println("Enter valid number");
		}
		System.out.println();
		System.out.println("Enter 1 for playing first or 2 for playing second");
		int input_num = sc.nextInt();
		if(input_num<1 || input_num>2){
			System.out.println("Enter valid number");
		}
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
				source.computerWins += 1;
				source.getIntel().setGameStatus(0);
				System.out.println("Computer Wins " + source.computerWins +" time");

			}


			ArrayList<Integer> loss_position_list = source.getIntel().getList_of_possible_loss();
			if(loss_position_list.size() != 0){
				//System.out.println(loss_position_list);
				for(int i = 0;i<loss_position_list.size();i++){
					int j = loss_position_list.get(i);
					ArrayList<Integer> coordinates = source.getIntel().getBoardCoordinates(j);
					//System.out.println(source.firstPlayer);
					//if(source.getIntel().getMagicSquareArray().get(j)){
					if(source.board.get(coordinates.get(0)).get(coordinates.get(1)).get(coordinates.get(2)) == source.firstPlayer){
						source.playerWins += 1;
						loss_position_list.remove(i);
						//System.out.println("Winning Line: "+);
						System.out.println("You Win "+ source.playerWins +" time");
					}
					if(source.board.get(coordinates.get(0)).get(coordinates.get(1)).get(coordinates.get(2)) == 3 - source.firstPlayer){
						loss_position_list.remove(i);
					}
				}
			}

			if(source.gameStrategy == 1){
				if(source.computerWins == 1){
					System.out.println("Computer is the True Winner");
					return;
				}
				if(source.playerWins == 1){
					System.out.println("You are the True Winner");
					return;
				}
			}

			if(source.gameStrategy == 2){
				if(source.computerWins == 2){
					System.out.println("Computer is the True Winner");
					return;
				}
				if(source.playerWins == 2){
					System.out.println("You are the True Winner");
					return;
				}
			}

			if(source.gameStrategy == 3){
				if(move_count == 21){
					System.out.println();
					System.out.println("Twenty moves are over now");
					System.out.println("Human won "+source.playerWins+" time");
					System.out.println("Computer won "+source.computerWins+" time");
					if(source.playerWins > source.computerWins){
						System.out.println("You are the True Winner");
						return;
					}
					else if(source.playerWins < source.computerWins){
						System.out.println("Computer is the True Winner");
						return;
					}
					else{
						System.out.println("This game is a tie");
						return;
					}
				}
			}

			if (source.getIntel().getGameStatus() == 2 || move_count == 28){
				System.out.println("Game Tied");
				return;
			}
			System.out.println();
			System.out.println("Move Number: "+move_count);
			System.out.println("Computer Score: "+source.computerWins);
			System.out.println("Player Score: "+source.playerWins);

			if((source.firstPlayer == 1 && move_count % 2 ==1) || (source.firstPlayer == 2 && move_count % 2 ==0)){
				// Human Turn
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
			}

			else{
				// Computer's Turnk

				int move_index = source.getIntel().play_a_move();
				//System.out.println(move_index);
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
				System.out.println("Position played: "+(lay_num+1)+' '+(row_num+1)+' '+(col_num+1));
				source.board.get(lay_num).get(row_num).set(col_num,XorO);

				source.print_board();
			}
		}

	}
}
