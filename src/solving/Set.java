package solving;

import java.util.ArrayList;

public class Set {
	/**
	 * List of tiles that are in this set
	 */
	private ArrayList<Tile> tiles;
	/**
	 * Name of this Set. It is for organization/debugging purposes. 
	 */
	private String name;
	/**
	 * Maximum value of a tile
	 */
	private int maxVal;

	/**
	 * Constructs a set with a given name and maximum value for the tiles
	 * @param name - name of the set
	 * @param maximumValue - maximum value of the tiles
	 */
	public Set(String name, int maximumValue){
		this.tiles = new ArrayList<Tile>();
		this.name = name;
		this.maxVal = maximumValue;
	}
	/**
	 * 
	 * @return name of the set
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 
	 * @return size of the set
	 */
	public int getSize(){
		return this.tiles.size();
	}
	/**
	 * 
	 * @return All values of tiles. Should be unique for each tile
	 */
	private ArrayList<Integer> getTileValues(){
		ArrayList<Integer> toReturn = new ArrayList<>();
		for(Tile t: this.tiles)
			if(t.getValue() != 0)
				toReturn.add(t.getValue());
		return toReturn;
	}
	/**
	 * Adds the given tile to the set
	 * @param t - tile to be added to the set
	 */
	public void addTile(Tile t){
		//System.out.println("Adding to " + this.name + ": " + t.getX() + ", " + t.getY() + ": " + t.getValue());
		this.tiles.add(t);
	}
	/**
	 * 
	 * @param x the x position of the tile
	 * 
	 * @param y the y position of the tile
	 * 
	 * @return the tile at given x & y, null if no such tile in the set
	 */
	public Tile getTileAt(int x, int y){
		for(Tile t: this.tiles)
			if(t.getCol() == x && t.getRow() == y)
				return t;
		return null;
	}
	/**
	 * 
	 * @param t - input tile
	 * 
	 * @return true if input is in the set, otherwise false
	 */
	public boolean contains(Tile t){
		return this.tiles.contains(t);
	}
	/**
	 * Updates all tiles in relation to all the other tiles
	 * 
	 * @return false if an error occured, otherwise true
	 */
	public boolean updateAll(){
		boolean cont=true;
		for(Tile t:this.tiles){
			cont = updateBy(t);
			if(!cont)
				break;
		}
		if(!cont)
			System.out.println("Update all failed on " + this.name);
		return cont;
	}
	/**
	 * Makes all the other tiles in this set not be able to be the value of this tile
	 * 
	 * @param t input tile
	 * 
	 * @return false if two tiles have the same, non-zero value, otherwise true
	 */
	public boolean updateBy(Tile t){
		int val = t.getValue();
		if(val == 0)
			return true;
		for(Tile i:this.tiles)
			if(i!=t)
				if(i.getValue() == t.getValue()){
					//System.out.println("Conflict between "+i.getCol()+", "+i.getRow()+" and "+t.getCol()+", "+t.getRow());
					return false;
				}
				else
					i.cantBe(val);
		return true;
	}
	/**
	 * Checks if there is only one tile that can be one of the values, 
	 * if there is such tile(s) then attempts to set the tile(s) to their values. 
	 * 
	 * @return false if it cannot set a tile to its value, otherwise true
	 */
	public boolean checkForOnly(){
		boolean valid = true;
		//System.out.println("Checking " + this.name);
		for(int i = 1; i <= this.maxVal && valid; i++){
			int count = 0;
			Tile t = null;
			for(Tile l : this.tiles)
				if(l.canBe(i) && l.getValue() == 0){
					count++;
					t = l;
				}
			if(count == 1)
				valid = t.setValue(i);
		}
		if(!valid)
			System.out.println("Check failed on " + this.name);
		return valid;
	}
	
	//
	public boolean checkForGroupAlone2(){
	    return true;
	}
	
	/**
	 * 
	 * @param size
	 * @param test
	 * @return
	 */
	public boolean checkForGroupAlone(int size, ArrayList<Integer> test){
		if(size == 1){ //If inputing one number just check all
			this.checkAll();
			return true;
		}
		if(size + this.getTileValues().size() > this.maxVal) //If we are checking for more tiles than there actually exists
			return true;
		boolean valid = true;
		int currSize = test.size();
		if(currSize == size){
			Set mini = new Set("Mini GA",this.maxVal);
			for(Tile t: this.tiles)
				if(t.possiblities().size() <= size && t.getValue() == 0){
					boolean match = true;
					for(int j = 0; j < t.possiblities().size() && match; j++)
						if(!test.contains(t.possiblities().get(j)))
							match = false;
					if(match)
						mini.addTile(t);
				}
			if(mini.getSize() > size){
				valid = false;
				mini.printAll();
			}
			else if(mini.getSize() == size)
				for(Tile t: this.tiles)
					if(!mini.contains(t))
						for(int k = 0; k < size; k++)
							t.cantBe(test.get(k));
		}
		else
			for(int i = (currSize == 0) ? 1 : (test.get(currSize - 1) + 1); i <= this.maxVal - size + currSize + 1 && valid; i++) //i<=MaxVal when size=curr+1
				if(this.getTileValues().contains(i))
					continue;
				else{ //Keep newTest way b/c otherwise new elements would get to test add each for cycle
					ArrayList<Integer> newTest = (ArrayList<Integer>) test.clone();
					newTest.add(i); 
					valid = this.checkForGroupAlone(size,newTest);
				}
		if(!valid)
			System.out.println("GA " + size + " on " +this.name);
		return valid;
	}
	//-------------------------------------------------------------------------------------------
	public boolean checkForGroupHidden(int size,ArrayList<Integer> test){
		boolean valid = true;
		int currSize = test.size();
		if(currSize == size){
			Set mini = new Set("Mini GH", this.maxVal);
			for(Tile t: this.tiles)
				if(t.getValue() == 0)
					for(int k = 0; k < size; k++)
						if(t.possiblities().contains(test.get(k)) && !mini.contains(t))
							mini.addTile(t);
			if(mini.getSize() == size)
				for(Tile t: tiles)
					if(mini.contains(t) && valid)
						valid = t.canBeOnly(test);
		}
		else
			for(int i = (currSize == 0) ? 1 : (test.get(currSize - 1) + 1); i <= this.maxVal - size + currSize + 1 && valid; i++)
				if(this.getTileValues().contains(i))
					continue;
				else{
					ArrayList<Integer> newTest = (ArrayList<Integer>) test.clone();
					newTest.add(i);
					valid = this.checkForGroupHidden(size,newTest);
				}
		if(!valid)
			System.out.println("GH " + size + " on " +this.name);
		return valid;
	}
	/**
	 * Check if any tiles in the set can only be one value and sets the tile to that value if it can only be one value
	 */
	public void checkAll(){
		for(Tile t: this.tiles)
			t.checkValue();
		//System.out.println("Checked " + this.name);
	}
	public Set clone(){
		Set s = new Set(this.name, this.maxVal);
		for(Tile t:this.tiles)
			s.addTile(t);
		return s;
	}
	public void printAll(){
		System.out.println(name + ": ");
		for(Tile t: this.tiles){
			t.print();
		}
	}
	public void simplePrint(){
		System.out.println(name + ": ");
		int i = 0;
		for(Tile t: this.tiles){
			i++;
			System.out.print(t.getValue() + " ");
			if(i % 9 == 0)
				System.out.println();
		}
	}
}
