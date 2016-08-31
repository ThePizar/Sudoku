package solving;

import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
	/**
	 * Column of tile
	 */
	private int col;
	/**
	 * Row of Tile
	 */
	private int row;
	/**
	 * Value of tile. 0 if no value. 
	 */
	private int value;
	/**
	 * Highest value for the tile according to the problem. Assumed that all possible values are between 0 and this number. 
	 * 0 is interpreted as no value
	 * Standard Sudoku = 9. 
	 */
	private int maxValue;
	/**
	 * Values that the tile can be, initially 1 - maxValue
	 */
	private ArrayList<Integer> canBe;
	/**
	 * Constructs a new Tile at a certain location and a starting value. Set to be 0 if it should have no value. 
	 * 
	 * @param col is the column of the tile
	 * @param row is the row of the tile
	 * @param value is the value of the tile. No value is 0
	 * @param maxValue is the maximum value of a tile. Assumed that all possible values are between 0 and this number.
	 */
	public Tile(int col, int row, int value, int maxValue){
		this.col = col;
		this.row = row;
        this.maxValue = maxValue;
        if(value <= maxValue && value >=0){
            this.value = value;
        }
        else {
            throw new IllegalArgumentException("Value (" + value + ") is out of bounds specified by maxValue (" + maxValue + ")");
        }
		this.canBe = new ArrayList<Integer>(maxValue);
		if(value == 0){
	        for(int i = 1; i <= maxValue; i++){
	            this.canBe.add(i);
	        }
		}
		else {
		    this.canBe.add(value);
		}
	}
	/**
	 * 
	 * @return value of the tile. O if no value. 
	 */
	public int getValue(){
		return this.value;
	}
	/**
	 * 
	 * @return column of the tile
	 */
	public int getCol(){
		return this.col;
	}
	/**
	 * 
	 * @return row of the tile
	 */
	public int getRow(){
		return this.row;
	}
	/**
	 * 
	 * @return the values that the tile can be
	 */
	public ArrayList<Integer> possiblities(){
		ArrayList<Integer> possiblities = new ArrayList<>();
		for(int i : this.canBe){
            possiblities.add(i);
		}
		return possiblities;
	}
	/**
	 * Tells whether this tile can be the given value
	 * @param a is the value being checked
	 * @return Whether this tile can be a
	 */
	public boolean canBe(int a){
		return this.canBe.contains(a);
	}
	/**
	 * 
	 * @param possibilities list of values that the tile is being assigned to be a part of
	 * @return true if it can be all of those values, otherwise false.
	 */
	public boolean canBeOnly(ArrayList<Integer> possibilities){
		boolean toReturn = true;
		for(int i = 1; i <= this.maxValue && toReturn; i++){
			if(!possibilities.contains(i)){
				toReturn = this.cantBe(i);
			}
		}
		return toReturn;
	}
	/**
	 * Removes the given int from the list of things that this Tile can be
	 * @param value - value that this tile cannot, is assumed to be between 1 and 9 inclusive
	 * 
     * @return whether this tile can be any value
	 */
	public boolean cantBe(Integer value){
	    this.canBe.remove(value);
		return this.canBe.size() > 0;
	}
	/**
	 * @param value - attempts to set the value of the tile to this value
	 * 
	 * @return false if value is not 0 and is not a, otherwise true
	 */
	public boolean setValue(int value){
		//System.out.println("Setting " + this.col + ", " + this.row + ": " + this.getValue() + " To: " + a);
	    if(value == 0 || value > this.maxValue) {
	        return false;
	    }
	    else{
	        this.value = value;
	        return this.canBeOnly(new ArrayList<Integer>(Arrays.asList(value)));
	    }
	}
	/**
	 * Checks whether the tile can only be one non-zero value
	 * 
	 * @return 0 if its value is still not known,
	 * otherwise it returns the (new) value of the tile
	 */
	protected int checkValue(){ 
		if(this.value != 0) {
			return this.value;
		}
		if(this.possiblities().size() == 1) {
			this.setValue(this.possiblities().get(0));
		}
		return this.value;
	}
	/**
	 * prints position as column then row then value, 
	 */
	public void print(){
		System.out.println(this.col + ", " + this.row + ": " + this.value);
	}
}

//This line is a test for git stuff