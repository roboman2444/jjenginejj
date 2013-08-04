import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.ARBBufferObject.glBindBufferARB;
import static org.lwjgl.opengl.ARBBufferObject.glDeleteBuffersARB;
import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;

public class grid {
	//public HashMap<Integer, block> blocks = new HashMap(); // integer for id... wait why not arraylist
	public ArrayList<block> blocks = new ArrayList();
	public int vVBOid;
	//todo add constuctor
	//constructor will generate vbo for itself
	public grid(){
		vVBOid = glGenBuffersARB();

	}
	public void addBlock(block b){
		//add block to cubearray
		b.id = blocks.size(); //TODO i gotta check if it should be blocks.size or blocks.zie + 1;
		blocks.add(b);
		// update whole vbo for cubearray


	}
	public void deleteBlock(int id){
		//delete the block from the cubearray
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
	public void dataToVBO(){
		glBindBufferARB(GL15.GL_ARRAY_BUFFER, vVBOid);
		int numOfverts = 0;
		int numOftris = 0;
		for(int i=0; i < blocks.size(); i++){
			numOfverts += blocks.get(i).m.v.size();
			numOftris += blocks.get(i).m.tris.size();
		}
		FloatBuffer verts =  BufferUtils.createFloatBuffer(numOfverts*9);
		verts.flip();
		FloatBuffer tris =  BufferUtils.createFloatBuffer(numOfverts*3);
		tris.flip();
		for(int i=0; i < blocks.size(); i++){

		}
		//glBufferDataARB( type , SIZEDA, DATA, GL_STATIC_DRAW_ARB)
		//type is likely GL_ARRAY_BUFFER_ARB

	}
	private void delVBO(int id){
		glDeleteBuffersARB(vVBOid);
	}
}
