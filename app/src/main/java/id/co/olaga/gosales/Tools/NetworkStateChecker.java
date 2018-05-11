package id.co.olaga.gosales.Tools;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.Activity.AddCustomer;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.App.AppController;

import static id.co.olaga.gosales.App.AppVar.NAME_SYNCED_WITH_SERVER;


import id.co.olaga.gosales.App.globalVariable;

/**
 * Created by Tdev on 1/27/2017.
 */

public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHelper db;
    int bitmap_size = 60;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        db = new DatabaseHelper(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
                Cursor cursortrack = db.unsyncedTrack();
                Cursor absenmasuk = db.unsyncedAbsenMasuk();
                Cursor absenpulang = db.unsyncedAbsenPulang();
                Cursor addcustomer = db.unsyncedAddCustomer();

                if (cursortrack.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveTrack(
                                cursortrack.getInt(cursortrack.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                cursortrack.getString(cursortrack.getColumnIndex(DatabaseHelper.USER_TRACK)),
                                cursortrack.getString(cursortrack.getColumnIndex(DatabaseHelper.COLUMN_TANGGAL)),
                                cursortrack.getString(cursortrack.getColumnIndex(DatabaseHelper.COLUMN_JAM)),
                                cursortrack.getString(cursortrack.getColumnIndex(DatabaseHelper.LATITUDE_TRACK)),
                                cursortrack.getString(cursortrack.getColumnIndex(DatabaseHelper.LONGITUDE_TRACK))
                        );

                    } while (cursortrack.moveToNext());
                }

                if (absenmasuk.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveAbsenMasuk(
                                absenmasuk.getInt(absenmasuk.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                absenmasuk.getString(absenmasuk.getColumnIndex(DatabaseHelper.USER_ABSEN)),
                                absenmasuk.getString(absenmasuk.getColumnIndex(DatabaseHelper.COLUMN_TANGGAL)),
                                absenmasuk.getString(absenmasuk.getColumnIndex(DatabaseHelper.COLUMN_JAM)),
                                absenmasuk.getBlob(absenmasuk.getColumnIndex(DatabaseHelper.FOTO)),
                                absenmasuk.getString(absenmasuk.getColumnIndex(DatabaseHelper.LATITUDE)),
                                absenmasuk.getString(absenmasuk.getColumnIndex(DatabaseHelper.LONGITUDE))
                        );

                    } while (absenmasuk.moveToNext());
                }

                if (absenpulang.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveAbsenPulang(
                                absenpulang.getInt(absenpulang.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                absenpulang.getString(absenpulang.getColumnIndex(DatabaseHelper.USER_ABSEN)),
                                absenpulang.getString(absenpulang.getColumnIndex(DatabaseHelper.COLUMN_TANGGAL)),
                                absenpulang.getString(absenpulang.getColumnIndex(DatabaseHelper.COLUMN_JAM)),
                                absenpulang.getBlob(absenpulang.getColumnIndex(DatabaseHelper.FOTO)),
                                absenpulang.getString(absenpulang.getColumnIndex(DatabaseHelper.LATITUDE)),
                                absenpulang.getString(absenpulang.getColumnIndex(DatabaseHelper.LONGITUDE))
                        );

                    } while (absenpulang.moveToNext());
                }

                if (addcustomer.moveToFirst()) {
                    do {
                        //calling the method to save the unsynced name to MySQL
                        saveAddCustomer(
                                addcustomer.getInt(addcustomer.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_COMPANY)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_ADDRESS)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_CITY)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_PHONE)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_FAX)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_MOBILE)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_ID)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_NAME)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_ID_ADDRESS)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_ID_CITY)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_NPWP)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_NPWP_NAME)),
                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_NPWP_ADDRESS)),
                                        addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_NPPKP)),
                                        addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_PAYMENT)),
                                        addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_LIMIT)),
                                        addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_TOP)),
                                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_TIPE)),
                                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.COLUMN_TANGGAL)),
                                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_LONGITUDE)),
                                                addcustomer.getString(addcustomer.getColumnIndex(DatabaseHelper.CUST_LATITUDE)),
                                                addcustomer.getBlob(addcustomer.getColumnIndex(DatabaseHelper.CUST_PIC)),
                                                addcustomer.getBlob(addcustomer.getColumnIndex(DatabaseHelper.CUST_ID_PIC)),
                                                addcustomer.getBlob(addcustomer.getColumnIndex(DatabaseHelper.CUST_SIGNATURE))
                        );

                    } while (addcustomer.moveToNext());
                }



            }
        }

    }


    private void saveTrack(final int id, final String user, final String tanggal
            , final String jam, final String latitude
            , final String longitude){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.TRACK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                                db.updateTrackStatus(id, NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(AppVar.DATA_SAVED_BROADCAST));



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.USER, user);
                params.put(AppVar.TANGGAL, tanggal);
                params.put(AppVar.JAM, jam);
                params.put(AppVar.LATITUDE, latitude );
                params.put(AppVar.LONGITUDE, longitude);

                return params;
            }
        };

        AppController.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void saveAbsenPulang(final int id, final String user, final String tanggal
                            , final String jam, final byte[] foto, final String latitude
                            , final String longitude ) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ABSEN_UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                                //updating the status in sqlite
                                db.updateAbsenPulang(id, NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(AppVar.DATA_SAVED_BROADCAST));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.USER, user);
                params.put(AppVar.TANGGAL, tanggal);
                params.put(AppVar.JAM, jam);
                params.put(AppVar.IMAGE, getStringImage(foto));
                params.put(AppVar.LATITUDE, latitude );
                params.put(AppVar.LONGITUDE, longitude);

                return params;
            }
        };

        AppController.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void saveAbsenMasuk(final int id, final String user, final String tanggal
            , final String jam, final byte[] foto, final String latitude
            , final String longitude ) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ABSEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                                db.updateAbsenMasuk(id, NAME_SYNCED_WITH_SERVER);

                                //sending the broadcast to refresh the list
                                context.sendBroadcast(new Intent(AppVar.DATA_SAVED_BROADCAST));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.USER, user);
                params.put(AppVar.TANGGAL, tanggal);
                params.put(AppVar.JAM, jam);
                params.put(AppVar.IMAGE, getStringImage(foto));
                params.put(AppVar.LATITUDE, latitude );
                params.put(AppVar.LONGITUDE, longitude);

                return params;
            }
        };

        AppController.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void saveAddCustomer(final int id,final String cust_company,final String
            cust_address,final String cust_city,final String cust_phone,final String
            cust_fax,final String cust_mobile,final String cust_id,final String cust_name,final String
            cust_id_address,final String cust_id_city,final String cust_npwp,final String
            cust_npwp_name,final String cust_npwp_address,final String cust_nppkp, final String
            cust_payment,final String cust_limit,final String cust_top,final String cust_tipe_customer, final String
            cust_since,final String cust_sales_id,final String cust_longitude,
                                 final String cust_latitude,final byte[] cust_pic,
                                 final byte[] cust_ktp,final byte[] cust_signature) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.ADD_CUSTOMER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        db.updateAddCustomer(id, NAME_SYNCED_WITH_SERVER);

                        //sending the broadcast to refresh the list
                        context.sendBroadcast(new Intent(AppVar.DATA_SAVED_BROADCAST));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(AppVar.NAMA_TOKO, cust_company);
                params.put(AppVar.TELEPON_TOKO, cust_phone);
                params.put(AppVar.HP_TOKO, cust_mobile);
                params.put(AppVar.FAX_TOKO, cust_fax);
                params.put(AppVar.ALAMAT_TOKO, cust_address);
                params.put(AppVar.KOTA_TOKO, cust_city);
                params.put(AppVar.KTP_CUSTOMER, cust_id);
                params.put(AppVar.NAMA_CUSTOMER, cust_name);
                params.put(AppVar.ALAMAT_CUSTOMER, cust_id_address);
                params.put(AppVar.KOTA_CUSTOMER, cust_id_city);
                params.put(AppVar.NPWP_CUSTOMER, cust_npwp);
                params.put(AppVar.ALAMAT_NPWP_CUSTOMER, cust_npwp_address);
                params.put(AppVar.NAMA_NPWP_CUSTOMER,cust_npwp_name);
                params.put(AppVar.NPPKP_CUSTOMER, cust_nppkp);
                params.put(AppVar.PAYMENT_TOKO, cust_payment);
                params.put(AppVar.TOP_TOKO, cust_top);
                params.put(AppVar.LIMIT_TOKO, cust_limit);
                params.put(AppVar.TIPE_CUSTOMER, cust_tipe_customer);
                params.put(AppVar.FOTO_TOKO,getStringImage(cust_pic));
                params.put(AppVar.FOTO_KTP_TOKO, getStringImage(cust_ktp));
                params.put(AppVar.FOTO_SIGNATURE_TOKO, getStringImage(cust_signature));

                params.put(AppVar.USER, cust_sales_id);
                params.put(AppVar.LONGITUDE, cust_longitude);
                params.put(AppVar.LATITUDE, cust_latitude);
                params.put(AppVar.TANGGAL, cust_since);


                return params;
            }
        };

        AppController.getInstance(context).addToRequestQueue(stringRequest);
    }


    public String getStringImage(byte[] bmp) {

        String encodedImage = Base64.encodeToString(bmp, Base64.DEFAULT);
        return encodedImage;
    }

}
