package id.co.olaga.gosales.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.App.AppVar;

import static id.co.olaga.gosales.App.AppController.TAG;



public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CustomerFragment.OnFragmentInteractionListener,
        ProdukFragment.OnFragmentInteractionListener,
        RuteFragment.OnFragmentInteractionListener,
        StockCanvasFragment.OnFragmentInteractionListener
 {

    SessionManager sessionManager;
    public static String nama;
    public static String pass;
     MaterialSearchView searchView;
     Toolbar toolbar;




     //Fragment Variabel

    Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchViewCode();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header =navigationView.getHeaderView(0);

        // perkara databse dan context ngambil activity nya

        sessionManager = new SessionManager(getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        nama = user.get(SessionManager.kunci_user);
        pass = user.get(SessionManager.kunci_password);

        TextView userProfile = (TextView) header.findViewById(R.id.userprofile);
        userProfile.setText(nama);

        cek_status();

        fragment = new RuteFragment();
        callFragment(fragment);

    }

     private void searchViewCode() {

         searchView = (MaterialSearchView) findViewById(R.id.search_view);
//         searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
         searchView.setEllipsize(true);
         searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 return false;
             }
         });
         searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
             @Override
             public void onSearchViewShown() {
             }

             @Override
             public void onSearchViewClosed() {
             }
         });
     }

     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // buat title di action bar setiap fragment

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_search:
                return true;

            case R.id.refresh_data:
                refreshAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_absen) {

            Intent intent = new Intent(MenuActivity.this, AbsenActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_rute) {

                fragment = new RuteFragment();
                callFragment(fragment);



        } else if (id == R.id.nav_customer) {

                fragment = new CustomerFragment();
                callFragment(fragment);

        }

        else if (id == R.id.nav_produk) {

            fragment = new ProdukFragment();
            callFragment(fragment);


        }else if (id == R.id.nav_stock_canvas) {


                fragment = new StockCanvasFragment();
                callFragment(fragment);


        }else if (id == R.id.nav_riwayat) {


            Intent intent = new Intent(MenuActivity.this, RiwayatKerjaActivity.class);
            startActivity(intent);


        }else if (id == R.id.nav_keluar) {
            sessionManager.logout();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void cek_status() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
//                            Toast.makeText(MenuActivity.this, "Berhasil Masuk", Toast.LENGTH_LONG).show();


                        } else if (response.contains(AppVar.LOCKED)) {
                            Toast.makeText(MenuActivity.this, "Username Dikunci", Toast.LENGTH_LONG).show();
                            sessionManager.logout();

                        } else {
                            Toast.makeText(MenuActivity.this, "username dan password salah", Toast.LENGTH_LONG).show();
                            sessionManager.logout();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuActivity.this, "Sambungan ke server bermasalah", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_USER, nama);
                params.put(AppVar.KEY_PASSWORD, pass);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }


    private void callFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fmain, fragment)
                .commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // pengambilan data dari mysql ke sqlite

     private void callVolleyKota(){

         // membuat request JSON
         JsonArrayRequest jArr = new JsonArrayRequest(AppVar.ADD_KOTA, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 Log.d(TAG, response.toString());

                 // Parsing json
                 for (int i = 0; i < response.length(); i++) {
                     try {
                         JSONObject obj = response.getJSONObject(i);

                         DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                         db.addKota(obj.getString(AppVar.TAG_CITY_NAME));


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }


             }
         }, new Response.ErrorListener() {

             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.d(TAG, "Error: " + error.getMessage());

             }
         });

         // menambah request ke request queue
         AppController.getInstance(this).addToRequestQueue(jArr);
     }

     private void callVolleyTipe(){

         // membuat request JSON
         JsonArrayRequest jArr = new JsonArrayRequest(AppVar.ADD_TIPE_CUSTOMER, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 Log.d(TAG, response.toString());

                 // Parsing json
                 for (int i = 0; i < response.length(); i++) {
                     try {
                         JSONObject obj = response.getJSONObject(i);

                         DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                         db.addTipeCustomer(obj.getString(AppVar.TAG_TYPE_NAME));


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }


             }
         }, new Response.ErrorListener() {

             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.d(TAG, "Error: " + error.getMessage());

             }
         });

         // menambah request ke request queue
         AppController.getInstance(this).addToRequestQueue(jArr);
     }

     private void callVolleyProduk(){

         // membuat request JSON
         JsonArrayRequest jArr = new JsonArrayRequest(AppVar.ADD_PRODUK, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                 Log.d(TAG, response.toString());

                 // Parsing json
                 for (int i = 0; i < response.length(); i++) {
                     try {
                         JSONObject obj = response.getJSONObject(i);

                         DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                         db.addProduk(obj.getString(AppVar.TAG_STOCK_CODE),obj.getString(AppVar.TAG_STOCK_NAME),
                                 obj.getString(AppVar.TAG_STOCK_BRAND), obj.getString(AppVar.TAG_STOCK_CATEGORY1),
                                 obj.getString(AppVar.TAG_STOCK_CATEGORY2), obj.getString(AppVar.TAG_TYPE_NAME),
                                 obj.getString(AppVar.TAG_STOCK_UOM1), obj.getString(AppVar.TAG_STOCK_UOM2),
                                 obj.getString(AppVar.TAG_STOCK_UOM3), obj.getString(AppVar.TAG_STOCK_UOMPRIMARY),
                                 obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI1), obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI2),
                                 obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI3));


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }


             }
         }, new Response.ErrorListener() {

             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.d(TAG, "Error: " + error.getMessage());

             }
         });

         // menambah request ke request queue
         AppController.getInstance(this).addToRequestQueue(jArr);
     }

     private void callVolleyStock(){

         StringRequest eventoReq = new StringRequest(Request.Method.POST,AppVar.ADD_STOCK,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         Log.d(TAG, response.toString());

                         try{
                             JSONArray j= new JSONArray(response);

                             // Parsea json
                             for (int i = 0; i < j.length(); i++) {
                                 try {
                                     JSONObject obj = j.getJSONObject(i);

                                     DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                                     db.addStockCanvaser(obj.getString(AppVar.TAG_STOCK_CODE_CANVAS),obj.getString(AppVar.TAG_STOCK_NAME_CANVAS),obj.getInt(AppVar.TAG_STOCK_QTY),
                                             obj.getString(AppVar.TAG_STOCK_UOM), obj.getInt(AppVar.TAG_STOCK_QTYX),
                                             obj.getString(AppVar.TAG_STOCK_UOMX));


                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }

                             }

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }


                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.d(TAG, "Error: " + error.getMessage());


             }
         }){
             @Override
             protected Map<String, String> getParams() {
                 // Posting parameters to login url
                 Map<String, String> params = new HashMap<String, String>();
                 params.put(AppVar.USER, "JA02");
                 params.put(AppVar.TANGGAL, "2018-03-01");
                 return params;
             }
         };

         // Añade la peticion a la cola
         AppController.getInstance(this).addToRequestQueue(eventoReq);
     }

     private void refreshAll(){

         final ProgressDialog loading = ProgressDialog.show(MenuActivity.this, "Uprading Your Data....", "Please wait...", false, false);

         ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

         if (activeNetwork != null) {
             //if connected to wifi or mobile data plan
             if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                 // Delete All Data Dulu
                 DatabaseHelper db= new DatabaseHelper(getApplicationContext());
                 db.deleteAll();

                 // Di Tambah Lagi Data nya
                 callVolleyKota();
                 callVolleyTipe();
                 callVolleyProduk();
                 callVolleyStock();

                 loading.dismiss();
                 Toast.makeText(MenuActivity.this, "Data Anda Berhasil Di Perbaharui",Toast.LENGTH_LONG).show();

             }
         }
         else
         {
             loading.dismiss();
             Toast.makeText(MenuActivity.this, "Periksa Koneksi Jaringan Anda",Toast.LENGTH_LONG).show();
         }

     }

}