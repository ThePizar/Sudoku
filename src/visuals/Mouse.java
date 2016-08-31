package visuals;

public class Mouse {
	private static int x,y;
	public Mouse(int x, int y) {
		Mouse.x = x;
		Mouse.y = y;
	}
	public static int getX() {
		return x;
	}
	public static void setX(int xInput) {
		x = xInput;
	}
	public static int getY() {
		return y;
	}
	public static void setY(int yInput) {
		y = yInput;
	}
}
