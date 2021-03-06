package com.jjenginejj.system;

import com.jjenginejj.render.model;
import com.jjenginejj.render.render;
import com.jjenginejj.test.gamecode;
import com.jjenginejj.test.physics;
import com.jjenginejj.world.block;
import com.jjenginejj.world.worldobjects;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class jjenginejj {
	private static int fpsCounter = 0;
	public static float timescale = 0.1f;
	private static float fpsTotalTime = 0;
	static {
		String lwjgl_folder = "libs" + File.separator;
		final String OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

		if (OS.contains("win"))
			lwjgl_folder += "win";
		else if (OS.contains("mac"))
			lwjgl_folder += "osx";
		else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
			lwjgl_folder += "lin";
		else if (OS.contains("sunos"))
			lwjgl_folder += "sol";
		System.setProperty("org.lwjgl.librarypath", new File(lwjgl_folder).getAbsolutePath());
	}
	public static void main(String[] args){
		render.init();
		input.init();
		gamecode.init();
		physics.init();
		block plane = new block();
		try {
			plane.m = model.loadModel("cube.obj");
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		plane.size[0] = 1000f;
		plane.size[1] = 1000f;
		plane.size[2] = 0f;
		plane.rotation[0] = 0;
		plane.rotation[1] = 0;
		plane.rotation[2] = 0;
		plane.pos[0] = 0;
		plane.pos[1] = 0;
		plane.pos[2] = 0;
		worldobjects.addBlock(plane);
		for(int i = 0; i< 1000; i++){
			block b = new block();
			try {
				b.m = model.loadModel("untitled.obj");
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			b.size[0] = 1;
			b.size[1] = 1;
			b.size[2] = 1;
			b.rotation[0] = 0;
			b.rotation[1] = 0;
			b.rotation[2] = 0;
			b.pos[0] = ((float)Math.random()*1f);
			b.pos[1] = ((float)Math.random()*1f);
			b.pos[2] = ((float)Math.random()*1f);
			worldobjects.addBlock(b);
		}

		worldobjects.getData();
		long lasttime = System.currentTimeMillis();
		while(true){
			long time = System.currentTimeMillis();
			timescale = (time-lasttime)/100.0f;
			//camera.rotz+= 1;
			render.draw();
			gamecode.run();
			input.getInput();
			fpsTotalTime += timescale;
			fpsCounter ++;
			if(fpsCounter == 100){
				fpsCounter = 0;
				//System.out.println("fps: "+ 1000/fpsTotalTime);
				Display.setTitle("fps: "+1000/fpsTotalTime);
				fpsTotalTime = 0;
			}
			lasttime = time;
		}

	}
}
