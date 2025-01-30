package com.example.aplikasiklasifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(1024, 1024);
        Thread thread = new Thread() { // from class: com.example.deteksidagingayam.MainActivity.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MainActivity mainActivity;
                Intent intent;
                try {
                    try {
                        sleep(4000L);
                        mainActivity = MainActivity.this;
                        intent = new Intent(MainActivity.this, menuutama.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        mainActivity = MainActivity.this;
                        intent = new Intent(MainActivity.this, menuutama.class);
                    }
                    mainActivity.startActivity(intent);
                    MainActivity.this.finish();
                } catch (Throwable th) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, menuutama.class));
                    MainActivity.this.finish();
                    throw th;
                }
            }
        };
        thread.start();
    }
}
