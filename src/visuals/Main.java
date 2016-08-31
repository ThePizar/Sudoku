package visuals;

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.ArrayList;
//import java.util.Scanner;

import java.util.Scanner;

import solving.Set;
import solving.Tile;

public class Main extends Applet implements Runnable {
	/**
	 * IDK what this is for but applet's unhappy if its not here
	 */
	private static final long serialVersionUID = -852812044749793030L;

	private static Button contButton = new Button(550, 100, 75, 25, Color.white, Color.red, "Continue");
	private static Button loadConsole = new Button(100, 100, 75, 25, Color.white, Color.green, "Load From Console");
	private static Button doCycle = new Button(200, 100, 75, 25, Color.white, Color.blue, "Cycle");
	private static Button printOut = new Button(400, 100, 75, 25, Color.white, Color.blue, "Print");
	private static ArrayList<Button> numpad = new ArrayList<Button>();


	private static Scanner sc = new Scanner(System.in);

	private static Set all = new Set("Master", 9);
	private static ArrayList<Set> sets = new ArrayList<Set>();

	private static ArrayList<TileImage> tileImages = new ArrayList<TileImage>();

	private static boolean legal = true;

	private static int count = 0;
	private static int state = 0;

	public void start() { 
		state = 0;
		//doCycle.toggle();
		for(int i = 0; i < 3;i++)
			for(int j= 0; j < 3;j++)
				numpad.add(new Button(550+j*20, 140+i*20, 20, 20, Color.cyan, Color.black, ""+(i*3+j+1)));
		numpad.add(new Button(570,200,20,20,Color.cyan,Color.black,"0"));
		/*for(int i = 1; i <= 9; i++){
			//Set temp = new Set("Row " + i);
			for(int j = 1; j <= 9; j++){
				Tile t = new Tile(j, i, 0);
				all.addTile(t);
				tileImages.add(new TileImage(200+(j-1)*20, 200+(i-1)*20, 20, t));
				//temp.addTile(t);
			}
			//sets.add(temp.clone());
		}

		for(int i = 1; i <= 9; i++){
			Set temp = new Set("Column " + i);
			for(int j = 1; j <= 9; j++){
				Tile t = all.getTileAt(i, j);
				temp.addTile(t);
			}
			sets.add(temp.clone());
		}
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Set temp = new Set("Box " + j + " " + i);
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
			legal = s.setUpTiles();
			if(!legal){
				System.out.println("Illegal Setup");
				break;
			}
			//System.out.println("Set " + s.getName());
		}
		state = 1;
		 */
		for(int i = 1; i <= 9; i++){
			for(int j = 1; j <= 9; j++){
				Tile t = new Tile(j, i, 0, 9);
				all.addTile(t);
				tileImages.add(new TileImage(200+(j-1)*20, 200+(i-1)*20, 20,Color.cyan, Color.black, t));
			}
		}
		state = 1;
		enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | 
				AWTEvent.MOUSE_MOTION_EVENT_MASK);
		new Thread(this).start();
	}

	public void run(){
		setSize(800, 600);

		// Set up the graphics stuff, double-buffering.
		BufferedImage screen = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics g = screen.getGraphics();
		Graphics appletGraphics = getGraphics();

		// Some variables to use for the fps.
		int tick = 0, fps = 0, acc = 0;
		long lastTime = System.nanoTime();

		// Game loop.
		while (true) {

			//fps calculator
			long now = System.nanoTime();
			acc += now - lastTime;
			tick++;
			if (acc >= 1000000000L) {
				acc -= 1000000000L;
				fps = tick;
				tick = 0;
			}

			lastTime = now;

			Font ArialBold=new Font("Arial",Font.BOLD,16);

			// Render

			//Background
			g.setColor(Color.white);
			g.fillRect(0, 0, 800, 600);

			//fps background
			/*g.setColor(Color.blue);
			g.fillRect(20, 20, 55, 11);
			 */

			//fps display
			g.setColor(Color.cyan);
			g.setFont(ArialBold);
			g.drawString("FPS " + String.valueOf(fps), 10, 20);

			if(state > 0 && state != 3){
				for(TileImage t: tileImages)
					t.display(g);
				if(state < 3)
				for(TileImage t: tileImages)
					t.toolTip(g);
			}
			if(state == 1){
				contButton.display(g);
				loadConsole.display(g);
				for(Button b:numpad)
					b.display(g);
			}
			else if(state == 2){
				doCycle.display(g);
				printOut.display(g);

				g.setColor(Color.red);
				g.setFont(ArialBold);
				g.drawString("Cycles: " + count, 400, 100);
			}
			else if(state == 3){
				g.setColor(Color.black);
				g.setFont(new Font("Arial",Font.BOLD,48));
				g.drawString("Failed Puzzle", 250, 350);
			}

			// Draw the entire results on the screen.
			appletGraphics.drawImage(screen, 0, 0, null);

			do {
				Thread.yield();
			} while (System.nanoTime() - lastTime < 0);

			if (!isActive()) {
				return;
			}
		}
	}

	public void processEvent(AWTEvent e) {
		//System.out.println(e.getID()); 
		//Getting event ids for events, see usefulIds.txt

		//Setting Mouse location for every MouseEvent
		if(e.getClass().getSimpleName().equals("MouseEvent")){
			Mouse.setX(((MouseEvent)e).getX());
			Mouse.setY(((MouseEvent)e).getY());
		}
		if(state > 0 && state < 3)
			//For tiletip
			for(TileImage t: tileImages)
				t.isHovered();
		if(state == 1){
			if(contButton.clicked(e)){
				setSetup();
				state = 2;
				all.checkAll();
			}
			if(loadConsole.clicked(e))
				loadFromConsole();
			for(TileImage t: tileImages)
				t.clicked(e);
			for(Button b: numpad)
				if(b.clicked(e))
					for(TileImage t: tileImages)
						if(t.isSelected()){
							t.getTile().setValue(Integer.parseInt(b.getText()));
							t.resetSelected();
						}
		}
		else if(state == 2){
			if(doCycle.clicked(e) && legal){ //When cycle clicked, run a cycle of set checks
				count++;
				for(Set s: sets){
					legal = s.updateAll();
					if(legal)
						legal = s.checkForOnly();
					for(int i = 2; i <= 5 && legal; i++){
						ArrayList<Integer> list = new ArrayList<Integer>();
						legal = s.checkForGroupAlone(i,list);
						if(legal){
							list.clear();
							legal = s.checkForGroupHidden(i,list);
						}
					}
					if(!legal)
						break;
				}
				all.checkAll();
				if(!legal){
					System.out.println("Illegal Setup");
					state = 3;
				}
				if(!cont())
					state = 4;
			}

			if(printOut.clicked(e)){ //Print out current board
				all.simplePrint();
			}
		}
		/*firstSlider.clicked(e);
		//if(firstSlider.isHeld())
		//	firstSlider.moveSlider();
		 */
	}
	private static void setSetup(){
		//Columns
		for(int i = 1; i <= 9; i++){
			Set temp = new Set("Column " + i,9);
			for(int j = 1; j <= 9; j++){
				Tile t = all.getTileAt(i, j);
				temp.addTile(t);
			}
			sets.add(temp.clone());
		}
		//Rows
		for(int i = 1; i <= 9; i++){
			Set temp = new Set("Row " + i,9);
			for(int j = 1; j <= 9; j++){
				Tile t = all.getTileAt(j, i);
				temp.addTile(t);
			}
			sets.add(temp.clone());
		}
		//Boxes
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				Set temp = new Set("Box " + j + " " + i,9);
				for(int k = 1; k <= 3; k++){
					for(int l = 1; l <= 3; l++){
						Tile t = all.getTileAt(i*3+k, j*3+l);
						temp.addTile(t);
					}
				}
				sets.add(temp.clone());
			}
		}
	}
	private static void loadFromConsole(){
		for(int i = 1; i <= 9; i++)
			for(int j = 1; j <= 9; j++){
				all.getTileAt(j, i).setValue(sc.nextInt());
				System.out.println(i + " " + j);
			}
	}
	private static boolean cont(){
		for(int i = 1; i <= 9; i++)
			for(int j = 1; j <= 9; j++)
				if(all.getTileAt(i, j).getValue()==0)
					return true;
		return false;
	}
	public static void printState(){
		System.out.println(state);
	}
}