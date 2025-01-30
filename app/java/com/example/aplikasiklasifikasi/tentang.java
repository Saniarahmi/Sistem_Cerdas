package com.example.aplikasiklasifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class tentang extends AppCompatActivity {

    Button pakar;
    Button kami;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        this.kami = (Button) findViewById(R.id.kami);



        this.kami.setOnClickListener(new View.OnClickListener() { // from class: com.example.deteksidagingayam.menuutama.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(tentang.this, kami.class);
                tentang.this.startActivity(intent);
            }
        });


    }
}