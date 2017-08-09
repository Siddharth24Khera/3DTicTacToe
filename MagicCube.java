import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class MagicCube{
    private ArrayList<ArrayList<ArrayList<Integer>>> magic_cube; //represented as 3x3x3 array list of integers
    private ArrayList<ArrayList<Integer>> sum_good_list;
    private ArrayList<ArrayList<Integer>> sum_bad_list;
    private ArrayList<ArrayList<Integer>> not_sum_good_list;
    private int order;
    Hashtable<Integer,Tuple> hash;


    public MagicCube(int order){
        hash = new Hashtable<Integer,Tuple>();
        this.order = order;
        magic_cube = new ArrayList<ArrayList<ArrayList<Integer>>>(3);
        for(int i=0;i<order;i++){
            magic_cube.add(new ArrayList<ArrayList<Integer>>(3));
            for(int j=0;j<order;j++){
                magic_cube.get(i).add(new ArrayList<Integer>(3));
                for(int k=0;k<order;k++){
                    magic_cube.get(i).get(j).add(0);
                }
            }
        }
        this.fillCube(); //fills the cube
        sum_good_list = new ArrayList<ArrayList<Integer>>(); //generates a list of all tuples with sum 42, and are collinear
        sum_bad_list = new ArrayList<ArrayList<Integer>>(); //generates a list of all tuples which are not collinear with sum 42
        not_sum_good_list = new ArrayList<ArrayList<Integer>>();//list with collinear tuples and sum is not 42
        generateLists();
    }

    public ArrayList<ArrayList<ArrayList<Integer>>> getMagic_cube(){
        return magic_cube;
    }

    public ArrayList<ArrayList<Integer>> getSum_good_list(){
        return sum_good_list;
    }

    public ArrayList<ArrayList<Integer>> getSum_bad_list(){
        return sum_bad_list;
    }

    public ArrayList<ArrayList<Integer>> get_not_sum_good_list(){
        return not_sum_good_list;
    }

    public int getOrder(){
        return order;
    }

    public void print_cube(){ //prints all the 11 surfaces of the magic cube
        System.out.println("Printing the top three layers of the magic cube\n");
        for(int i=0;i<order;i++){
            System.out.println("Layer "+ (i+1));
            for(int j=0;j<order;j++){
                for(int k=0;k<order;k++){
                    System.out.print(magic_cube.get(i).get(j).get(k)+" ");
                }
                System.out.println();
            }
            System.out.println();
        }

        System.out.println("Printing the left three layers of the magic cube\n");
        for(int k=0;k<order;k++){
            System.out.println("Layer "+ (k+1));
            for(int j=0;j<order;j++){
                for(int i=0;i<order;i++){
                    System.out.print(magic_cube.get(j).get(i).get(k)+" ");
                }
                System.out.println();
            }
            System.out.println();
        }

        System.out.println("Printing the front three layers of the magic cube\n");
        for(int k=0;k<order;k++){
            System.out.println("Layer "+ (k+1));
            for(int j=0;j<order;j++){
                for(int i=0;i<order;i++){
                    System.out.print(magic_cube.get(j).get(2-k).get(i)+" ");
                }
                System.out.println();
            }
            System.out.println();
        }

        System.out.println("Printing the diagonals of the magic cube\n");
        System.out.println("Diagonal 1");
        for(int i=0;i<order;i++){
            for(int j=0;j<order;j++){
                System.out.print(magic_cube.get(i).get(j).get(j)+" ");
            }
            System.out.println();
        }

        System.out.println("\nDiagonal 2");
        for(int i=0;i<order;i++){
            for(int j=0;j<order;j++){
                System.out.print(magic_cube.get(i).get(2-j).get(j)+" ");
            }
            System.out.println();
        }
        System.out.println();

    }

    //fills the cube according to the algorithm
    private void fillCube(){ 
        int n_row = order/2, n_col = order/2, n_ht = 0;
        for(int i =0; i < Math.pow(this.order,3);i++) {
            magic_cube.get(n_ht).get(n_row).set(n_col, i+1);
            hash.put(i+1,new Tuple(n_ht,n_row,n_col));
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
    
    //adjust method - sets the values in the range from 0 to n by incrementing or decremting correspondingly
    private int adjust(int order, int num){
        while(num<0)
            num+=order;
        while(num>order-1)
            num-=order;
        return num;
    }

    //Checks collinearity using a 3D concept - direction cosines must be parallel
    private boolean check_collinearity(Tuple one, Tuple two, Tuple three){
        int x_1 = one.get_x() - three.get_x();
        int x_2 = one.get_y() - three.get_y();
        int x_3 = one.get_z() - three.get_z();
        int y_1 = two.get_x() - one.get_x();
        int y_2 = two.get_y() - one.get_y();
        int y_3 = two.get_z() - one.get_z();
        int sum1 = (int)(Math.pow(((x_1*y_1)+(x_2*y_2)+(x_3*y_3)),2));
        int sum2 = ((x_1*x_1)+(x_2*x_2)+(x_3*x_3))*((y_1*y_1)+(y_2*y_2)+(y_3*y_3));
        if(sum1 == sum2)
            return true;
        return false;
    }
    
    //geenrate all the three lists required
    //maintained a hash table to get the coordinates and corresponding number on the cube in O(1) time
    private void generateLists(){
        for(int i=1;i<26;i++){
            for(int j=i+1;j<27;j++){
                for(int k=j+1;k<28;k++){
                    ArrayList<Integer> al = new ArrayList<Integer>();
                    al.add(i);
                    al.add(j);
                    al.add(k);
                    Collections.sort(al);
                    Tuple coord1 = hash.get(i);
                    Tuple coord2 = hash.get(j);
                    Tuple coord3 = hash.get(k);
                    boolean b = (check_collinearity(coord1,coord2,coord3));
                    if((i+j+k)==42){
                        if(b)	sum_good_list.add(al);
                        else 	sum_bad_list.add(al);
                    }
                    else if(b)	not_sum_good_list.add(al); //end of if-else construct
                }//end of k-loop
            }//end of j-loop
        } //end of i-loop
    }//end of method
}

//tuple class that stores a tuple of 3 numbers
class Tuple {
    int i;
    int j;
    int k;

    Tuple() {
        i = -1;
        j = -1;
        k = -1;
    }

    Tuple(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public int get_x() {
        return i;
    }

    public int get_y() {
        return j;
    }

    public int get_z() {
        return k;
    }
}
