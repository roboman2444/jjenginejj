package com.jjenginejj.entity;

import com.jjenginejj.sound.Sound;
import com.jjenginejj.sound.SoundSystem;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Entity implements Serializable {
    private static final ArrayList<Entity> entities = new ArrayList<Entity>();
    protected int ID;
    protected final float[] pos = new float[3];
    protected boolean isNetwork;
    protected boolean isPlayingSound;
    protected Sound currentSound;

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

    private Entity() { }

    public float getX() {
	return pos[0];
    }

    public float getY() {
	return pos[1];
    }

    public float getZ() {
	return pos[2];
    }

    public void setX(float x) {
	pos[0] = x;
	if (selectedSound != null) {
	    selectedSound.setPos(pos[0], pos[1], pos[2]);
	}
    }

    public void setY(float y) {
	pos[1] = y;
	if (selectedSound != null) {
	    selectedSound.setPos(pos[0], pos[1], pos[2]);
	}
    }

    public void setZ(float z) {
	pos[3] = z;
	if (selectedSound != null) {
	    selectedSound.setPos(pos[0], pos[1], pos[2]);
	}
    }

    public boolean isNetworkEntity() {
	return isNetwork;
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

    private Sound selectedSound;
    public void playSound(Sound sound) {
	selectedSound = sound;
	sound.setPos(pos[0], pos[1], pos[2]);
	sound.play();
	isPlayingSound = true;
    }

    public Sound getCurrentSound() {
	return selectedSound;
    }

    public void playCurrentSound() {
	if (selectedSound != null)
	    selectedSound.play();
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
