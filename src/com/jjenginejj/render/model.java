package com.jjenginejj.render;

import com.jjenginejj.entity.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class model {
    //todo
    public float[] verts;
    public int[]	faces;
    public int numVerts = 0;
    public int numFaces = 0;
    public int vboid = 0;
    public int vaoid = 0;
    public int vboiid = 0;
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

    public void detachEntity(Entity e) {
	if (entities.contains(e))
	    entities.remove(e);
    }

    public void attachEntity(Entity e) {
	if (!entities.contains(e))
	    entities.add(e);
    }




	/*public void scale(com.com.jjenginejj.system.jjenginejj.system.util.vector3d s){
		for(int i=0; i < verts.size(); i++){
			verts.get(i).scale(s);
		}
	}
	public void translate(com.com.jjenginejj.system.jjenginejj.system.util.vector3d t){
		for(int i=0; i < verts.size(); i++){
			verts.get(i).translate(t);
		}
	}
	public void rotate(com.com.jjenginejj.system.jjenginejj.system.util.vector3d r){
		for(int i=0; i < verts.size(); i++){
			verts.get(i).rotate(r);
		}
	}
	public float[] toVertArray(){
		float[] tv = new float[verts.size()*3];
		for(int i=0; i < verts.size();i++){
			//tv[(i*3)] = verts.get(i).pos[0];
			//tv[(i*3)+1] = verts.get(i).pos[1];
			//tv[(i*3)+2] = verts.get(i).pos[2];
			System.arraycopy(tv, i*3,verts.get(i).pos,0,3);
		}
		return tv;
	}
	public int[] toIntArray(int offset){
		int[] ti = new int[tris.size()];
		for(int i=0; i < tris.size();i++){
			ti[i] = tris.get(i)+offset; //should work?
		}
		return ti;
	}
	*/
}

