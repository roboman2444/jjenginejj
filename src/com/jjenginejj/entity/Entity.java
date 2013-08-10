package com.jjenginejj.entity;

import com.jjenginejj.render.model;
import com.jjenginejj.render.texture.Texture;
import com.jjenginejj.sound.Sound;
import com.jjenginejj.sound.SoundSystem;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Serializable {
    private static final ArrayList<Entity> entities = new ArrayList<Entity>();
    private static boolean sorted;
    protected int ID;
	protected float[] pos = new float[3];
    protected boolean isNetwork;
    protected boolean isPlayingSound;
    protected Sound currentSound;
    protected model _model;
    protected Texture texture;
	protected float[] rot = new float[3];
	protected float[] size = new float[3];// for phys
	protected float[] scale = new float[3]; //for rendering

    public static List<Entity> getEntities() {
	return entities;
    }

    public static Entity createEntity() {
	Entity e = new Entity();
	e.ID = entities.size();
	entities.add(e);
	return e;
    }

    public static Entity createEntity(float x, float y, float z) {
	Entity e = createEntity();
	e.pos[0] = x;
	e.pos[1] = y;
	e.pos[2] = z;
	return e;
    }
	public static Entity createEntity(float[] inpos) {
		Entity e = createEntity();
		e.pos = inpos;
		return e;
	}

    public static Entity createEntity(model _model) {
	Entity e = createEntity();
	e.setModel(_model);
	return e;
    }

    public static Entity createEntity(model _model, Texture texture) {
	Entity e = createEntity(_model);
	e.texture = texture;
	return e;
    }

    private Entity() { }

	public float[] getPos(){
		return pos;
	}

public void setPos(float[] inpos){
	pos = inpos;
}

    public boolean isNetworkEntity() {
	return isNetwork;
    }

    public int getTextureID() {
	return texture.textureID;
    }

    public Texture getTextureObject() {
	return texture;
    }

    public model getModel() {
	return _model;
    }

    public boolean hasModel() {
	return _model != null;
    }

    public void setModel(model _model) {
	if (this._model != null)
	    this._model.detachEntity(this);

	if (_model == null)
	    this._model = null;
	else {
	    this._model = _model;
	    this._model.attachEntity(this);
	}
    }

    public void removeModel() {
	setModel(null);
    }

    public void playSound(String file) throws IOException {
	Sound s;
	if (SoundSystem.exists(file)) {
	    s = SoundSystem.getSound(file);
	} else {
	    s = SoundSystem.loadSound(file, file);
	}

	playSound(s);
    }
    public void playSound(Sound sound) {
	currentSound = sound;
	sound.setPos(pos);
	sound.play();
	isPlayingSound = true;
    }

    public Sound getCurrentSound() {
	return currentSound;
    }

    public void playCurrentSound() {
	if (currentSound != null)
	    currentSound.play();
    }

    public int getID() {
	return ID;
    }

    @Override
    public int hashCode() {
	return ID;
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Entity)) return false;
	Entity e = (Entity)obj;
	return e.ID == ID;
    }
}
