package com.jjenginejj.world;

import java.util.HashMap;

public class worldobjects {
	public static int gridsize = 50;
	public static HashMap<String, grid> grids = new HashMap(); // string for "x,y"

	public static void addBlock(block b){
		//todo make this hashmap better
		String key = (int)(b.pos.x/gridsize) + "," + (int)(b.pos.y/gridsize);

		grid g = grids.get(key);
		if(g == null){ // if no com.jjjenginejj.render.com.jjenginejj.render.model.grid exists there
			g = grids.put(key, new grid()); // adds new com.jjjenginejj.render.com.jjenginejj.render.model.grid
		}
		g.addBlock(b); //add com.jjjenginejj.render.com.jjenginejj.render.model.block to com.jjjenginejj.render.com.jjenginejj.render.model.grid

	}
	public static void deleteBlock(){//maybe use ids, idk yet
		 // i dunno if i should do it by com.jjjenginejj.render.com.jjenginejj.render.model.block object or by com.jjjenginejj.render.com.jjenginejj.render.model.block id or what...
		/*
		String key = (int)(b.posx/gridsize) + "," + (int)(b.posy/gridsize);

		com.jjjenginejj.render.com.jjenginejj.render.model.grid g = grids.get(key);
		if(g == null){ // if no com.jjjenginejj.render.com.jjenginejj.render.model.grid exists there
			System.out.println("There is no com.jjjenginejj.render.com.jjenginejj.render.model.grid for this com.jjjenginejj.render.com.jjenginejj.render.model.block that im deleting");
			return;
		}
		*/
		// delete the com.jjjenginejj.render.com.jjenginejj.render.model.block from the cubearray
		//if the cubearray now has no cubes, delete it (it would have already removed its vbo)
	}
}
