import java.util.ArrayList;
import java.util.HashMap;

public class model {
	//todo
	ArrayList<vertex> v = new ArrayList<vertex>();
	ArrayList<Integer> tris = new ArrayList<Integer>();
	public static HashMap<String, model> modellist = new HashMap<String, model>();
	public model(){

	}
	public static model loadModel(String filename){
		model m = modellist.get(filename);
		if(m == null){
			m = new model();

		}
		return m;
	}
	public void scale(vector3d s){
		for(int i=0; i < v.size(); i++){
			v.get(i).scale(s);
		}
	}
	public void translate(vector3d t){
		for(int i=0; i < v.size(); i++){
			v.get(i).translate(t);
		}
	}
	public void rotate(vector3d r){
		for(int i=0; i < v.size(); i++){
			v.get(i).rotate(r);
		}
	}
}

