package com.jjenginejj.render;

import com.jjenginejj.system.util.Matrix4x4;
import com.jjenginejj.world.worldobjects;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class render {
	public static boolean PostProcessEnabled = false;
	public static boolean PostProcessBloom = false;
	public static boolean PostProcessFlare = true;
	public static int PostProcessBloomBlurPasses = 2;
	public static int sizeX = 800;
	public static int sizeY = 600;

	private static int temprot = 0; //todo remove
	public static void toggleFS() {
		try {
			Display.setFullscreen(!Display.isFullscreen());
		} catch (LWJGLException e) {
			// TODO Auto-generated catch com.jjjenginejj.render.com.jjenginejj.render.model.block
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
		Display.setTitle("jjenginejj");

		Shader.initShader();
	//	com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.initFramebuffers();
		// init OpenGL

		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST); // depth testing, yo
		glDepthFunc(GL_LEQUAL);
		glBlendFunc(GL_SRC_ALPHA,GL_ONE);
		glDisable(GL_BLEND);
		glShadeModel (GL_SMOOTH);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);


		resizeDisplay();
	}
	private static void resizeWindow(int x, int y){
		if(x==0)x=1;
		if(y==0)y=1;
		//todo
		glViewport(0, 0, x, y);
		glMatrixMode(GL_PROJECTION);// Select The Projection Matrix
		glLoadIdentity();// Reset The Projection Matrix
		gluPerspective(camera.fov,(float)x/(float)y, 0.1f,1000.0f);
		glMatrixMode(GL_MODELVIEW);                     // Select The Modelview Matrix
		glLoadIdentity();                           // Reset The Modelview Matrix
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		//com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.framebufferList.get("renderOut").resizeFramebuffer(x, y);
		//com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.framebufferList.get("blurTemp").resizeFramebuffer(x, y);
		//com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.framebufferList.get("blurOut").resizeFramebuffer(x, y);
		//com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.framebufferList.get("bloomOut").resizeFramebuffer(x, y);
		//com.jjjenginejj.com.jjenginejj.render.render.Framebuffer.framebufferList.get("flareOut").resizeFramebuffer(x, y);
		System.out.println("Window resized to " + sizeX + "x" + sizeY);
	}
	public static void drawEntities(){
	}
	public static void draw() {
		//glEnable(GL_TEXTURE_2D);
		//glEnable(GL_BLEND);
		//glEnable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0);

		switchToPerspective();
		if(PostProcessEnabled)glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("renderOut").framebufferID);
		else glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//glMatrixMode(GL_MODELVIEW);//just fo shitsngiggles
		glLoadIdentity();// again, for shitsngiggles
		adjustToCamera();
		glColor3f(1.0f, 1.0f, 0.0f);
		worldobjects.draw();

		if(PostProcessEnabled){
			postprocess.PostProcess();
		}

		if(Display.wasResized())resizeDisplay();
		Display.update();


	}


	public static void switchToPerspective(){
		glMatrixMode(GL_PROJECTION);// Select The Projection Matrix
		glLoadIdentity();// Reset The Projection Matrix
		gluPerspective(camera.fov,(float)sizeX/(float)sizeY, 0.1f,1000.0f);
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
	public static void rotateCrap(float[] rot){
		glRotatef(-camera.rot[0], 1, 0, 0);
		glRotatef(-camera.rot[1], 0, 1, 0);
		glRotatef(-camera.rot[2], 0, 0, 1);
	}
	public static void translateCrap(float[] pos){
		glTranslatef(pos[0], pos[1], pos[2]);
	}
	public static void adjustToCamera(){
		//all shits is negative because move "world", not "com.jjjenginejj.com.jjenginejj.render.render.camera"
		//rotateCrap(-camera.roty, -camera.rotz, -camera.rotx);
		//glRotatef(-camera.rot[2], 0, 0, 1);
		//glRotatef(-camera.rot[1], 0, 1, 0);
		//glRotatef(-camera.rot[0], 1, 0, 0);
		float[] tempscale = new float[3];
		tempscale[0] = 1;
		tempscale[1] = 1;
		tempscale[2] = 1;

		Matrix4x4 cam = Matrix4x4.createFromEntity(camera.pos, camera.rot,tempscale);
		cam.invert();
		DoubleBuffer db = DoubleBuffer.allocate(16);
		db.put(cam.m_);
		db.flip();
		glMultMatrix(db);

		//glRotatef(-camera.rot[1],  0, 1, 0);
		//glRotatef(-camera.rot[0], 1, 0, 0);
		//glRotatef(-camera.rot[2], 0, 0, 1);
		glTranslatef(-camera.pos[0], -camera.pos[1], -camera.pos[2]);
	}
}
