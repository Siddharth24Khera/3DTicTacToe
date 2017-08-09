import java.util.ArrayList;
import java.util.Scanner;

/****************************************************************************************************************************
 * Assignment 1 : COL333 3D TicTacToe
 * Creators: 2015MT60567,Siddharth Khera  && 2015CS50278,Ankur Sharma
 * We have implemented all three strategies described in the problem statement, which can be chosen by the user at runtime.
 * The program comes with a visual board for easy visualization and input from human are taken via indexed positions of the visual board.
 * The computer plays with the following tactic in order of preference, which can be reordered for unpredictability.
 * 1. Self Win
 * 2. Block Opp Win
 * 3. Go for Cube centre
 * 4. Go for face centres ( randomly choosing an empty one)
 * 5. Go for Cube corners
 * 6. Go for edge centres
 */

public class Source  
{
	/****************************************************************************************************************************
	 * This class contains the main functions and works as the main script ,creating object for the Intelligence class.
	 * It also manages the visual cmd commands, printing the board and managing moves called by the intelligent agent and the human
	 */
	private Intelligence intel;
	private int firstPlayer; // 0 for computer 1 for human
	private ArrayList<ArrayList<ArrayList<Integer>>> board; // 0 for unoccupied; 1 for X; 2 for O
	private int computerWins;
	private int playerWins;
	private int gameStrategy;		// 1 for one line;2 for 2 lines; 3 for most lines till move 21

	public Source(){
		intel = new Intelligence(); // Class defining all the working and functioning of the agent
		firstPlayer = 2;
		computerWins = 0;
		playerWins = 0;
		gameStrategy = 0;

		board = new ArrayList<ArrayList<ArrayList<Integer>>>(3);
		// Initiating the board with 0s i.e. blanks
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
		// Function that prints the three layer in top to bottom fashion of the 3D cube
		// Called after each chance with updated X and O
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
		// Uncomment the following three lines to view the three lists of exceptions formed
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
			//Input Validation
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

		// Moves that go till move 27 in the case of absolute tie
		while(move_count < 28){
			move_count += 1;

			if (source.getIntel().getGameStatus() == 1){
				// Game status is changed by the intelligence class on encountering a winning situation and filling it.
				source.computerWins += 1;
				source.getIntel().setGameStatus(0);
				System.out.println("Computer Wins " + source.computerWins +" time");

			}


			ArrayList<Integer> loss_position_list = source.getIntel().getList_of_possible_loss();
			/* A loss list is maintained by thr intelligence wherein it stores all the possible locations where human can play to obtain a win
			*  It is updated after each turn; adding more locations and pruning out the ones that are blocked
			*/
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
						System.out.println("You Win "+ source.playerWins +" time");
					}
					if(source.board.get(coordinates.get(0)).get(coordinates.get(1)).get(coordinates.get(2)) == 3 - source.firstPlayer){
						loss_position_list.remove(i);
					}
				}
			}

			if(source.gameStrategy == 1){
				// Strategy 1 : First to get a collinear line wins; Program returns after declaring winner
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
				// Strategy 2: First to get two collinear lines win
				// Statement checked after every move
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
				// Strategy 3: At move == 21, the program returns after declaring te winner on the basis of comparison of player and computer wins.
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
				// gameStatus changed to 2 by intel if situation ends at tie
				System.out.println("Game Tied");
				return;
			}
			System.out.println();
			System.out.println("Move Number: "+move_count);
			System.out.println("Computer Score: "+source.computerWins);
			System.out.println("Player Score: "+source.playerWins);

			if((source.firstPlayer == 1 && move_count % 2 ==1) || (source.firstPlayer == 2 && move_count % 2 ==0)){
				// This if statement handles Human Turn
				System.out.println("Your Turn: Enter layer, row and column separated by space");
				int lay_num = sc.nextInt() - 1;
				int row_num = sc.nextInt() - 1;
				int col_num = sc.nextInt() - 1;

				while(lay_num>2||lay_num<0||row_num>2||row_num<0||col_num>2||col_num<0||source.board.get(lay_num).get(row_num).get(col_num)!=0){
					//Input Validation
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

				// Updates the information of human move to the intelligence class
				int index_bool_list = source.getIntel().getMagicCube().getMagic_cube().get(lay_num).get(row_num).get(col_num);
				source.getIntel().append_player_moves(index_bool_list);
				source.getIntel().set_magicSquareArray(index_bool_list);

				source.print_board();
			}

			else{
				// Computer's Turn

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
