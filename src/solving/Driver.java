package solving;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static Scanner sc = new Scanner(System.in);
	public static Set all = new Set("Master", 9);
	public static ArrayList<Set> sets = new ArrayList<Set>();
	public static void main(String[] args) {
		boolean legal = true;
		for(int i = 1; i <= 9; i++){
			Set temp = new Set("Row " + i, 9);
			for(int j = 1; j <= 9; j++){
				Tile t = new Tile(j, i, sc.nextInt(),9);
				all.addTile(t);
				temp.addTile(t);
			}
			sets.add(temp.clone());
		}
		for(int i = 1; i <= 9; i++){
			Set temp = new Set("Column " + i, 9);
			for(int j = 1; j <= 9; j++){
				Tile t = all.getTileAt(i, j);
				temp.addTile(t);
			}
			sets.add(temp.clone());
		}
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Set temp = new Set("Box "+i+" "+j, 9);
				for(int k = 1; k <= 3; k++){
					for(int l = 1; l <= 3; l++){
						Tile t = all.getTileAt(i*3+k, j*3+l);
						temp.addTile(t);
					}
				}
				sets.add(temp.clone());
			}
		}
		for(Set s: sets){
			//System.out.println("Setting " + s.getName());
			legal = s.updateAll();
			if(!legal){
				System.out.println("Illegal Setup");
				break;
			}
			//System.out.println("Set " + s.getName());
		}
		printAll();
		System.out.println("Set Up Finished");
		int count=0;
		while(legal && ++count<=6){
			System.out.println(count);
			
			if(count == 1)
				all.checkAll();
			for(Set s: sets){
				legal = s.updateAll();
				if(legal)
					legal = s.checkForOnly();
				if(!legal)
					break;
			}
			all.checkAll();
			if(!legal)
				System.out.println("Illegal Setup");
			if(legal)
				legal=cont();
			printAll();
		}
	}
	private static boolean cont(){
		for(int i = 1; i <= 9; i++)
			for(int j = 1; j <= 9; j++)
				if(all.getTileAt(i, j).getValue()==0)
					return true;
		return false;
	}
	private static void printAll(){
		for(int i = 1; i <= 9; i++){
			for(int j = 1; j <= 9; j++)
				System.out.print(all.getTileAt(j, i).getValue()+" ");
			System.out.println();
		}
	}
}
