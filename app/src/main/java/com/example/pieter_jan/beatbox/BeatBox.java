package com.example.pieter_jan.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pieter-jan on 12/1/2016.
 */

public class BeatBox {

    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds"; // folder where the sounds can be found
    private static final int MAX_SOUNDS = 5; // Amount of sounds that can be played at the same time

    private AssetManager mAssets;
    private List<Sound> mSounds = new ArrayList<>(); // the list with the sounds
    private SoundPool mSoundPool;

    public BeatBox(Context context){
        mAssets = context.getAssets();
        // This old constructor is deprecated, but we need it for compatability
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0); // STREAM_MUSIC will put the sound on the same level as music and games
        loadSounds();
    }


    /**
     * Method that looks in the assets
     */
    public void loadSounds(){
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER); // get the sounds from the assetsfolder
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException ioe){
            Log.e(TAG, "Could not list assets", ioe );
            return;
        }

        for (String filename : soundNames){
            try{
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void load(Sound sound) throws IOException{ // openFD throws IOException so load needs to throw this too
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if (soundId == null){
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * Release the sounds from memory
     */
    public void release(){
        mSoundPool.release();
    }

}
