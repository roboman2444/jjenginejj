package com.jjenginejj.render.model;

import java.util.ArrayList;
import java.util.HashMap;

public class worldobjects {
	public static int gridsize = 50;
	//public static HashMap<String, grid> grids = new HashMap(); // string for "x,y"
	public static ArrayList<grid> grids = new ArrayList();

	public static void addBlock(block b){
		//todo make this hashmap better
		/*String key = (int)(b.pos.x/gridsize) + "," + (int)(b.pos.y/gridsize);

		grid g = grids.get(key);
		if(g == null){ // if no com.jjjenginejj.render.com.jjenginejj.render.model.model.grid exists there
			g = grids.put(key, new grid()); // adds new com.jjjenginejj.render.com.jjenginejj.render.model.model.grid
		}

		g.addBlock(b); //add com.jjjenginejj.render.com.jjenginejj.render.model.model.block to com.jjjenginejj.render.com.jjenginejj.render.model.model.grid
                */
		int tx = (int)b.pos[0]/gridsize;
		int ty = (int)b.pos[1]/gridsize;
		for(int i = 0; i<grids.size(); i++){
			grid g = grids.get(i);
			if(g.x == tx & g.y == ty){
				g.addBlock(b);
				return;
			}
		}
		grid g = new grid(tx, ty);
		g.addBlock(b);
	}
	public static void draw(){
		for (int i = 0; i<grids.size(); i++){
			grid g = grids.get(i);
			g.draw();
		}
	}
	public static void deleteBlock(){//maybe use ids, idk yet
		 // i dunno if i should do it by com.jjjenginejj.render.com.jjenginejj.render.model.model.block object or by com.jjjenginejj.render.com.jjenginejj.render.model.model.block id or what...
		/*
		String key = (int)(b.posx/gridsize) + "," + (int)(b.posy/gridsize);

		com.jjjenginejj.render.com.jjenginejj.render.model.model.grid g = grids.get(key);
		if(g == null){ // if no com.jjjenginejj.render.com.jjenginejj.render.model.model.grid exists there
			System.out.println("There is no com.jjjenginejj.render.com.jjenginejj.render.model.model.grid for this com.jjjenginejj.render.com.jjenginejj.render.model.model.block that im deleting");
			return;
		}
		*/
		// delete the com.jjjenginejj.render.com.jjenginejj.render.model.model.block from the cubearray
		//if the cubearray now has no cubes, delete it (it would have already removed its vbo)
	}
}
