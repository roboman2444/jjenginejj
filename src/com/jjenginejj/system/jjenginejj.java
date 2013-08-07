package com.jjenginejj.system;

import com.jjenginejj.render.model.model;
import com.jjenginejj.render.render;
import com.jjenginejj.test.gamecode;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class jjenginejj {
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
		try {
			model.loadModel("untitled.obj");
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		while(true){
			render.draw();
			gamecode.run();
			input.getInput();
		}

	}
}
