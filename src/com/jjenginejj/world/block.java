package com.jjenginejj.world;

import com.jjenginejj.render.model;
import com.jjenginejj.system.util.Matrix4x4;

public class block {
	public final float[] size = new float[3];
	public final float[] pos = new float[3];
	public final float[] rotation = new float[3];
	public transient model m;
	public int id; // maybe idk maybe change
	//todo add other shit
	//todo add constuctors or something

	public int[] toVBOIndices(int offset){
		int[] indices = m.faces;
		for(int i = 0; i<m.numFaces*3; i++){
			indices[i] += offset; // that SHOULD work
		}
		return indices;
	}
	public float[] toVBOVerts(){
		Matrix4x4 tempmatrix = Matrix4x4.createFromEntity(pos, rotation, size);
		float[] verts = m.verts;
		float ti[] = new float[3];
		for(int i=0; i < m.numVerts; i++){
			ti[0] = verts[(i*5)];
			ti[1] = verts[(i*5)+1];
			ti[2] = verts[(i*5)+2];
			float[] to = Matrix4x4.transform(tempmatrix, ti);
			verts[(i*5)] = to[0];
			verts[(i*5)+1] = to[1];
			verts[(i*5)+2] = to[2];
		}
		return verts;
	}
}
