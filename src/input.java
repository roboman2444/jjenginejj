import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class input {
	public static boolean  keyESC;

	public static int mouseDeltaX = 0;
	public static int mouseDeltaY = 0;
	public static boolean snapMouse = false;
	public static void getInput(){
		if(snapMouse){
			/*
			mouseDeltaX = Mouse.getX() - render.sizeX/2;
			mouseDeltaY = Mouse.getY() - render.sizeY/2;
			Mouse.setCursorPosition(render.sizeX/2, render.sizeY/2);
			*/
			mouseDeltaX = Mouse.getDX();
			mouseDeltaY = Mouse.getDY();
		}
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				switch(Keyboard.getEventKey()){
					case Keyboard.KEY_ESCAPE:keyESC	= true; break;
				}
			}
			else {
				switch(Keyboard.getEventKey()){
					case Keyboard.KEY_ESCAPE:keyESC	= false; break;
				}
			}
		}
	}
	public static void initInput(){
		//todo set up some sort of keybind things
		if(snapMouse)Mouse.setCursorPosition(render.sizeX/2, render.sizeY/2);
		//Mouse.setGrabbed(false);
		Mouse.setGrabbed(snapMouse);
	}
	public static void toggleMouseSnap(){
		snapMouse = !input.snapMouse;
		initInput();
		mouseDeltaX = 0;
		mouseDeltaY = 0;
	}

}
