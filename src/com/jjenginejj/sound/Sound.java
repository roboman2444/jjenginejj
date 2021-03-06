package com.jjenginejj.sound;

import org.lwjgl.openal.AL10;

public final class Sound {
    private final String name;
    private final int source;
    private final int buffer;

    private float speed;
    private float volume;
    private boolean looping;

	private float[] pos = new float[3];

    private SoundState state;

    protected Sound(String name, int source, int buffer) {
        this.name = name;
        this.source = source;
        this.buffer = buffer;

        speed = 1F;
        volume = 1F;
        state = SoundState.STOPPED;
    }

    public void setPos(float[] inpos) {
	this.pos = inpos;

	AL10.alSource3f(source, AL10.AL_POSITION, pos[0], pos[1], pos[2]);
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

    private static float lpos[] = new float[3];
    public static void setListenerPos(float[] inpos) {
	lpos = inpos;
	AL10.alListener3f(AL10.AL_POSITION, inpos[0], inpos[1], inpos[2]);
    }

public static float[] getListenerPos(){
	return lpos;
}

}
