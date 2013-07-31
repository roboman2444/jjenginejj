import java.io.File;
import java.util.Locale;

public class jjenginejj {
	static {
		String lwjgl_folder = "libs" + File.separator;
		final String OS = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);

		if (OS.contains("win"))
			lwjgl_folder += "win";
		else if (OS.contains("mac"))
			lwjgl_folder += "osx";
		else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
			lwjgl_folder += "lin";
		else if (OS.contains("sunos"))
			lwjgl_folder += "sol";
		System.setProperty("org.lwjgl.librarypath", new File(lwjgl_folder).getAbsolutePath());
	}
	public static void main(String[] args){
		render.init();
		input.init();
		while(true){
			render.draw();
			gamecode.run();
			input.getInput();
		}

	}
}
