package com.jjenginejj.render.model;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;

public class grid {
	//public HashMap<Integer, com.jjjenginejj.render.com.jjenginejj.render.model.model.block> blocks = new HashMap(); // integer for id... wait why not arraylist
	public ArrayList<block> blocks = new ArrayList();
	public int VBOid;
	//todo add constuctor
	//constructor will generate vbo for itself
	public grid(){
		VBOid = glGenBuffersARB();

	}
	public void addBlock(block b){
		//add com.jjjenginejj.render.com.jjenginejj.render.model.model.block to cubearray
		b.id = blocks.size(); //TODO i gotta check if it should be blocks.size or blocks.zie + 1;
		blocks.add(b);
		// update whole vbo for cubearray
		genverts();//YESH

	}
	public void deleteBlock(int id){
		//delete the com.jjjenginejj.render.com.jjenginejj.render.model.model.block from the cubearray
		// if i dont have any cubes, remove vbo.
		//regenerate vbobuffer
	}
	private void genverts(){

		//generates them verts
		//todo return something useful
		int vertcount = 0;
		int tricount = 0;
		for(int i =0; i< blocks.size(); i++){ 			//todo maybe put counting in a less used function... but it wont speed up that much, not like its being called often
			block b = blocks.get(i); // small speed up?
			vertcount += b.m.numVerts;
			tricount  += b.m.numFaces;
		}
		FloatBuffer vb = BufferUtils.createFloatBuffer(vertcount * 5);
		IntBuffer ib = BufferUtils.createIntBuffer(tricount * 3);
		int vertoffset = 0;
		for(int i =0; i < blocks.size(); i++){
			//todo
			block b = blocks.get(i); // small speed up?
			vb.put(b.toVBOVerts());
			ib.put(b.toVBOIndices(vertoffset));
			vertoffset += b.m.numVerts; // should work
		}
		ib.flip(); // back to 0
		vb.flip();
		//todo actually put in vbo
	}

	private void delVBO(int id){
		glDeleteBuffersARB(VBOid);
	}
}
