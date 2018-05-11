package id.co.olaga.gosales.Activity;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.App.TabPagerAdapter;
import id.co.olaga.gosales.R;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;

import static id.co.olaga.gosales.App.AppController.TAG;


public class AddCustomer extends AppCompatActivity {

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private globalVariable gv;
    private DatabaseHelper db;
    private static final String TAG = AddCustomer.class.getSimpleName();
    //volley
    int success;
    String tag_json_obj = "json_obj_req";

    // session manager
    SessionManager sessionManager;
    public static String nama;

    //perihal lokasi
    public static String latitude = "";
    public static String longitude = "";
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Customer");
        gv= new globalVariable();
        db = new DatabaseHelper(this);
        sessionManager = new SessionManager(AddCustomer.this.getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        nama = user.get(SessionManager.kunci_user);
        CekGPS();



        setViewPager();
    }

    private void setViewPager() {

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addcustomer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;

            case R.id.action_save:
                sendData();
                return true;





        }
        return super.onOptionsItemSelected(item);
    }

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendData(){

        final String nameHistory="Add "+ gv.getCust_company();

        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(AddCustomer.this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ADD_CUSTOMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(AppVar.TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                db.addcustomerpending(nameHistory,AppVar.GROUP_JOIN,AppVar.NAME_SYNCED_WITH_SERVER,getTanggal(),getJam(),
                                        gv.getCust_company(),gv.getCust_alamat(),gv.getCust_kota(),
                                        gv.getCust_telepon(),gv.getCust_fax(),gv.getCust_hp(),
                                        gv.getCust_ktp(),gv.getCust_nama_ktp(),gv.getCust_ktp_alamat(),gv.getCust_kota_ktp(),
                                        gv.getCust_npwp(),gv.getCust_nama_npwp(),gv.getCust_alamat_npwp(),
                                        gv.getCust_nppkp(),gv.getCust_payment(),gv.getCust_limit(),
                                        gv.getCust_top(),gv.getCust_tipe(),getTanggal(),nama,longitude,
                                        latitude,gv.getCust_foto(),gv.getCust_foto_ktp(),gv.getCust_foto_signature());

                                Toast.makeText(AddCustomer.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            } else {

                                db.addcustomerpending(nameHistory,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,getTanggal(),getJam(),
                                        gv.getCust_company(),gv.getCust_alamat(),gv.getCust_kota(),
                                        gv.getCust_telepon(),gv.getCust_fax(),gv.getCust_hp(),
                                        gv.getCust_ktp(),gv.getCust_nama_ktp(),gv.getCust_ktp_alamat(),gv.getCust_kota_ktp(),
                                        gv.getCust_npwp(),gv.getCust_nama_npwp(),gv.getCust_alamat_npwp(),
                                        gv.getCust_nppkp(),gv.getCust_payment(),gv.getCust_limit(),
                                        gv.getCust_top(),gv.getCust_tipe(),getTanggal(),nama,longitude,
                                        latitude,gv.getCust_foto(),gv.getCust_foto_ktp(),gv.getCust_foto_signature());

                                Toast.makeText(AddCustomer.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.d(TAG, "Error: " + volleyError.getMessage());

                        // Hiding the progress dialog after all task complete.
                        db.addcustomerpending(nameHistory,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,getTanggal(),getJam(),
                                gv.getCust_company(),gv.getCust_alamat(),gv.getCust_kota(),
                                gv.getCust_telepon(),gv.getCust_fax(),gv.getCust_hp(),
                                gv.getCust_ktp(),gv.getCust_nama_ktp(),gv.getCust_ktp_alamat(),gv.getCust_kota_ktp(),
                                gv.getCust_npwp(),gv.getCust_nama_npwp(),gv.getCust_alamat_npwp(),
                                gv.getCust_nppkp(),gv.getCust_payment(),gv.getCust_limit(),
                                gv.getCust_top(),gv.getCust_tipe(),getTanggal(),nama,longitude,
                                latitude,gv.getCust_foto(),gv.getCust_foto_ktp(),gv.getCust_foto_signature());

                        loading.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AddCustomer.this, "Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put(AppVar.NAMA_TOKO, gv.getCust_company());
                params.put(AppVar.TELEPON_TOKO, gv.getCust_telepon());
                params.put(AppVar.HP_TOKO, gv.getCust_hp());
                params.put(AppVar.FAX_TOKO, gv.getCust_fax());
                params.put(AppVar.ALAMAT_TOKO, gv.getCust_alamat());
                params.put(AppVar.KOTA_TOKO, gv.getCust_kota());
                params.put(AppVar.KTP_CUSTOMER, gv.getCust_ktp());
                params.put(AppVar.NAMA_CUSTOMER, gv.getCust_nama_ktp());
                params.put(AppVar.ALAMAT_CUSTOMER, gv.getCust_ktp_alamat());
                params.put(AppVar.KOTA_CUSTOMER, gv.getCust_kota_ktp());
                params.put(AppVar.NPWP_CUSTOMER, gv.getCust_npwp());
                params.put(AppVar.ALAMAT_NPWP_CUSTOMER, gv.getCust_alamat_npwp());
                params.put(AppVar.NAMA_NPWP_CUSTOMER, gv.getCust_nama_npwp());
                params.put(AppVar.NPPKP_CUSTOMER, gv.getCust_nppkp());
                params.put(AppVar.PAYMENT_TOKO, gv.getCust_payment());
                params.put(AppVar.TOP_TOKO, gv.getCust_top());
                params.put(AppVar.LIMIT_TOKO, gv.getCust_limit());
                params.put(AppVar.TIPE_CUSTOMER, gv.getCust_tipe());
                params.put(AppVar.FOTO_TOKO, getStringImage(gv.getCust_foto()));
                params.put(AppVar.FOTO_KTP_TOKO, getStringImage(gv.getCust_foto_ktp()));
                params.put(AppVar.FOTO_SIGNATURE_TOKO, getStringImage(gv.getCust_foto_signature()));

                params.put(AppVar.USER, nama);
                params.put(AppVar.LONGITUDE, longitude);
                params.put(AppVar.LATITUDE, latitude);
                params.put(AppVar.TANGGAL, getTanggal());


                Log.e(TAG, "" + params);
                return params;
            }

        };

        AppController.getInstance(this).addToRequestQueue(stringRequest,tag_json_obj);


    }

    public String getStringImage(byte[] bmp) {
        String encodedImage = "";
        
        if(bmp!=null){
            encodedImage = Base64.encodeToString(bmp, Base64.DEFAULT);
           
        }
        return encodedImage;
    }

    public void CekGPS() {
        try {

            /* Pengecekan GPS hidup / tidak */
            LocationManager manager = (LocationManager) AddCustomer.this.getSystemService(LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddCustomer.this);
                builder.setTitle("Info");
                builder.setMessage("Anda akan mengaktifkan GPS ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int witch) {

                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(AddCustomer.this.getBaseContext());

        // menampilkan status google play service
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, AddCustomer.this, requestCode);
            dialog.show();
        } else {
            // Google Play Service Tersedia
            try {
                LocationManager locationManager = (LocationManager)
                        getSystemService(LOCATION_SERVICE);

                // Membuat Kriteria Untuk Penumpangan Provider
                Criteria criteria = new Criteria();

                // Mencari Provider Terbaik
                String provider = locationManager.getBestProvider(criteria, true);

                // Mendapatkan Lokasi Terakhir
                if (ActivityCompat.checkSelfPermission(AddCustomer.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddCustomer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(provider);

                if(location != null){
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider,1000,0, (LocationListener) this);
            } catch (Exception e){
            }
        }
    }


    public void onLocationChanged(Location lokasi){
        //TODO Auto-generated method stub
        latitude = lokasi.getLatitude()+"";
        longitude = lokasi.getLongitude()+"";
    }

    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public globalVariable getGv(){
        return gv;
    }


    private String getJam() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }




}