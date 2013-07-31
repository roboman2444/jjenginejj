import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class postprocess extends render{
	private static void drawFSQuad(){     //todo make betterly
		glLoadIdentity();
		translateCrap(0f, -2f, 0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1,-1, 0.5f);
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(1,-1, 0.5f);
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(1,1, 0.5f);
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1,1, 0.5f);
		glEnd();
	}
	public static Framebuffer Blur(Framebuffer framebuffer, int passes){ //takes framebuffer to blur, returns output framebuffer
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		switchToOrtho();
		for(int i=0; i < passes; i++){ // todo maybe i can optimize looking up the framebuffers
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("blurTemp").framebufferID);
			GL20.glUseProgram(Shader.shaders.get("gaush"));
			glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
			drawFSQuad();
			glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("blurOut").framebufferID);
			GL20.glUseProgram(Shader.shaders.get("gausv"));
			glBindTexture(GL_TEXTURE_2D, Framebuffer.framebufferList.get("blurTemp").textureID);
			drawFSQuad();
			framebuffer = Framebuffer.framebufferList.get("blurOut");// for returns AND it re-uses it in the next pass
		}
		return framebuffer;
	}
	public static Framebuffer Bloom(Framebuffer framebuffer, int blurPasses){
		switchToOrtho();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		Framebuffer blurred = Blur(framebuffer, blurPasses);
		Framebuffer output = Framebuffer.framebufferList.get("bloomOut");
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, output.framebufferID);

		//if(PostProcessEnabled)glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, Framebuffer.framebufferList.get("pp5").framebufferID);
		//else glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);

		glColor3f(1f, 1f, 1f);
		GL20.glUseProgram(0);
		glBindTexture(GL_TEXTURE_2D, blurred.framebufferID);
		drawFSQuad();
		glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		return output;
	}
	public static Framebuffer Flare(Framebuffer framebuffer){
		switchToOrtho();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		Framebuffer output = Framebuffer.framebufferList.get("flareOut");
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, output.framebufferID);

		glColor3f(1f, 1f, 1f);
		GL20.glUseProgram(Shader.shaders.get("lensflare"));
		//glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		return output;
	}
	public static void PostProcess(){
		Framebuffer framebuffer = Framebuffer.framebufferList.get("renderOut");
		if(PostProcessBloom){
			framebuffer = Bloom(framebuffer, PostProcessBloomBlurPasses); //renders bloom and sets framebuffer to output
		}
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glColor3f(1f, 1f, 1f);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0); //standard
		switchToOrtho();
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
		if(PostProcessFlare){
			//framebuffer = Flare(framebuffer); //renders flare and sets framebuffer to output
			framebuffer = Flare(Framebuffer.framebufferList.get("blurOut"));
		}
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		glColor3f(1f, 1f, 1f);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST); // depth testing, yo
		GL20.glUseProgram(0); //standard
		switchToOrtho();
		glBindTexture(GL_TEXTURE_2D, framebuffer.textureID);
		drawFSQuad();
	}
}
