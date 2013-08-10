package com.jjenginejj.render;

import com.jjenginejj.entity.Entity;
import com.jjenginejj.world.block;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.lwjgl.opengl.ARBBufferObject.glGenBuffersARB;

public class model {
    //todo
    public float[] verts;
    public int[]   faces;
    public int numVerts = 0;
    public int numFaces = 0;
    public int VBOindicesid = 0;
    public int VBOvertsid = 0;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    public static HashMap<String, model> modellist = new HashMap<String, model>();
    public model(){

    }
    public static model loadModel(String filename)throws FileNotFoundException, IOException {
	model m = modellist.get(filename);
	if(m == null){
	    m = new model();
	    modellist.put(filename, m);
	    File f = new File(filename);
	    BufferedReader reader = new BufferedReader(new FileReader(f));
	    String line;
	    while((line=reader.readLine()) != null){
		if(line.startsWith("v ")){
		    m.numVerts++;
		}else if(line.startsWith("f ")){
		    m.numFaces++;
		}
	    }
	    m.verts = new float[5*m.numVerts];
	    m.faces = new int[3*m.numFaces];
	    int vIndex = 0;
	    int vtIndex = 0;
	    int fIndex = 0;
	    reader = new BufferedReader(new FileReader(f)); // stupid quick and easy way to reset to beginning o file
	    while((line=reader.readLine()) != null){
		if(line.startsWith("v ")){
		    //m.verts.add(new com.com.jjenginejj.system.jjenginejj.system.util.vertex(Float.valueOf(line.split(" ")[1]), Float.valueOf(line.split(" ")[2]), Float.valueOf(line.split(" ")[3])));
		    m.verts[(5*vIndex)] = Float.valueOf(line.split(" ")[1]);
		    m.verts[(5*vIndex)+1] = Float.valueOf(line.split(" ")[2]);
		    m.verts[(5*vIndex)+2] = Float.valueOf(line.split(" ")[3]);
		    vIndex++;
		} else if(line.startsWith("vt ")){
		    m.verts[(5*vtIndex)+3] = Float.valueOf(line.split(" ")[1]);
		    m.verts[(5*vtIndex)+4] = Float.valueOf(line.split(" ")[2]);
		    vtIndex++;
		}
		else if(line.startsWith("f ")){
		    m.faces[(3*fIndex)] = Integer.valueOf(line.split(" ")[1].split("/")[0]);
		    m.faces[(3*fIndex)+1] = Integer.valueOf(line.split(" ")[2].split("/")[0]);
		    m.faces[(3*fIndex)+2] = Integer.valueOf(line.split(" ")[3].split("/")[0]);
		    fIndex++;
		}
	    }
	    System.out.println("loaded model " + filename +" with "+ m.numVerts + " verts");
	    System.out.println("verts in xyzwt form " + Arrays.toString(m.verts));
	    System.out.println("faces " + Arrays.toString(m.faces));
	}

	return m;
    }
    public void genVBO(){
	    //generates them verts
	    //todo return something useful
	    VBOvertsid = glGenBuffersARB();
	    VBOindicesid = glGenBuffersARB();

	    FloatBuffer vb = BufferUtils.createFloatBuffer(numVerts * 5);
	    IntBuffer ib = BufferUtils.createIntBuffer(numFaces * 3);
	    int vertoffset = 0;
	    vb.put(verts);
	    ib.put(faces);
	    ib.flip(); // back to 0
	    vb.flip();
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOvertsid);
	    //GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vb, GL15.GL_STATIC_DRAW);
	    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vb, GL15.GL_STATIC_READ);
	    GL11.glVertexPointer(3, GL11.GL_FLOAT, 20, 0);
	    GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 20, 12);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOindicesid);
	    //GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ib, GL15.GL_STATIC_DRAW);
	    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, ib, GL15.GL_STATIC_READ);
	    //That should be good!
	    //todo actually put in vbo
    }
    public void detachEntity(Entity e) {
	if (entities.contains(e))
	    entities.remove(e);
    }

    public void attachEntity(Entity e) {
	if (!entities.contains(e))
	    entities.add(e);
    }

}

