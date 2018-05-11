package id.co.olaga.gosales.recycle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;

public class ViewDataProduk3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> NamaList;

    private DatabaseHelper MyDatabase;
    private String stockCategory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_data_produk3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Produk");


        Bundle dataExtra = getIntent().getExtras();
        stockCategory2 = dataExtra.getString("kirimtype");

        NamaList = new ArrayList<>();
        MyDatabase = new DatabaseHelper(getApplicationContext());
        recyclerView = findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter3(NamaList);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }




    protected void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT DISTINCT "+ AppVar.TAG_STOCK_NAME + " FROM produk WHERE "+
                AppVar.TAG_STOCK_CATEGORY2 +" = '"+ stockCategory2 +"'",null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            NamaList.add(cursor.getString(0));//Menambil Data Dari Kolom 1 (Nama)

        }
    }
}
