package com.jjenginejj.render.texture;

import org.lwjgl.opengl.GL11;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Hashtable;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private static final ColorModel glAlphaColorModel;
    private static final ColorModel glColorModel;
    static {

	glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
		new int[] {8,8,8,8},
		true,
		false,
		ComponentColorModel.TRANSLUCENT,
		DataBuffer.TYPE_BYTE);

	glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
		new int[] {8,8,8,0},
		false,
		false,
		ComponentColorModel.OPAQUE,
		DataBuffer.TYPE_BYTE);
    }


    int width;
    int height;
    public int textureID;
    public static HashMap<String, Texture> textureList = new HashMap<String, Texture>();
    public Texture(String filename) throws IOException {

	textureID= GL11.glGenTextures();
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
	//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
	//GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0,GL11.GL_RGBA, GL11.GL_INT, (java.nio.ByteBuffer) null);
	drawImage(filename);
	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	System.out.println("Loaded Texture " + filename + "with size " + width + "x" + height + " and textureID " + textureID);
    }
    public static Texture addTexture(String filename){
	Texture t = textureList.get(filename);
	if(t == null){
	    try {
		textureList.put(filename, new Texture(filename));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return t;
    }

    private void drawImage(String filename) throws IOException {
	BufferedImage image = loadImage(filename);
	int pixel;
	if (image.getColorModel().hasAlpha()) {
	    pixel = GL_RGBA;
	} else {
	    pixel = GL_RGB;
	}

	ByteBuffer buffer = convertImageData(image);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, get2Fold(width), get2Fold(height), 0, pixel, GL_UNSIGNED_BYTE, buffer);
    }

    private int get2Fold(int fold) {
	int ret = 2;
	while (ret < fold) {
	    ret *= 2;
	}
	return ret;
    }

    private ByteBuffer convertImageData(BufferedImage bufferedImage) {
	ByteBuffer imageBuffer;
	WritableRaster raster;
	BufferedImage texImage;

	int texWidth = bufferedImage.getWidth();
	int texHeight = bufferedImage.getHeight();

        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }

	width = texWidth;
	height = texHeight;

	// create a raster that can be used by OpenGL as a source
	// for a texture
	if (bufferedImage.getColorModel().hasAlpha()) {
	    raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
	    texImage = new BufferedImage(glAlphaColorModel,raster,false,new Hashtable());
	} else {
	    raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,texWidth,texHeight,3,null);
	    texImage = new BufferedImage(glColorModel,raster,false,new Hashtable());
	}

	// copy the source image into the produced image
	Graphics g = texImage.getGraphics();
	g.setColor(new Color(0f,0f,0f,0f));
	g.fillRect(0,0,texWidth,texHeight);
	g.drawImage(bufferedImage,0,0,null);

	// build a byte buffer from the temporary image
	// that be used by OpenGL to produce a texture.
	byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

	imageBuffer = ByteBuffer.allocateDirect(data.length);
	imageBuffer.order(ByteOrder.nativeOrder());
	imageBuffer.put(data, 0, data.length);
	imageBuffer.flip();

	return imageBuffer;
    }

    private static BufferedImage loadImage(String ref) throws IOException {
	URL url = Texture.class.getClassLoader().getResource(ref);

	if (url == null) {
	    throw new IOException("Cannot find: " + ref);
	}

	// due to an issue with ImageIO and mixed signed code
	// we are now using good oldfashioned ImageIcon to load
	// images and the paint it on top of a new BufferedImage
	Image img = new ImageIcon(url).getImage();
	BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	Graphics g = bufferedImage.getGraphics();
	g.drawImage(img, 0, 0, null);
	g.dispose();

	return bufferedImage;
    }
}
