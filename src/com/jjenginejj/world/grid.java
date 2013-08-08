package com.jjenginejj.world;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;

public class grid {
	public final int x;
	public final int y;
	private int vertcount = 0;
	private int tricount = 0;
	//public HashMap<Integer, com.jjjenginejj.render.com.jjenginejj.render.model.model.block> blocks = new HashMap(); // integer for id... wait why not arraylist
	public ArrayList<block> blocks = new ArrayList();
	public int VBOvertsid, VBOindicesid;
	//todo add constuctor
	//constructor will generate vbo for itself
	public grid(int xi, int yi){
		VBOvertsid = glGenBuffersARB();
		VBOindicesid = glGenBuffersARB();
		x = xi;
		y = yi;

	}
	public void addBlock(block b){
		//add com.jjjenginejj.render.com.jjenginejj.render.model.block to cubearray
		b.id = blocks.size(); //TODO i gotta check if it should be blocks.size or blocks.zie + 1;
		blocks.add(b);
		// update whole vbo for cubearray
		//genverts();//YESH
	}
	public void tupdate(){
		genverts();//YESH
		System.out.println(x +"x" + y + " " + vertcount+ " " + VBOvertsid+ " " + tricount + " " + VBOindicesid );
	}
	public void deleteBlock(int id){
		blocks.remove(id);
		//delete the com.jjjenginejj.render.com.jjenginejj.render.model.block from the cubearray
		// if i dont have any cubes, remove vbo.
		//regenerate vbobuffer
		genverts();
	}
	public void draw(){
		//GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		//GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOvertsid);
		//GL11.glVertexPointer(3, GL11.GL_FLOAT, 20, 0); //do i need this?
		//GL11.glTexCoordPointer(2, GL11.GL_FLOAT,20,12);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBOindicesid);
		GL11.glDrawElements(GL11.GL_TRIANGLES, tricount*3, GL11.GL_UNSIGNED_INT,0);
	}
	public void genverts(){

		//generates them verts
		//todo return something useful

		for (block b : blocks) {                        //todo maybe put counting in a less used function... but it wont speed up that much, not like its being called often
			vertcount += b.m.numVerts;
			tricount += b.m.numFaces;
		}
		FloatBuffer vb = BufferUtils.createFloatBuffer(vertcount * 5);
		IntBuffer ib = BufferUtils.createIntBuffer(tricount * 3);
		int vertoffset = 0;
		for (block b : blocks) {
			//todo
			vb.put(b.toVBOVerts());
			ib.put(b.toVBOIndices(vertoffset));
			vertoffset += b.m.numVerts; // should work
		}
		ib.flip(); // back to 0
		vb.flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOvertsid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vb, GL15.GL_STATIC_DRAW);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 20, 0);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT,20,12);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOindicesid);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ib, GL15.GL_STATIC_DRAW);
		//That should be good!
		//todo actually put in vbo
	}

	private void delVBO(int id){
		glDeleteBuffersARB(VBOvertsid);
		glDeleteBuffersARB(VBOindicesid);
	}
}
