package com.jjenginejj.sound;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class SoundSystem {
    private static List<Sound> loadedSounds = new ArrayList<Sound>();

    public static Sound loadSound(String name, String filePath) throws IOException {
        if (exists(name)) {
            throw new IllegalArgumentException("The specified sound name is already registered!");
        }
        WaveData wavFile;
        String resource = "sound" + File.separator + filePath;
        InputStream stream = SoundSystem.class.getClassLoader().getResourceAsStream(resource);
        if (stream == null) {
            resource = "sound/" + filePath;
            stream = SoundSystem.class.getClassLoader().getResourceAsStream(resource);
            if (stream == null)
                throw new IOException("Can't find sound \"" + resource + "\"!");
        }
        wavFile = WaveData.create(stream);
        if (wavFile == null) {
            URL url = SoundSystem.class.getClassLoader().getResource(resource);
            wavFile = WaveData.create(url);
            if (wavFile == null)
                throw new IOException("Failed to create WaveData for \"" + resource + "\"!");
        }
        int buffer = AL10.alGenBuffers();
        AL10.alBufferData(buffer, wavFile.format, wavFile.data, wavFile.samplerate);
        stream.close();
        wavFile.dispose();

        int source = AL10.alGenSources();
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);

        Sound sound = new Sound(name, source, buffer);
        loadedSounds.add(sound);

        return sound;
    }

    public static boolean exists(String name) {
        for (Sound sound : loadedSounds) {
            if (sound.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Sound getSound(String name) {
        for (Sound sound : loadedSounds) {
            if (sound.getName().equals(name)) {
                return sound;
            }
        }
        return null;
    }


    public static  void unloadSound(String name) {
        Sound sound = getSound(name);

        if (sound == null) {
            return;
        }

        AL10.alDeleteBuffers(sound.getBuffer());
        loadedSounds.remove(sound);
    }
}
