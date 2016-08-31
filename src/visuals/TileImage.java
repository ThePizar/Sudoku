package visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import solving.Tile;

public class TileImage extends ToggleButton{
	/**
	 * The tile this image represents
	 */
	protected Tile t;
	
	/**
	 * Constructs a Tile's image at a given position as a button
	 * 
	 * @param x - x position of the image
	 * @param y - y position of the image
	 * @param size - size of the square of the image
	 * @param buttonColor - color of the button created
	 * @param textColor - color of the text in the button
	 * @param t - tile represented by the image
	 */
	public TileImage(int x, int y, int size, Color buttonColor,
			Color textColor, Tile t){
		super(x, y, size, size, buttonColor, textColor, "");
		this.t = t;
		this.text = t.getValue()==0?"":""+t.getValue();
	}
	/**
	 * 
	 * @return the tile represented by this image
	 */
	public Tile getTile(){
		return t;
	}
	/**
	 * Draws the image as the value of the tile (blank if 0) inside of a outlined square
	 * if the tile is enabled as a button then it is a solid square
	 * @param g - the graphics onto which the image is drawn
	 */
	public void display(Graphics g){
		this.text = t.getValue()==0?"":""+t.getValue();
		super.display(g);
	}
	/**
	 * Draws text next to the cursor that shows the potential value(s) of the tile
	 * @param g - the graphics onto which the image is drawn
	 */
	public void toolTip(Graphics g){
		if(this.isHovered() && t.getValue() == 0){
			ArrayList<Integer> possibles = t.possiblities();
			String list = "";
			for(int i = 0; i < possibles.size(); i++){
				list += possibles.get(i);
				if(i != possibles.size()-1)
					list += ", ";
			}
			g.setColor(Color.gray);
			g.fillRect(Mouse.getX(), Mouse.getY()-this.width, this.width*possibles.size(), this.width);
			g.setColor(Color.red);
			g.setFont(new Font("Arial",Font.PLAIN,this.width-2));
			g.drawString(list, Mouse.getX()+1, Mouse.getY()-3);
		}
	}
}
