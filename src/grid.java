import java.util.ArrayList;

public class grid {
	ArrayList listofblocks = new ArrayList(); //todo have one per texture?
	//ummm
	///yeah
	//todo add constuctor
	//constructor will generate vbo for itself
	public static void addBlock(){
		//add block to cubearray
		// update whole vbo for cubearray
		//glBindBufferARB(GL_ARRAY_BUFFER_ARB, id)
		//glBufferDataARB( type , SIZEDA, DATA, GL_STATIC_DRAW_ARB)
		//type is likely GL_ARRAY_BUFFER_ARB

	}
	public static void deleteBlock(){
		//delete the block from the cubearray
		// if i dont have any cubes, remove vbo.
		//regenerate vbobuffer

	}
	public static void genverts(){
		//generates them verts
	}
}
