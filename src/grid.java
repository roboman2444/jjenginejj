import java.util.ArrayList;
import java.util.HashMap;

public class grid {
	//public HashMap<Integer, block> blocks = new HashMap(); // integer for id... wait why not arraylist
	public ArrayList<block> blocks = new ArrayList();
	public int FBOid;
	//todo add constuctor
	//constructor will generate vbo for itself
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
	}
	public void dataToVBO(){
		//glBindBufferARB(GL_ARRAY_BUFFER_ARB, FBOid)
		//glBufferDataARB( type , SIZEDA, DATA, GL_STATIC_DRAW_ARB)
		//type is likely GL_ARRAY_BUFFER_ARB

	}
	private void addVBO(){

	}
	private void delVBO(int id){

	}
}
