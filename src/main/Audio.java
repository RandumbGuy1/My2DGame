package main;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

public class Audio {
    Clip clip;
    Dictionary<String, URL> sounds = new Hashtable<>();

    public Audio() {
        File dir = new File("res/sound");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                String name = child.getName();
                sounds.put(name, getClass().getResource("/sound/" + name));
            }
        }
    }

    public void setFile(String key) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sounds.get(key));
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ignored) {
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(-1);
    }

    public void stop() {
        clip.stop();
    }
}
