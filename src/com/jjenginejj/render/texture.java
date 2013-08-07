package com.jjenginejj.render;

import org.lwjgl.opengl.GL11;
import java.util.HashMap;

public class texture {
	int width, height, textureID;
	public static HashMap<String, texture> textureList = new HashMap<String, texture>();
	public texture(String filename){

		textureID= GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		//GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0,GL11.GL_RGBA, GL11.GL_INT, (java.nio.ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		System.out.println("Loaded texture " + filename + "with size " + width + "x" + height + " and textureID " + textureID);
	}
	public static texture addTexture(String filename){
		texture t = textureList.get(filename);
		if(t == null){
			textureList.put(filename, new texture(filename));
		}
		return t;


	}
}
