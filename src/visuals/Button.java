package visuals;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {
	/**
	 * x position of the button's bottom left corner
	 */
	protected int x;
	/**
	 * y position of the button's bottom right corner
	 */
	protected int y;
	/**
	 * width of the button
	 */
	protected int width;
	/**
	 * height of the button
	 */
	protected int height;
	/**
	 * color of the button
	 */
	protected Color buttonColor;
	/**
	 * color of the text in the button
	 */
	protected Color textColor;
	/**
	 * text of the button
	 */
	protected String text;
	/**
	 * font used by the button
	 */
	protected Font font=new Font("Arial",Font.PLAIN,12);

	/**
	 * Consructs a button with the given parameters for drawing
	 * @param x - x position of the button's bottom left corner
	 * @param y - y position of the button's bottom left corner
	 * @param width - width of the button
	 * @param height - height of the button
	 * @param buttonColor - color of the button
	 * @param textColor - color of the button's text
	 * @param string - text of the button
	 */
	public Button(int x, int y, int width, int height, Color buttonColor,
			Color textColor, String string) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttonColor = buttonColor;
		this.textColor = textColor;
		this.text = string;
	}
	/**
	 * 
	 * @return the text of the button
	 */
	public String getText(){
		return text;
	}
	/**
	 * Draws the button as a rectangle of given parameters on the given graphics
	 * @param g - graphics on which to draw
	 */
	public void display(Graphics g){
		if(!isHovered()){
			g.setColor(buttonColor);
			g.drawRect(x, y, width, height);
		}
		else{
			g.setColor(buttonColor);
			g.fillRect(x, y, width, height);
			g.setColor(textColor);
			g.drawRect(x, y, width, height);
		}
		g.setColor(textColor);
		g.setFont(font);
		g.drawString(text, x+1, y+(height/2+6));
	}
	/**
	 * 
	 * @param e - the event being checked
	 * @return if the button is being clicked on
	 */
	public boolean clicked(AWTEvent e){
		if(e.getID()==501 && this.isHovered()){
			System.out.println(text + " clicked");
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return if the mouse is over the button
	 */
	protected boolean isHovered(){
		if(Mouse.getX() >= x && Mouse.getX() <= x + this.width
				&& Mouse.getY() >= y && Mouse.getY() <= y + this.height)
			return true;
		return false;
	}
}
