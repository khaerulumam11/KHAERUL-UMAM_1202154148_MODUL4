package com.example.khaerulumam.khaerulumam_1202154148_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button a, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = (Button) findViewById(R.id.pencari);
        b = (Button) findViewById(R.id.list);

        //mensetting toolbar yang akan muncul di atas dengan nama pilih menu
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pilihan Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ab = new Intent(MainActivity.this,PencariGambar.class);
                startActivity(ab);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ac = new Intent(MainActivity.this,ListNamaMahasiswa.class);
                startActivity(ac);
            }
        });
    }
}
