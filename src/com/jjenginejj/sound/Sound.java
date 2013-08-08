package com.jjenginejj.sound;

import org.lwjgl.openal.AL10;

public final class Sound {
    private final String name;
    private final int source;
    private final int buffer;

    private float speed;
    private float volume;
    private boolean looping;

    private float x, y, z;

    private SoundState state;

    protected Sound(String name, int source, int buffer) {
        this.name = name;
        this.source = source;
        this.buffer = buffer;

        speed = 1F;
        volume = 1F;
        state = SoundState.STOPPED;
    }

    public void setPos(float x, float y, float z) {
	this.x = x;
	this.y = y;
	this.z = z;

	AL10.alSource3f(source, AL10.AL_POSITION, x, y, z);
    }

    public void play() {
        AL10.alSourcePlay(source);
        state = SoundState.PLAYING;
    }

    public void stop() {
        AL10.alSourceStop(source);
        state = SoundState.STOPPED;
    }

    public void rewind(boolean play) {
        AL10.alSourceRewind(source);

        if (play) {
            play();
        }
    }

    public void setLooping(boolean looping) {
        this.looping = looping;

        AL10.alSourcei(source, AL10.AL_LOOPING, (looping ? AL10.AL_TRUE : AL10.AL_FALSE));
    }

    public boolean isLooping() {
        return looping;
    }

    public void pause() {
        if (state == SoundState.PLAYING) {
            AL10.alSourcePause(source);
            state = SoundState.PAUSED;
        } else if (state == SoundState.PAUSED) {
            AL10.alSourcePlay(source);
            state = SoundState.PLAYING;
        }
    }

    public String getName() {
        return name;
    }

    public int getSource() {
        return source;
    }

    public int getBuffer() {
        return buffer;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        AL10.alSourcef(source, AL10.AL_PITCH, speed);
        this.speed = speed;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        if (volume < 0) {
            volume = 0;
        } else if (volume > 1) {
            volume = 1;
        }

        AL10.alSourcef(source, AL10.AL_GAIN, volume);

        this.volume = volume;
    }

    public SoundState getState() {
        return state;
    }

    private static float lx, ly, lz;
    public static void setListenerPos(float x, float y, float z) {
	lx = x;
	ly = y;
	lz = z;
	AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    public static float getListenerX() {
	return lx;
    }

    public static float getListenerY() {
	return ly;
    }

    public static float getListenerZ() {
	return lz;
    }

}
