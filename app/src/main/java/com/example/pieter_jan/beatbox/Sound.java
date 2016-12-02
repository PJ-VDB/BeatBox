package com.example.pieter_jan.beatbox;

/**
 * Created by pieter-jan on 12/1/2016.
 */

public class Sound {

    private String mAssetPath; // the path of the song
    private String mName; // the song name
    private Integer mSoundId; // Integer can be assigned a null value

    public Sound(String assetPath) {
        mAssetPath = assetPath; // the full path where the song can be found
        String[] components = mAssetPath.split("/"); // split the path at every "/"
        String filename = components[components.length - 1]; // the last part of the directory, hence the filename
        mName = filename.replace(".wav", ""); // remove the '.wav' part of the filename, this forms the name of the song
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
