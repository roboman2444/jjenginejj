import java.util.ArrayList;
import java.util.HashMap;

public class worldobjects {
	public static int gridsize = 50;
	public static HashMap<String, grid> grids = new HashMap(); // string for "x,y"

	public static void addBlock(block b){
		//todo make this hashmap better
		String key = (int)(b.posx/gridsize) + "," + (int)(b.posy/gridsize);

		grid g = grids.get(key);
		if(g == null){ // if no grid exists there
			g = grids.put(key, new grid()); // adds new grid
		}
		g.addBlock(b); //add block to grid

	}
	public static void deleteBlock(){//maybe use ids, idk yet
		 // i dunno if i should do it by block object or by block id or what...
		/*
		String key = (int)(b.posx/gridsize) + "," + (int)(b.posy/gridsize);

		grid g = grids.get(key);
		if(g == null){ // if no grid exists there
			System.out.println("There is no grid for this block that im deleting");
			return;
		}
		*/
		// delete the block from the cubearray
		//if the cubearray now has no cubes, delete it (it would have already removed its vbo)
	}
}
