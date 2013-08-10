package com.jjenginejj.world;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class worldobjects {
    public static int gridsize = 50;
    //public static HashMap<String, grid> grids = new HashMap(); // string for "x,y"
    public static ArrayList<grid> grids = new ArrayList<grid>();

    public static void addBlock(block b){
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
	grids.add(g);
    }
    public static void draw(){
	for (grid g : grids) {
	    g.draw();
	}

    }
    public static void getData(){
	for (grid g : grids) {
	    g.tupdate();//todo change to just genverts
	}
    }

    public static void saveBlocks(String file) throws IOException {
	FileOutputStream fileOutputStream = new FileOutputStream(file);
	GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
	ObjectOutputStream objectOutputStream = new ObjectOutputStream(gzipOutputStream);

	List<block> blocks_to_save = new ArrayList<block>();
	for (grid g : grids) {
	    for (block b : g.blocks) {
		blocks_to_save.add(b);
	    }
	}

	objectOutputStream.writeObject(blocks_to_save);

	objectOutputStream.close();
	gzipOutputStream.close();
	fileOutputStream.close();
    }

    public static block[] loadBlocks(String file) throws IOException, ClassNotFoundException {
	FileInputStream fileInputStream = new FileInputStream(file);
	GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
	ObjectInputStream objectInputStream = new ObjectInputStream(gzipInputStream);

	List<block> blocks_to_load = (List<block>)objectInputStream.readObject();

	objectInputStream.close();
	gzipInputStream.close();
	fileInputStream.close();

	return blocks_to_load.toArray(new block[blocks_to_load.size()]);
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
