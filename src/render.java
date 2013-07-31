import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;

public class render extends jjenginejj {
	public static boolean PostProcessEnabled = false;
	public static boolean CubeProcessEnabled = false;
	public static boolean PostProcessBloom = false;
	public static boolean PostProcessFlare = true;
	public static int PostProcessBloomBlurPasses = 2;
	public static int sizeX = 800;
	public static int sizeY = 600;
	public static float ppwhatx = 0;
	public static float ppwhaty = 0;
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
		//System.out.println("resized to " + sizeX +" " + sizeY);
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
		Framebuffer.initFramebuffers();
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

	}
	private static void drawFSQuad(){     //todo make betterly
		glLoadIdentity();
		translateCrap(0f, -2f, 0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1,-1, 0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(1,-1, 0.5f);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(1,1, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1,1, 0.5f);
		glEnd();
	}
	private static Framebuffer Blur(Framebuffer framebuffer, int passes){ //takes framebuffer to blur, returns output framebuffer
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		switchToOrtho();
		for(int i=0; i < passes; i++){ // todo maybe i can optimize looking up the framebuffers
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("blurTemp").framebufferID);
			GL20.glUseProgram(Shader.shaders.get("gaush"));
			glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
			drawFSQuad();
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("blurOut").framebufferID);
			GL20.glUseProgram(Shader.shaders.get("gausv"));
			glBindTexture(GL_TEXTURE_2D, Framebuffer.framebufferList.get("blurTemp").textureID);
			drawFSQuad();
			framebuffer = Framebuffer.framebufferList.get("blurOut");// for returns AND it re-uses it in the next pass
		}
		return framebuffer;
	}
	private static Framebuffer Bloom(Framebuffer framebuffer, int blurPasses){
		switchToOrtho();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		Framebuffer blurred = Blur(framebuffer, blurPasses);
		Framebuffer output = Framebuffer.framebufferList.get("bloomOut");
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, output.framebufferID);

		//if(PostProcessEnabled)glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("pp5").framebufferID);
		//else glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

		glColor3f(1f, 1f, 1f);
		GL20.glUseProgram(0);
		glBindTexture(GL_TEXTURE_2D, blurred.framebufferID);
		drawFSQuad();
		glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		return output;
	}
	private static Framebuffer Flare(Framebuffer framebuffer){
		switchToOrtho();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		Framebuffer output = Framebuffer.framebufferList.get("flareOut");
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, output.framebufferID);

		glColor3f(1f, 1f, 1f);
		GL20.glUseProgram(Shader.shaders.get("lensflare"));
		//glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		return output;
	}
	private static void PostProcess(){
		Framebuffer framebuffer = Framebuffer.framebufferList.get("renderOut");
		if(PostProcessBloom){
			framebuffer = Bloom(framebuffer, PostProcessBloomBlurPasses); //renders bloom and sets framebuffer to output
		}
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glColor3f(1f, 1f, 1f);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0); //standard
		switchToOrtho();
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		if(PostProcessFlare){
			//framebuffer = Flare(framebuffer); //renders flare and sets framebuffer to output
			framebuffer = Flare(Framebuffer.framebufferList.get("blurOut"));
		}
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glColor3f(1f, 1f, 1f);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0); //standard
		switchToOrtho();
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
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
			PostProcess();
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
