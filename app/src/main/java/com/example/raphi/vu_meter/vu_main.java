package com.example.raphi.vu_meter;


import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import android.content.Context;


public class vu_main extends AppCompatActivity {

    public static final String TAG = "VU_MAIN";

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vu_main);

        //Programmbeginn
        //Button referenzieren
        Button messung_starten;
        messung_starten = (Button) findViewById(R.id.button_go);

        //Onclick Ereignis
        messung_starten.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Text View referenzieren
                TextView textview_pegel;
                textview_pegel = (TextView) findViewById(R.id.view_pegel);

                //Progress Bar referenzieren
                ProgressBar progress_pegel;
                progress_pegel = (ProgressBar) findViewById(R.id.progress_pegel);

                //Media Recorder initialiesieren
                int amplitude;
                MediaRecorder mRecorder;
                mRecorder = new MediaRecorder();
                File audiofile = null;



                textview_pegel.setText("Messung läuft!");
                progress_pegel.setMax(32676);
                progress_pegel.setProgress(400);


                //File sampleDir = Environment.getExternalStorageDirectory();
                //File sampleDir = ""

                //Context context;

                try {
                    audiofile = File.createTempFile("sound", ".3gp", null);
                } catch (IOException e) {
                    Log.e(TAG, "sdcard access error");
                    return;
                }

                //Media Recorder starten
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(audiofile.getAbsolutePath());
                //mRecorder.setAudioSamplingRate(8000);
                try{
                    mRecorder.prepare();


                } catch(IOException e){
                    e.printStackTrace();
                }

                isRecording = true;
                mRecorder.start();
                Log.d(TAG, "recorder started");

                amplitude = mRecorder.getMaxAmplitude();
                progress_pegel.setProgress(amplitude);


                int a;

                for (a=0; a<= 99999; a++){
                    Log.d(TAG, amplitude+"");
                }

                //Media Recorder anhalten
                if(mRecorder != null && isRecording == true){
                    isRecording = false;
                    mRecorder.stop();
                    mRecorder.reset();
                    mRecorder.release();

                }


            }
        });

        //Exit-Button
        Button exit_app;
        exit_app = (Button) findViewById(R.id.button_exit);

        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                System.exit(0);
            }
        });
    }
}
