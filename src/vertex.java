import java.util.Arrays;

public class vertex {
	float[] pos = new float[3];
	float[] color = new float[4];
	float[] texcoord = new float[2];
	public vertex(vector3d vpos){
		pos = vector3d.vecToArray(vpos);//todo
		Arrays.fill(color, 1);
		Arrays.fill(texcoord, 1);
	}
	public vertex(float x, float y, float z){
		pos[0] = x;
		pos[1] = y;
		pos[2] = z;
		Arrays.fill(color, 1);
		Arrays.fill(texcoord, 1);
	}
	//scale
	public void scale(vector3d scale){
		pos[0] *= scale.x;
		pos[1] *= scale.y;
		pos[2] *= scale.z;
	}
	public void scale(float[] scale){
		pos[0] *= scale[0];
		pos[1] *= scale[1];
		pos[2] *= scale[2];
	}
	public void scale( float x, float y, float z){
		pos[0] *= x;
		pos[1] *= y;
		pos[2] *= z;
	}
	public void scale( float scale){
		pos[0] *= scale;
		pos[1] *= scale;
		pos[2] *= scale;
	}
	//transform
	public void translate( vector3d t){
		pos[0] += t.x;
		pos[1] += t.y;
		pos[2] += t.z;
	}
	public void translate( float[] t){
		pos[0] += t[0];
		pos[1] += t[1];
		pos[2] += t[2];
	}
	public void translate(float x, float y, float z){
		pos[0] += x;
		pos[1] += y;
		pos[2] += z;
	}
	//rotate
	public void rotate(vector3d r){
		//todo
	}
	public void rotate(float[] r){
		//todo
	}
	public void rotate(float x, float y, float z){
		//todo
	}

}
