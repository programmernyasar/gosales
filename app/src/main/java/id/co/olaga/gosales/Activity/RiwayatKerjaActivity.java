package id.co.olaga.gosales.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.ListAdapter.Nama;
import id.co.olaga.gosales.ListAdapter.NamaAdapter;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.Tools.NetworkStateChecker;

import static id.co.olaga.gosales.App.AppVar.DATA_SAVED_BROADCAST;

public class RiwayatKerjaActivity extends AppCompatActivity {

    public static String nama;
    SessionManager sessionManager;
    private List<Nama> names;
    private DatabaseHelper db;
    private NamaAdapter nameAdapter;
    private ListView riwayatListView;
    private BroadcastReceiver broadcastReceiver;
    public Intent receiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riwayat_kerja);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Riwayat Pekerjaan");

        // Inflate the layout for this fragment
        riwayatListView = (ListView) findViewById(R.id.listviewriwayat);
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        db = new DatabaseHelper(this);
        names = new ArrayList<>();


        //calling the method to load all the stored names

        loadNames();
        refreshList();


        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadNames();
                refreshList();

            }
        };

        //registering the broadcast receiver to update sync status
       registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));



    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadNames() {
        names.clear();
        Cursor cursor = db.getAllHistory();
        if (cursor.moveToFirst()) {
            do {
                Nama name = new Nama(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TANGGAL)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JAM))
                );
                names.add(name);
            } while (cursor.moveToNext());
        }

        nameAdapter = new NamaAdapter(this, R.layout.name, names);
        riwayatListView.setAdapter(nameAdapter);
    }


    public void refreshList() {
        nameAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

                unregisterReceiver(broadcastReceiver);

        super.onDestroy();

    }


}
