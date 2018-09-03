package com.example.user.myapplication;

import android.os.Build;
import android.speech.tts.TextToSpeech;

class ThreadSpeak implements Runnable {

    private TextToSpeech myTTS;
    private String textMessage;
    Thread runner;

    public ThreadSpeak(TextToSpeech tts, String message) {
        this.myTTS = tts;
        this.textMessage = message;
    }

    public ThreadSpeak(String threadName) {
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