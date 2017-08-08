import java.io.*;
import java.util.*;
import java.lang.Math;
import java.util.Collections;

public class Intelligence
{
	private ArrayList<Boolean> magicSquareArray;
	private ArrayList<Integer> list_of_player_moves;
	private ArrayList<Integer> list_of_comp_moves;
	private ArrayList<Integer> list_of_possible_loss;
	private ArrayList<Integer> list_of_possible_wins;
	private MagicCube magicCube;
	private int gameStatus; // 0 for 'in process' and 1 for win and 2 for tie

	public Intelligence(){
		magicCube = new MagicCube(3);
		int total_elem_num = (int)Math.pow(magicCube.getOrder(),3) + 1;
		magicSquareArray = new ArrayList<Boolean>(total_elem_num);
		for (int i = 0; i < total_elem_num; i++) {
		  magicSquareArray.add(false);
		}
		list_of_player_moves = new ArrayList<Integer>();
		list_of_comp_moves = new ArrayList<Integer>();
		list_of_possible_loss = new ArrayList<Integer>();
		gameStatus = 0;

	}

	public void set_magicSquareArray(int i){this.magicSquareArray.set(i,true);}

	public MagicCube getMagicCube() {return magicCube;}

	public ArrayList<Integer> getList_of_possible_loss() {return list_of_possible_loss;	}

	public ArrayList<Integer> getList_of_possible_wins() {return list_of_possible_wins;	}

	public ArrayList<Boolean> getMagicSquareArray() {return magicSquareArray; }

	public void append_player_moves(int i){	this.list_of_player_moves.add(i);}

	public void append_comp_moves(int i){this.list_of_comp_moves.add(i);}

	public int getGameStatus(){return gameStatus;}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public ArrayList<Integer> getBoardCoordinates(int index){
		ArrayList<Integer> layer_row_col = new ArrayList<Integer>(2);
		for(int i=0;i<3;i++){
			for(int j =0;j<3;j++){
				for(int k=0;k<3;k++){
					if(magicCube.getMagic_cube().get(i).get(j).get(k) == index){
						layer_row_col.add(i);
						layer_row_col.add(j);
						layer_row_col.add(k);
					}
				}
			}
		}
		return layer_row_col;
	}

