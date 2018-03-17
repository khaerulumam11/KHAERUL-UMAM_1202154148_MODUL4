package com.example.khaerulumam.khaerulumam_1202154148_studycase4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {

    private ListView mListView;
    private ProgressBar mProgressBar;
    private String [] mUsers= {
            "Umam",
            "Reza",
            "Bregas",
            "Yadi",
            "Ikhsan",
            "Yoel",
            "Indra",
            "Jimmy",

    };

    private ItemListView itemListView;
    private Button a;

    ListNamaFragment fragment;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        a = (Button) findViewById(R.id.carilist);

        mListView.setVisibility(View.GONE);

        //mensetting toolbar yang akan muncul di atas dengan nama list mahasiswa
        Toolbar mToolbar = (Toolbar) findViewById(R.id.listnama);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("List Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListView = new ItemListView(activity);
                itemListView.execute();

//                fragment.beginTask();
            }
        });

        if (savedInstanceState == null){
            fragment = new ListNamaFragment();
            getSupportFragmentManager().beginTransaction().add(fragment,"task").commit();
        }else{ //activity created for subscquent time
            fragment = (ListNamaFragment) getSupportFragmentManager().findFragmentByTag("task");
        }

        if (fragment != null){
            if (fragment.itemListView != null && fragment.itemListView.getStatus() == AsyncTask.Status.RUNNING){
               // progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    class ItemListView  extends AsyncTask<Void, String, Void> {

        private Activity activity;

        public ItemListView(Activity activity){
            this.activity = activity;
        }

        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListNamaMahasiswa.this);

        public void onAttach(Activity activity){
            this.activity = activity;
        }

        public void onDetach(){
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter(); //casting suggestion

            //for progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Please wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            //this will handle cacle asynctack when click cancle button
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    itemListView.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();

//            if (ListNamaMahasiswa.this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
//
//                ListNamaMahasiswa.this.setRequestedOrientation(Configuration.ORIENTATION_PORTRAIT);
//            } else {
//                ListNamaMahasiswa.this.setRequestedOrientation(Configuration.ORIENTATION_LANDSCAPE);
//            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String item : mUsers){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    itemListView.cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int) ((counter/(float)mUsers.length)*100);
            mProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            mProgressDialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //hide progreebar
            mProgressBar.setVisibility(View.GONE);

            //remove progress dialog
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);

           // ListNamaMahasiswa.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }
}
