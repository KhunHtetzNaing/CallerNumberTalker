package com.htetznaing.callernumbertalker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class Play {
    MediaPlayer m;
    String fucker [];
    int [] voice = {R.raw.s0,R.raw.s1,R.raw.s2,R.raw.s3,R.raw.s4,R.raw.s5,R.raw.s6,R.raw.s7,R.raw.s8,R.raw.s9};
    Context context;
    String no;
    LOL lol = new LOL();
    public Play(Context context,String no) {
        this.context = context;
        this.no = no;
        fucker = no.split("");
        lol.execute();
    }

    public void play(int i) {
        m = MediaPlayer.create(context, i);
        if (m.isPlaying()) {
            m.stop();
        }
        m.start();
    }

    class LOL extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            for (int i=0;i<fucker.length;i++){
                switch (fucker[i]){
                    case "၀":play(voice[0]);break;
                    case "၁":play(voice[1]);break;
                    case "၂":play(voice[2]);break;
                    case "၃":play(voice[3]);break;
                    case "၄":play(voice[4]);break;
                    case "၅":play(voice[5]);break;
                    case "၆":play(voice[6]);break;
                    case "၇":play(voice[7]);break;
                    case "၈":play(voice[8]);break;
                    case "၉":play(voice[9]);break;
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
