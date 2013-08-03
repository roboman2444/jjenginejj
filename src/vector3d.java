public class vector3d {
	float x;
	float y;
	float z;
	//constructors
	public vector3d(float ix, float iy, float iz){
		x = ix;
		y = iy;
		z = iz;
	}
	public vector3d(float i){
		x = i;
		y = i;
		z = i;
	}
	public vector3d(float[] i){
		x = i[0];
		y = i[1];
		z = i[2];
	}
	//do i even need this?
	public static vector3d arrayToVec(float[] i){
		return new vector3d(i);
	}
	public static float[] vecToArray(vector3d v){
		float[] i = new float[3];
		i[0] = v.x;
		i[1] = v.y;
		i[2] = v.z;
	}
	//rotate
	public static vector3d rotate(vector3d v, float angle, vector3d axis){
		//todo
		//todo
		//todo
		return v;
	}
	//translate TODO make sure it doesnt overwrite the object
	public static vector3d translate(vector3d v, vector3d i){
		v.x += i.x;
		v.y += i.y;
		v.z += i.z;
		return v;
	}
	public static vector3d translate(vector3d v, float x, float y, float z){
		v.x += x;
		v.y += y;
		v.z += z;
		return v;
	}

	//scale  TODO make sure it doesnt overwrite the object
	public static vector3d scale(vector3d v, vector3d i){
		v.x *= i.x;
		v.y *= i.y;
		v.z *= i.z;
		return v;
	}
	public static vector3d scale(vector3d v, float i){
		v.x *= i;
		v.y *= i;
		v.z *= i;
		return v;
	}
	public static vector3d scale(vector3d v, float x, float y, float z){
		v.x *= x;
		v.y *= y;
		v.z *= z;
		return v;
	}

	//cross product
	public static vector3d cross(vector3d a, vector3d b){
		vector3d v = a; //TODO eddie make sure this works
		v.x =(a.x*b.z)-(b.x*a.z);
		v.y =(a.z*b.x)-(b.z*a.x);
		v.z =(a.x*b.y)-(b.x*b.y);
		return v;
	}

	//dot product
	public static float dot(vector3d a, vector3d b){
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}

	//length of vector
	public static float length(vector3d v){
		return (float)Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
	}

	//normalize vector
	public static vector3d normalize(vector3d a){
		return scale(a, 1/length(a)); // that SHOULD work
	}
}
