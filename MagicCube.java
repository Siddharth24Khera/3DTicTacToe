import java.io.*;
import java.util.*;
class MagicCube{
	private ArrayList<ArrayList<ArrayList<Integer>>> magic_cube;
	private int order;

	public MagicCube(int order){
		this.order = order;
		magic_cube = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i=0;i<order;i++){
			magic_cube.add(new ArrayList<ArrayList<Integer>>());
			for(int j=0;j<order;j++){
				magic_cube.get(i).add(new ArrayList<Integer>());
				for(int k=0;k<order;k++){
					magic_cube.get(i).get(j).add(0);
				}
			}
		}
		this.fillCube();
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> getMagic_cube(){
		return magic_cube;
	}

	public int getOrder(){
		return order;
	}

	public void print_cube(){
		System.out.println("Printing the top three layers of the magic cube");
		for(int k=0;k<order;k++){
			for(int i=0;i<order;i++){
				for(int j=0;j<order;j++){
					System.out.print(magic_cube.get(k).get(i).get(j)+" ");
				}
				System.out.println();
			}
			System.out.println();
		}	
	}

	private void fillCube(){
		int n_row = order/2, n_col = order/2, n_ht = 0;
		for(int i =0; i < Math.pow(this.order,3);i++) {
			magic_cube.get(n_ht).get(n_row).set(n_col, i+1);
			n_ht = adjust(order,--n_ht);
			n_col = adjust(order,--n_col);
			if(magic_cube.get(n_ht).get(n_row).get(n_col)!=0){
				n_row = adjust(order,--n_row);
				n_col = adjust(order,++n_col);
				if (magic_cube.get(n_ht).get(n_row).get(n_col)!=0){
					n_row = adjust(order,++n_row);
					n_ht = adjust(order, n_ht+2);
				}
			}
		}
	}

	private int adjust(int order, int num){
		while(num<0)
			num+=order;
		while(num>order-1)
			num-=order;
		return num;
	}

	public static void main(String args[]){
		MagicCube magicCube = new MagicCube(3);
		magicCube.print_cube();
	}

}