	public int play_a_move(){
		int move_index;
		move_index = check_win(0);
		if ( move_index != -1 ){
			System.out.println("Computer's Winning Move");
			this.gameStatus = 1;
			int updateLossList = check_win(2);
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = check_win(1);
		if ( move_index != -1){
			System.out.println("Computer's Blocking Move");
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = cube_centre_move();
		if ( move_index != -1){
			System.out.println("Computer's Cube Centre Move");
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = face_centre_move();
		if ( move_index != -1){
			System.out.println("Computer's Face Centre Move");
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = cube_corner_move();
		if ( move_index != -1){
			System.out.println("Computer's Cube Corner Move");

			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = edge_centre_move();
		if ( move_index != -1){
			System.out.println("Computer's Edge Centre Move");
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		this.gameStatus = 2;
		return move_index;
	}

	private int check_win(int compOrHum){
		// 0 for comp 1 for Human
		boolean isCalledFromWin = false;
		if (compOrHum == 2){
			isCalledFromWin = true;
			compOrHum = 1;
		}



		ArrayList<ArrayList<Integer>> list_of_sums = get_sum_tuples_list(compOrHum);
		//System.out.println(list_of_sums);
		ArrayList<Integer> falseArrayList = new ArrayList<Integer>(2);
		falseArrayList.add(-1);falseArrayList.add(-1);
		if (isEqual_ArrayList(list_of_sums.get(0),falseArrayList)){
			return -1;
		}


		Iterator<ArrayList<Integer>> it = list_of_sums.listIterator();
		while ((it.hasNext())){
			ArrayList<Integer> sum_tuple = it.next();

			// handle not sum good list
			int not_sum_good_index = get_not_42_good_list_index(sum_tuple);
			if(not_sum_good_index != -1){
				if (this.magicSquareArray.get(not_sum_good_index))
					continue;
				if(compOrHum == 1) {
					list_of_possible_loss.add(not_sum_good_index);
					continue;
				}
				else{
					System.out.println();
					System.out.println();
					System.out.println("Computer wins the following line:");
					ArrayList<Integer> boardCoord1 = getBoardCoordinates(sum_tuple.get(0));
					ArrayList<Integer> boardCoord2 = getBoardCoordinates(sum_tuple.get(1));
					ArrayList<Integer> boardCoord3 = getBoardCoordinates(not_sum_good_index);
					System.out.print("["+(boardCoord1.get(0)+1)+','+(boardCoord1.get(1)+1)+','+(boardCoord1.get(2)+1)+"] ");
					System.out.print("["+(boardCoord2.get(0)+1)+','+(boardCoord2.get(1)+1)+','+(boardCoord2.get(2)+1)+"] ");
					System.out.println("["+(boardCoord3.get(0)+1)+','+(boardCoord3.get(1)+1)+','+(boardCoord3.get(2)+1)+"] ");
					return not_sum_good_index;
				}
			}

			int diff = 42 - sum_tuple.get(0) - sum_tuple.get(1);

			if (diff <= 0 || diff > 27 ){
				continue;
			}
			ArrayList<Integer> triplet = new ArrayList<Integer>(3);
			triplet.add(sum_tuple.get(0));triplet.add(sum_tuple.get(1));triplet.add(diff);
			Collections.sort(triplet);
			boolean flag = false;
			for ( ArrayList<Integer> bad_tuple_42 : magicCube.getSum_bad_list()){
				if(isEqual_ArrayList(bad_tuple_42,triplet)) {
					flag= true;
				}
			}
			if(flag){
				continue;
			}

			if (this.magicSquareArray.get(diff))
				continue;
			if(compOrHum == 1) {
				list_of_possible_loss.add(diff);

			}
			else {
				System.out.println();
				System.out.println();
				System.out.println("Computer wins the following line:");
				ArrayList<Integer> boardCoord1 = getBoardCoordinates(sum_tuple.get(0));
				ArrayList<Integer> boardCoord2 = getBoardCoordinates(sum_tuple.get(1));
				ArrayList<Integer> boardCoord3 = getBoardCoordinates(diff);
				System.out.print("["+(boardCoord1.get(0)+1)+','+(boardCoord1.get(1)+1)+','+(boardCoord1.get(2)+1)+"] ");
				System.out.print("["+(boardCoord2.get(0)+1)+','+(boardCoord2.get(1)+1)+','+(boardCoord2.get(2)+1)+"] ");
				System.out.println("["+(boardCoord3.get(0)+1)+','+(boardCoord3.get(1)+1)+','+(boardCoord3.get(2)+1)+"] ");
				return diff;
			}

		}

		if(compOrHum == 1 && list_of_possible_loss.size() != 0 && !isCalledFromWin){
			int blockIndex =  list_of_possible_loss.get(0);
			list_of_possible_loss.remove(0);
			return blockIndex;
		}

		return -1;

	}

	private int cube_centre_move(){
		int centre_index = this.magicCube.getMagic_cube().get(1).get(1).get(1);
		if (!this.magicSquareArray.get(centre_index))
			return centre_index;
		return -1;
	}

	private int face_centre_move(){
		ArrayList<Integer> face_centres = new ArrayList<Integer>(6);
		face_centres.add(this.magicCube.getMagic_cube().get(0).get(1).get(1));
		face_centres.add(this.magicCube.getMagic_cube().get(1).get(2).get(1));
		face_centres.add(this.magicCube.getMagic_cube().get(1).get(1).get(0));
		face_centres.add(this.magicCube.getMagic_cube().get(2).get(1).get(1));
		face_centres.add(this.magicCube.getMagic_cube().get(1).get(0).get(1));
		face_centres.add(this.magicCube.getMagic_cube().get(1).get(1).get(2));
		Collections.shuffle(face_centres);
		Iterator<Integer> it_face_centre = face_centres.listIterator();
		while (it_face_centre.hasNext()){
			int face_centre_index = it_face_centre.next();
			if (this.magicSquareArray.get(face_centre_index))
				continue;
			return face_centre_index;
		}
		return -1;
	}

	private int edge_centre_move(){
		ArrayList<Integer> edge_center_indices = new ArrayList<Integer>(12);
		for(int i = 1; i<27;i=i+2){
			if(i == 13)
				continue;
			int ternary = conver_to_base3(i);
			int layer = ternary/100;
			int row = (ternary % 100) / 10;
			int col = (ternary %10) / 10;
			edge_center_indices.add(magicCube.getMagic_cube().get(layer).get(row).get(col));
		}
		Collections.shuffle(edge_center_indices);
		Iterator<Integer> it_edge_center = edge_center_indices.listIterator();
		while (it_edge_center.hasNext()){
			int edge_center = it_edge_center.next();
			if (this.magicSquareArray.get(edge_center))
				continue;
			return edge_center;
		}
		return -1;
	}

	private int cube_corner_move(){
		ArrayList<Integer> corner_indices = new ArrayList<Integer>(4);
		corner_indices.add(magicCube.getMagic_cube().get(0).get(0).get(0));
		corner_indices.add(magicCube.getMagic_cube().get(0).get(0).get(2));
		corner_indices.add(magicCube.getMagic_cube().get(0).get(2).get(0));
		corner_indices.add(magicCube.getMagic_cube().get(0).get(2).get(2));
		corner_indices.add(magicCube.getMagic_cube().get(2).get(0).get(0));
		corner_indices.add(magicCube.getMagic_cube().get(2).get(0).get(2));
		corner_indices.add(magicCube.getMagic_cube().get(2).get(2).get(0));
		corner_indices.add(magicCube.getMagic_cube().get(2).get(2).get(2));
		Collections.shuffle(corner_indices);
		Iterator<Integer> it_corner = corner_indices.listIterator();
		while (it_corner.hasNext()){
			int corner_index = it_corner.next();
			if (this.magicSquareArray.get(corner_index))
				continue;
			return corner_index;
		}
		return  -1;
	}

	private ArrayList<ArrayList<Integer>> get_sum_tuples_list(int i)
	{
		// i = 0 for comp and i = 1 for player
		ArrayList<Integer> input_list;
		ArrayList<ArrayList<Integer>> output_list = new ArrayList<ArrayList<Integer>>();
		if(i == 0)
			input_list = this.list_of_comp_moves;
		else
			input_list = this.list_of_player_moves;

		int input_size = input_list.size();
		if (input_size == 1 || input_size == 0){
			ArrayList<Integer> falseArrayList = new ArrayList<Integer>(2);
			falseArrayList.add(-1);falseArrayList.add(-1);
			output_list.add(falseArrayList);
			return output_list;
		}

		Iterator<Integer> input_it = input_list.listIterator();
		int last_elem = input_list.get(input_size - 1);
		while (input_it.hasNext()){
			int num = input_it.next();
			if(num == last_elem)
				break;
			ArrayList<Integer> doublet = new ArrayList<Integer>(2);
			doublet.add(num); doublet.add(last_elem);
			output_list.add(doublet);
		}
		return output_list;
	}

	private boolean isEqual_ArrayList(ArrayList<Integer> l1, ArrayList<Integer> l2){
		//null checking
		if(l1==null && l2==null)
			return true;
		if((l1 == null && l2 != null) || (l1 != null && l2 == null))
			return false;

		if(l1.size() != l2.size())
			return false;
		return l1.equals(l2);
	}

	private int conver_to_base3(int num) {
		int ret = 0, factor = 1;
		while (num > 0) {
			ret += num % 3 * factor;
			num /= 3;
			factor *= 10;
		}
		return ret;
	}

	private int get_not_42_good_list_index(ArrayList<Integer> doublet){
		ArrayList<ArrayList<Integer>> full_list_of_triplets = magicCube.get_not_sum_good_list();
		// Return third index ; if none exist return -1
		for(int i=0;i<full_list_of_triplets.size();i++){
			ArrayList<Integer> current_triplet = full_list_of_triplets.get(i);
			int first = current_triplet.indexOf(doublet.get(0));
			int second = current_triplet.indexOf(doublet.get(1));
			if((first==-1) || (second==-1))	continue;
			int missing_index  = 3 - (first + second);
			return current_triplet.get(missing_index);
		}
		return -1;
	}
}
