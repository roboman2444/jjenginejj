package com.jjenginejj.render.model;

import java.util.ArrayList;

import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;

public class grid {
	//public HashMap<Integer, com.jjjenginejj.render.com.jjenginejj.render.model.model.block> blocks = new HashMap(); // integer for id... wait why not arraylist
	public ArrayList<block> blocks = new ArrayList();
	public int vVBOid;
	//todo add constuctor
	//constructor will generate vbo for itself
	public grid(){
		vVBOid = glGenBuffersARB();

	}
	public void addBlock(block b){
		//add com.jjjenginejj.render.com.jjenginejj.render.model.model.block to cubearray
		b.id = blocks.size(); //TODO i gotta check if it should be blocks.size or blocks.zie + 1;
		blocks.add(b);
		// update whole vbo for cubearray


	}
	public void deleteBlock(int id){
		//delete the com.jjjenginejj.render.com.jjenginejj.render.model.model.block from the cubearray
		// if i dont have any cubes, remove vbo.
		//regenerate vbobuffer
	}
	private void genverts(){

		//generates them verts
		//todo return something useful
		for( int i =0; i < blocks.size(); i++){
			//todo

		}
	}
	/*public void dataToVBO(){
		glBindBufferARB(GL15.GL_ARRAY_BUFFER, vVBOid);
		int numOfVerts = 0;
		int numOfTris = 0;
		for(int i=0; i < blocks.size(); i++){
			numOfVerts += blocks.get(i).m.verts.size();
			numOfTris += blocks.get(i).m.tris.size();
		}
		FloatBuffer verts =  BufferUtils.createFloatBuffer(numOfVerts*9);

		ByteBuffer tris =  BufferUtils.createByteBuffer(numOfVerts*3*4);

		int indexOfVerts = 0;
		int indexOfTris = 0;
		for(int i=0; i < blocks.size(); i++){
			verts.put(blocks.get(i).genVerts());
			tris.put(blocks.get(i).genTris(indexOfVerts*9));
			numOfVerts += blocks.get(i).m.verts.size();
			numOfTris += blocks.get(i).m.tris.size();

		}
		tris.flip();
		verts.flip();
		//glBufferDataARB( type , SIZEDA, DATA, GL_STATIC_DRAW_ARB)
		//type is likely GL_ARRAY_BUFFER_ARB

	}
	private void delVBO(int id){
		glDeleteBuffersARB(vVBOid);
	}
	*/
}
