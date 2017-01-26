package com.example.bessie.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class numbers extends AppCompatActivity {
    // Handles playback of all the sound files
    private MediaPlayer mediaPlayer;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange
                            == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //the audiofocus_gain case means we have regained focus and can
                        //resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AudioFocus_Loss case means we've lost audio focus and
                        // stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    // This listener gets triggered when the {@link MediaPlayer} has completed playing the file
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Creating and setting the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //create an array of words
        final ArrayList<Word> words = new ArrayList<Word>();

        //list of words

        //words add one

        words.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("Nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);
                //release the media player if it currently exists because we are about to
                // play a different sound file.
                releaseMediaPlayer();

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    //Create and setup the {@link MediaPlayer} for the audio resource associated
                    //with the current word
                    mediaPlayer = MediaPlayer.create(numbers.this, word.getAudioResourceId());
                    // Start the audio file
                    mediaPlayer.start();
                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sounds has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }


    }
}
