package com.jjenginejj.world;

import com.jjenginejj.render.model;
import com.jjenginejj.system.util.vector3d;

public class block {
	//vector3d size;
	//vector3d pos;
	//vector3d rotation;
	public final float[] size = new float[3];
	public final float[] pos = new float[3];
	public final float[] rotation = new float[3];
	model m;
	//com.com.jjenginejj.system.jjenginejj.system.util.vector3d color;
	public int id; // maybe idk maybe change
	//todo add other shit
	//todo add constuctors or something
	/*
	public FloatBuffer genVerts(){
		FloatBuffer data;
		data.put(verta(true, true, true));
		data.put(verta(true, true, false));
		data.put(verta(true, false, true));
		data.put(verta(true, false, false));
		data.put(verta(false, true, true));
		data.put(verta(false, true, false));
		data.put(verta(false, false, true));   //todo remove
		data.put(verta(false, false, false));
	}
	public float[] verta( boolean z, boolean y, boolean x){
		float[] data = new float[9];
		data[0] = pos.x;
		data[1] = pos.y;                           //todo remove
		data[2] = pos.z;
		if(x)data[0] += size.x/2;
		else data[0] -= size.x/2;
		if(y)data[1] += size.y/2;
		else data[1] -= size.y/2;
		if(z)data[2] += size.z/2;
		else data[2] -= size.z/2;
		return data;
	}
	*/

	public int[] toVBOIndices(int offset){
		int[] indices = m.faces;
		for(int i = 0; i<m.numFaces*3; i++){
			indices[i] += offset; // that SHOULD work
		}
		return indices;
	}
	public float[] toVBOVerts(){
		//todo scale
		//todo rotate
		//todo translate
		float[] verts = m.verts;
		return verts;
	}
}
