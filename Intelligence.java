import java.io.*;
import java.util.*;
import java.lang.Math;

public class Intelligence
{
	private ArrayList<Boolean> magicSquareArray;
	private ArrayList<Integer> list_of_player_moves;
	private ArrayList<Integer> list_of_comp_moves;
	private MagicCube magicCube;
	private int gameStatus; // 0 for 'in process' and 1 for win and 2 for tie

	public Intelligence(){
		magicCube = new MagicCube(3);
		int total_elem_num = (int)Math.pow(magicCube.getOrder(),2) + 1;
		magicSquareArray = new ArrayList<Boolean>(total_elem_num);
		for (int i = 0; i < total_elem_num; i++) {
		  magicSquareArray.add(false);
		}
		list_of_player_moves = new ArrayList<Integer>();
		list_of_comp_moves = new ArrayList<Integer>();
		gameStatus = 0;

	}

	public void set_magicSquareArray(int i){this.magicSquareArray.set(i,true);}

	public MagicCube getMagicCube() {return magicCube;}

	public void append_player_moves(int i){	this.list_of_player_moves.add(i);}

	public void append_comp_moves(int i){this.list_of_comp_moves.add(i);}

	public int getGameStatus(){return gameStatus;}

	public ArrayList<Integer> getBoardCoordi(int index){
		ArrayList<Integer> row_col = new ArrayList<Integer>(2);
		for(int i=0;i<3;i++){
			for(int j =0;j<3;j++){
				if(magicCube.getCubeArray().get(i).get(j) == index){
					row_col.add(i);
					row_col.add(j);
				}
			}
		}
		return row_col;
	}

	public int play_a_move(){
		int move_index;

		move_index = self_win();
		if ( move_index != -1 ){
			this.gameStatus = 1;
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = opp_win();
		if ( move_index != -1){
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = centre_move();
		if ( move_index != -1){
			magicSquareArray.set(move_index,true);
			return move_index;
		}

		move_index = side_move();
		if ( move_index != -1){
			magicSquareArray.set(move_index,true);
			return move_index;
		}
		
		move_index = corner_move();
		if ( move_index != -1){
			magicSquareArray.set(move_index,true);
			return move_index;
		}



		this.gameStatus = 2;
		return move_index;
	}

	private int self_win(){
		ArrayList<Integer> list_of_comp_sums = get_sum_list(0);
		if (list_of_comp_sums.get(0) == -1)
			return -1;
		Iterator<Integer> it_comp = list_of_comp_sums.listIterator();
		while ((it_comp.hasNext())){
			int diff = 15 - (it_comp.next());
			if (diff <= 0 || diff > 9 ){
				continue;
			}
			if (this.magicSquareArray.get(diff))
				continue;
			return diff;
		}
		return -1;
	}

	private int opp_win(){
		ArrayList<Integer> list_of_player_sums = get_sum_list(1);
		if (list_of_player_sums.get(0) == -1)
			return -1;
		Iterator<Integer> it_player = list_of_player_sums.listIterator();
		while ((it_player.hasNext())){
			int diff = 15 - (it_player.next());
			if (diff <= 0 || diff > 9 ){
				continue;
			}
			if (this.magicSquareArray.get(diff))
				continue;
			return diff;
		}
		return -1;
	}

	private int centre_move(){
		int centre_index = this.magicCube.getCubeArray().get(1).get(1);
		if (!this.magicSquareArray.get(centre_index))
			return centre_index;
		return -1;
	}

	private int corner_move(){
		ArrayList<Integer> corner_indices = new ArrayList<Integer>(4);
		corner_indices.add(magicCube.getCubeArray().get(0).get(0));
		corner_indices.add(magicCube.getCubeArray().get(0).get(2));
		corner_indices.add(magicCube.getCubeArray().get(2).get(2));
		corner_indices.add(magicCube.getCubeArray().get(2).get(0));
		Iterator<Integer> it_corner = corner_indices.listIterator();
		while (it_corner.hasNext()){
			int corner_index = it_corner.next();
			if (this.magicSquareArray.get(corner_index))
				continue;
			return corner_index;
		}
		return  -1;
	}

	private int side_move(){
		ArrayList<Integer> side_indices = new ArrayList<Integer>(4);
		side_indices.add(magicCube.getCubeArray().get(0).get(1));
		side_indices.add(magicCube.getCubeArray().get(1).get(2));
		side_indices.add(magicCube.getCubeArray().get(2).get(1));
		side_indices.add(magicCube.getCubeArray().get(1).get(0));
		Iterator<Integer> it_side = side_indices.listIterator();
		while (it_side.hasNext()){
			int side_index = it_side.next();
			if (this.magicSquareArray.get(side_index))
				continue;
			return side_index;
		}
		return -1;
	}

	private ArrayList<Integer> get_sum_list(int i){
		// i = 0 for comp and i = 1 for player
		ArrayList<Integer> input_list;
		ArrayList<Integer> output_list = new ArrayList<Integer>();
		if(i == 0)
			input_list = this.list_of_comp_moves;
		else
			input_list = this.list_of_player_moves;

		int input_size = input_list.size();
		if (input_size == 1 || input_size == 0){
			output_list.add(-1);
			return output_list;
		}

		Iterator<Integer> input_it = input_list.listIterator();
		int last_elem = input_list.get(input_size - 1);
		while (input_it.hasNext()){
			int num = input_it.next();
			if(num == last_elem)
				break;
			output_list.add(num + last_elem);
		}
		return output_list;
	}
}

class MagicCube{
	private ArrayList<ArrayList<Integer>> magic_cube;
	private int order;

	public MagicCube(int order){

		this.order = order;
		magic_cube = new ArrayList<ArrayList<Integer>>(order);
		for(int i = 0; i < order; i++)  {
			magic_cube.add(new ArrayList<Integer>());
			for(int j = 0; j < order; j++){
				magic_cube.get(i).add(0);
			}
		}
		this.fillSquare();
	}

	public ArrayList<ArrayList<Integer>> getCubeArray(){
		return magic_cube;
	}

	public int getOrder(){
		return order;
	}

	public void print_cube(){
		for(int i = 0;i<3;i++){
			for(int j=0;j<3;j++){
				System.out.print(magic_cube.get(i).get(j));
				System.out.print(' ');
			}
			System.out.println();
		}
	}

	private void fillSquare(){
		int n_row = 0,n_col = 0;

		for(int i =1; i < Math.pow(this.order,2)+1;i++) {
			if (i == 1) {
				n_row = order / 2;
				n_col = order - 1;
				this.magic_cube.get(n_row).set(n_col, i);
				continue;
			}

			n_row = (n_row - 1) % order;
			if ( n_row < 0 )
				n_row += order;
			n_col = (n_col + 1) % order;

			if (this.magic_cube.get(n_row).get(n_col) == 0) {
				this.magic_cube.get(n_row).set(n_col, i);
			}
			else{
				n_row = (n_row + 1) % order;
				n_col = (n_col - 2) % order;
				if(n_col<0)
					n_col += order;
				this.magic_cube.get(n_row).set(n_col, i);
			}
		}

	}
}
