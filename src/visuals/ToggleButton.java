package visuals;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;

public class ToggleButton extends Button {
	/**
	 * whether the button is selected
	 */
	private boolean selected;
	/**
	 * Constructs a toggleable button with the given parameters for drawing
	 * @param x - x position of the button's bottom left corner
	 * @param y - y position of the button's bottom left corner
	 * @param width - width of the button
	 * @param height - height of the button
	 * @param buttonColor - color of the button
	 * @param textColor - color of the button's text
	 * @param string - text of the button
	 */
	public ToggleButton(int x, int y, int width, int height, Color buttonColor,
			Color textColor, String string) {
		super(x, y, width, height, buttonColor, textColor, string);
		this.selected = false;
	}
	/**
	 * If the button is clicked it toggles the state of selection
	 */
	public boolean clicked(AWTEvent e){
		boolean isClicked = super.clicked(e);
		if(isClicked)
			this.selected = !this.selected;
		return isClicked;
	}
	/**
	 * Draws the button as a rectangle of given parameters on the given graphics.
	 * If the button is selected then it is drawn as a filled square instead of an outline. 
	 */
	public void display(Graphics g){
		g.setColor(this.buttonColor);
		if(selected)
			g.fillRect(this.x, this.y, this.width, this.height);
		else
			g.drawRect(this.x, this.y, this.width, this.height);

		g.setColor(this.textColor);
		g.setFont(this.font);
		g.drawString(this.text, this.x+1, this.y+(this.height/2+6));
	}
	/**
	 * 
	 * @return whether the button is selected
	 */
	public boolean isSelected(){
		return this.selected;
	}
	/**
	 * resets the selection to false
	 */
	public void resetSelected(){
		selected = false;
	}
}
