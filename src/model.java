import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import objimp.*;

public class model {
	//todo
	float[] verts;
	int[]	faces;
	int numVerts = 0;
	int numFaces = 0;
	int vboid = 0;
	int vaoid = 0;
	int vboiid = 0;
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
					//m.verts.add(new vertex(Float.valueOf(line.split(" ")[1]), Float.valueOf(line.split(" ")[2]), Float.valueOf(line.split(" ")[3])));
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




	/*public void scale(vector3d s){
		for(int i=0; i < verts.size(); i++){
			verts.get(i).scale(s);
		}
	}
	public void translate(vector3d t){
		for(int i=0; i < verts.size(); i++){
			verts.get(i).translate(t);
		}
	}
	public void rotate(vector3d r){
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

