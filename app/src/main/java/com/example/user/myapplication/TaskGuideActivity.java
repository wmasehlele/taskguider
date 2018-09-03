package com.example.user.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

class SpeakThread implements Runnable {

    private TextToSpeech myTTS;
    private String textMessage;
    Thread runner;

    public SpeakThread(TextToSpeech tts, String message) {
        this.myTTS = tts;
        this.textMessage = message;
    }

    public SpeakThread(String threadName) {
        runner = new Thread(this, threadName);
        System.out.println(runner.getName());
        runner.start();
    }

    public void run() {
        speak (this.textMessage);
    }

    private void speak (String message) {
        if (Build.VERSION.SDK_INT >= 21) {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}


public class TaskGuideActivity extends AppCompatActivity {

    private TextView speechResult, lblTaskTimer, taskTitle, taskDescription;
    private Button recordSpeech;

    private TextToSpeech myTTS;
    private SpeechRecognizer mySpeechRecognizer;
    private String TAG = "SPEECH RECOGNIZER";

    private List<HashMap<String, String>> aList;
    private int taskID = 0;

    private int CountSeconds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_guide);
        this.setTitle("TASK GUIDE");

        speechResult = (TextView) findViewById(R.id.lblSpeechResults);
        taskTitle = (TextView) findViewById(R.id.lblTaskTitle);
        lblTaskTimer = (TextView) findViewById(R.id.lblTaskTimer);
        recordSpeech = (Button) findViewById(R.id.btnRecordSpeech);
        taskDescription = (TextView) findViewById(R.id.lblTaskDescription);

        aList = new DataSource().getData();
        Intent intent = getIntent();
        taskID = intent.getIntExtra("taskID", 0);
        taskTitle.setText(aList.get(taskID).get("listview_title").toString());
        taskDescription.setText(aList.get(taskID).get("listview_discription").toString());

        Thread speakThread = new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "SPEAKING");
                initializeTextToSpeech ();
            }
        };
        speakThread.start();

        Thread listenThread = new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "LISTENING");
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, 10);
//                } else {
//                    Toast.makeText(TaskGuideActivity.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
//                }
                speechResult.setText("Listening ...");
                recordSpeech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, 10);
                        } else {
                            Toast.makeText(TaskGuideActivity.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
        listenThread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechResult.setText(result.get(0));
                }
                break;
        }
    }

    private void initializeTextToSpeech () {

        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            if (myTTS.getEngines().size() == 0) {
                Toast.makeText(TaskGuideActivity.this, "There is not TTS engine on your device", Toast.LENGTH_SHORT).show();
            } else {
                myTTS.setLanguage(Locale.UK);
                try {
                    speak ("Starting audio task guide for "+ aList.get(taskID).get("listview_title").toString());
                    Thread.sleep(6000);

                    speak ("The task will take you upto "+ aList.get(taskID).get("listview_task_timer").toString());
                    Thread.sleep(6000);

                    String [] instructions = aList.get(taskID).get("listview_task_steps").split(";");

                    for (int i=0; i < instructions.length; i++){
                        speak(instructions[i]);
                        Thread.sleep(5000);
                    }

                } catch (InterruptedException e) {
                    Log.d(TAG, "Thread Exception Occurred" + e.getStackTrace());
                }
            }
            }
        });
    }

    private void speak (String message) {
        if (Build.VERSION.SDK_INT >= 21) {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onPause () {
        super.onPause();
        Log.d(TAG, "onPause");
        if (myTTS.isSpeaking()){
            myTTS.stop();
            myTTS.shutdown();
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        Log.d(TAG, "onStop");
        if (myTTS.isSpeaking()){
            myTTS.stop();
            myTTS.shutdown();
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (myTTS.isSpeaking()){
            myTTS.stop();
            myTTS.shutdown();
        }
    }
}
