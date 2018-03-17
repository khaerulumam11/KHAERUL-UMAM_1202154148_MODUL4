package com.example.khaerulumam.khaerulumam_1202154148_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

public class PencariGambar extends AppCompatActivity {

    ImageView a;
    EditText b;
    Button c;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencari_gambar);

        a = (ImageView) findViewById(R.id.gambar);
        b = (EditText) findViewById(R.id.urlgambar);

        c = (Button) findViewById(R.id.cari);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = b.getText().toString();
                new DownloadImage().execute(link);
            }
        });

        //mensetting toolbar yang akan muncul di atas dengan nama Pencari Gambar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.pencari);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Pencarian Gambar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class DownloadImage extends AsyncTask<String,Void,Bitmap> {@Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        mProgressDialog = new ProgressDialog(PencariGambar.this);
        // Set progressdialog title
        mProgressDialog.setTitle("Download Image Tutorial");
        // Set progressdialog message
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            a.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}
