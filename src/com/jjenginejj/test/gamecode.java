package com.jjenginejj.test;

import com.jjenginejj.render.camera;
import com.jjenginejj.system.input;
import com.jjenginejj.system.util.Matrix4x4;

import java.lang.reflect.Array;
import java.util.Arrays;

public class gamecode {
	public static float makeVectorsX;
	public static float makeVectorsY;
	public static float makeVectorsZ;

	private static boolean pauseReleased, mReleased, fReleased, pReleased, cReleased, bReleased;
	private static float mouseSensitivity = 0.1f;
	public static void run(){
		//com.jjenginejj.render.render.ppwhatx += 1f * nbody.timescale;
		//com.jjenginejj.render.render.ppwhaty += 2f * nbody.timescale;
		camera.rot[2] -= input.mouseDeltaX*mouseSensitivity;
		camera.rot[1] += input.mouseDeltaY*mouseSensitivity;
		if(camera.rot[1] > 180) camera.rot[1] = 180;
		if(camera.rot[1] < 0) camera.rot[1] = 0;
		if(input.keyESC)System.exit(0);
		float tempscale[] = new float[3];
		tempscale[0] = 1.0f;
		tempscale[1] = 1.0f;
		tempscale[2] = 1.0f;
		float dir[] = new float[3];
		if(input.keyW){
			dir[0] += 0;
			dir[1] += 0.1f; //FORWARD BY UNIT 0.1
			dir[2] += 0;

		}
		if(input.keyA){
			dir[0] += 0.1f;
			dir[1] += 0; //FORWARD BY UNIT 0.1
			dir[2] += 0;

		}
		if(input.keyS){
			dir[0] += 0;
			dir[1] += -0.1f; //FORWARD BY UNIT 0.1
			dir[2] += 0;

		}
		if(input.keyD){
			dir[0] += -0.1f;
			dir[1] += 0; //FORWARD BY UNIT 0.1
			dir[2] += 0;

		}
		if(dir[0]!= 0 || dir[1] != 0 || dir[2] != 0){
			Matrix4x4 cm = Matrix4x4.createFromEntity(new float[3], camera.rot,tempscale);
			float tempcm[] = new float[3];
			tempcm = Matrix4x4.transform(cm, dir);
			camera.pos[0] += tempcm[0];
			camera.pos[1] += tempcm[1];
			camera.pos[2] += tempcm[2];
		}
		//System.out.println("camera pos at: " + camera.pos[0] +","+ camera.pos[1] +"," +camera.pos[2]);

		/*
		if(input.keySPACE){
			if(pauseReleased)nbody.paused = !nbody.paused;
			pauseReleased = false;

		} else pauseReleased = true;
                  */


		if(input.keyM){
			if(mReleased)input.toggleMouseSnap();
			mReleased = false;

		} else mReleased = true;
	}
	/*
	 * makeVectors(float pan, float tilt);
	 * makes a vector of length 1 for each rotation
	 * Returns nothing, x y and z vectors are in public var makeVectorsX etc.
	 */
	public static void makeVectors(float pan, float tilt){
		double radtilt = Math.toRadians((float)tilt);
		double radpan = Math.toRadians((float)pan);
		double tX = 0 ,tY = 1,tZ = 0;
		double ttX, ttY, ttZ;
		//rotate around Z
		ttX = Math.cos(radpan)*tX - Math.sin(radpan)*tY;
		ttY = Math.sin(radpan)*tX - Math.cos(radpan)*tY;
		tX = ttX;
		tY = ttY;
		//rotate around X
		ttY = Math.cos(radtilt)*tY - Math.sin(radtilt)*tZ;
		ttZ = Math.sin(radtilt)*tY + Math.cos(radtilt)*tZ;
		tY = ttY;
		tZ = ttZ;
		makeVectorsX = (float)tX;
		makeVectorsY = (float)tY;
		makeVectorsZ = (float)tZ;
	}
	public static void init(){

	}
}