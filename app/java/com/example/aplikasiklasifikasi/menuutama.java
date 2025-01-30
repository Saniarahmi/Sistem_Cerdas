package com.example.aplikasiklasifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class menuutama extends AppCompatActivity {
    ImageButton deteksi;
    ImageButton jenis;
    ImageButton tentang;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);
        ImageButton imageButton = (ImageButton) findViewById(R.id.deteksi);
        this.deteksi = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.example.deteksidagingayam.menuutama.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(menuutama.this, deteksi.class);
                menuutama.this.startActivity(intent);
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.tentang);
        this.tentang = imageButton2;
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.example.deteksidagingayam.menuutama.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(menuutama.this, tentang.class);
                menuutama.this.startActivity(intent);
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.jenis);
        this.jenis = imageButton3;
        imageButton3.setOnClickListener(new View.OnClickListener() { // from class: com.example.deteksidagingayam.menuutama.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(menuutama.this, jenis.class);
                menuutama.this.startActivity(intent);
            }
        });
    }
}
