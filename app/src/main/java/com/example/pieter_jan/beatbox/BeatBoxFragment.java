package com.example.pieter_jan.beatbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by pieter-jan on 12/1/2016.
 */

public class BeatBoxFragment extends Fragment{

    private BeatBox mBeatBox;

    public static BeatBoxFragment newInstance() {
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Destroys the views, but not the fragment: Preserves the SoundPool over a screen rotation

        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beat_box, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_beat_box_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3)); // 3 items on the grid
        // Wire up SoundAdapter
        recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    /*
    A class that hooks up the button the the recyclerview
     */
    private class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Button mButton;
    private Sound mSound;

    public SoundHolder(LayoutInflater inflater, ViewGroup container) {
        super(inflater.inflate(R.layout.list_item_sound, container, false));

        mButton = (Button) itemView.findViewById(R.id.list_item_sound_button);
        mButton.setOnClickListener(this);
    }

    /*
    * Bind a sound to the SoundHolder
     */
    public void bindSound(Sound sound){
        mSound = sound;
        mButton.setText(mSound.getName());
    }

    @Override
    public void onClick(View v) {
        mBeatBox.play(mSound);
    }
}

/*
An adapter class that hooks up to the SoundHolder
 */

    public class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new SoundHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(SoundHolder soundHolder, int position) { // Bind the sounds to a soundholder object
            Sound sound = mSounds.get(position);
            soundHolder.bindSound(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }



}
