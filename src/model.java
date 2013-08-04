import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class model {
	//todo
	ArrayList<vertex> verts = new ArrayList<vertex>();
	ArrayList<Integer> tris = new ArrayList<Integer>();
	public static HashMap<String, model> modellist = new HashMap<String, model>();
	public model(){

	}
	public static model loadModel(String filename)throws FileNotFoundException, IOException {
		model m = modellist.get(filename);
		if(m == null){
			m = new model();
			File f = new File(filename);
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			ArrayList<Float> tempv = new ArrayList<Float>();
			while((line=reader.readLine()) != null){
				if(line.startsWith("v ")){
					m.verts.add(new vertex(Float.valueOf(line.split(" ")[1]), Float.valueOf(line.split(" ")[2]), Float.valueOf(line.split(" ")[3])));
				}
			}


		}
		return m;
	}
	public void scale(vector3d s){
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
}

