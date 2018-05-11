package id.co.olaga.gosales.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.ListAdapter.NamaAdapter;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.Tools.MyJobService;

public class AbsenActivity extends AppCompatActivity {

    //pengolahan gambar
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    ImageView ivCamera;
    private static final int CAMERA_REQUEST_CODE = 7777;

    //Pengolahan Lokasi
    public static String nama;
    private Location location;
    public static String latitude="";
    public static String longitude="";

    //Pendukung
    private static final String TAG = AbsenActivity.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    SessionManager sessionManager;
    int success;

    //Job Service
    JobScheduler jobScheduler;
    private static final int MYJOBID = 1;

    //sqlite
    private DatabaseHelper db;
    public static String namasqlite;
    private NamaAdapter nameAdapter;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Absen Harian");


        sessionManager = new SessionManager(AbsenActivity.this.getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        nama = user.get(SessionManager.kunci_user);

        ivCamera=(ImageView)findViewById(R.id.foto) ;
        jobScheduler =(JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        db = new DatabaseHelper(this);

        CekGPS();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void mulaikerja(View view) {
        uploadMasuk();
        cronJob();
        Intent intent = new Intent(AbsenActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void selesaikerja(View view) {
        uploadPulang();
        jobScheduler.cancelAll();
        Intent intent = new Intent(AbsenActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();

    }

    private void showFileChooser() {
        //intent khusus untuk menangkap foto lewat kamera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (CAMERA_REQUEST_CODE):
                if (resultCode == Activity.RESULT_OK) {
                    // result code sama, save gambar ke bitmap
                    Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                    setToImageView(getResizedBitmap(bitmap, 512));
                }
                break;

        }
    }


    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        ivCamera.setImageBitmap(decoded);
    }

    //fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public void CekGPS() {
        try {

        /* Pengecekan GPS hidup / tidak */
            LocationManager manager = (LocationManager) AbsenActivity.this.getSystemService(LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AbsenActivity.this);
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
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(AbsenActivity.this.getBaseContext());

        // menampilkan status google play service
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, AbsenActivity.this, requestCode);
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
                if (ActivityCompat.checkSelfPermission(AbsenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AbsenActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private void uploadMasuk() {
        latitude = location.getLatitude()+"";
        longitude = location.getLongitude()+"";
        namasqlite="Absen Masuk";

        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(AbsenActivity.this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ABSEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(AppVar.TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(AbsenActivity.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                db.addAbsenMasuk(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_SYNCED_WITH_SERVER,
                                        nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);

                            } else {

                                db.addAbsenMasuk(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,
                                        nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);

                                Toast.makeText(AbsenActivity.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

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

                        // Hiding the progress dialog after all task complete.
                        db.addAbsenMasuk(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,
                                nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);

                        loading.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(AbsenActivity.this, "Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put(AppVar.IMAGE, getStringImage(decoded));
                params.put(AppVar.USER, nama);
                params.put(AppVar.LONGITUDE, longitude);
                params.put(AppVar.LATITUDE, latitude);
                params.put(AppVar.TANGGAL, getTanggal());
                params.put(AppVar.JAM, getJam());

                Log.e(TAG, "" + params);
                return params;
            }

        };

        AppController.getInstance(this).addToRequestQueue(stringRequest,tag_json_obj);


    }

    private void uploadPulang() {
        latitude = location.getLatitude()+"";
        longitude = location.getLongitude()+"";

        namasqlite="Absen Pulang";

        //menampilkan progress dialog

        final ProgressDialog loading = ProgressDialog.show(AbsenActivity.this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ABSEN_UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(AppVar.TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(AbsenActivity.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                db.addAbsenPulang(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_SYNCED_WITH_SERVER,
                                        nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);



                            } else {

                                db.addAbsenPulang(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,
                                        nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);

                                Toast.makeText(AbsenActivity.this, jObj.getString(AppVar.TAG_MESSAGE), Toast.LENGTH_LONG).show();

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

                        // Hiding the progress dialog after all task complete.

                        db.addAbsenPulang(namasqlite,AppVar.GROUP_JOIN,AppVar.NAME_NOT_SYNCED_WITH_SERVER,
                                nama,getTanggal(),getJam(),getByteImage(decoded),latitude,longitude);


                        loading.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(AbsenActivity.this, "Jaringan Bermasalah", Toast.LENGTH_LONG).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put(AppVar.IMAGE, getStringImage(decoded));
                params.put(AppVar.USER, nama);
                params.put(AppVar.LONGITUDE, longitude);
                params.put(AppVar.LATITUDE, latitude);
                params.put(AppVar.TANGGAL, getTanggal());
                params.put(AppVar.JAM, getJam());

                Log.e(TAG, "" + params);
                return params;
            }

        };

        AppController.getInstance(this).addToRequestQueue(stringRequest,tag_json_obj);


    }


    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }


    private String getJam() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cronJob(){


        ComponentName jobService =
                new ComponentName(getPackageName(), MyJobService.class.getName());
        JobInfo jobInfo =
                new JobInfo.Builder(MYJOBID, jobService).setPeriodic(1000).build();


        int jobId = jobScheduler.schedule(jobInfo);
        if(jobScheduler.schedule(jobInfo)>0) {

        }

    }


    public void addfoto(View view) {

        showFileChooser();

    }


    public byte[] getByteImage(Bitmap bmp) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }



}
