import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

public class render extends jjenginejj {
	public static boolean PostProcessEnabled = false;
	public static boolean PostProcessBloom = false;
	public static boolean PostProcessFlare = true;
	public static int PostProcessBloomBlurPasses = 2;
	public static int sizeX = 800;
	public static int sizeY = 600;
	public static void toggleFS() {
		try {
			Display.setFullscreen(!Display.isFullscreen());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resizeDisplay();

	}
	public static void resizeDisplay(){
		sizeX = Display.getWidth();
		sizeY = Display.getHeight();
		resizeWindow(sizeX, sizeY);
	}
	public static void init () {

		//how do i get a framebuffer by name?
		//ok

		try {
			Display.setDisplayMode(new DisplayMode(sizeX,sizeY));
			Display.create();
			Display.setResizable(true);
			//resizeDisplay();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		Shader.initShader();
	//	Framebuffer.initFramebuffers();
		// init OpenGL

		glDepthFunc(GL_LEQUAL);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		glBlendFunc(GL_SRC_ALPHA,GL_ONE);
		glEnable(GL_BLEND);
		glShadeModel (GL_SMOOTH);


		resizeDisplay();
	}
	private static void resizeWindow(int x, int y){
		if(x==0)x=1;
		if(y==0)y=1;
		//todo
		glViewport(0, 0, x, y);
		glMatrixMode(GL_PROJECTION);// Select The Projection Matrix
		glLoadIdentity();// Reset The Projection Matrix
		gluPerspective(camera.fov,(float)x/(float)y, 0.1f,100.0f);
		glMatrixMode(GL_MODELVIEW);                     // Select The Modelview Matrix
		glLoadIdentity();                           // Reset The Modelview Matrix
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		//Framebuffer.framebufferList.get("renderOut").resizeFramebuffer(x, y);
		//Framebuffer.framebufferList.get("blurTemp").resizeFramebuffer(x, y);
		//Framebuffer.framebufferList.get("blurOut").resizeFramebuffer(x, y);
		//Framebuffer.framebufferList.get("bloomOut").resizeFramebuffer(x, y);
		//Framebuffer.framebufferList.get("flareOut").resizeFramebuffer(x, y);
		System.out.println("Window resized to " + sizeX + "x" + sizeY);
	}

	public static void draw() {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0);

		switchToPerspective();
		if(PostProcessEnabled)glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("renderOut").framebufferID);
		else glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//glMatrixMode(GL_MODELVIEW);//just fo shitsngiggles
		glLoadIdentity();// again, for shitsngiggles
		camera.AdjustToCamera();

		if(PostProcessEnabled){
			postprocess.PostProcess();
		}
		

		if(Display.wasResized())resizeDisplay();
		Display.update();


	}

	public static void translateCrap(float x, float y, float z){
		glTranslatef(x, z, y);
	}
	public static void switchToPerspective(){
		glMatrixMode(GL_PROJECTION);// Select The Projection Matrix
		glLoadIdentity();// Reset The Projection Matrix
		gluPerspective(camera.fov,(float)sizeX/(float)sizeY, 0.1f,100.0f);
		glMatrixMode(GL_MODELVIEW);                     // Select The Modelview Matrix
		glLoadIdentity();                           // Reset The Modelview Matrix
	}
	public static void switchToOrtho(){
		glMatrixMode(GL_PROJECTION);// Select The Projection Matrix
		glLoadIdentity();// Reset The Projection Matrix
		glOrtho(-1, 1, -1, 1, 0.1, 100); //maybe change
		glMatrixMode(GL_MODELVIEW);                     // Select The Modelview Matrix
		glLoadIdentity();                           // Reset The Modelview Matrix
	}
	public static void rotateCrap(float roll, float yaw, float pitch){
		glRotatef(roll, 0, 0, 1);
		glRotatef(yaw,  0, 1, 0);
		glRotatef(pitch, 1, 0, 0);
	}
}
