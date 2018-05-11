package id.co.olaga.gosales.Tools;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.ListAdapter.NamaAdapter;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;

import static id.co.olaga.gosales.App.AppVar.NAME_NOT_SYNCED_WITH_SERVER;

//Require API Level 21
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

	private Location location;
	public static String latitude = "";
	public static String longitude = "";
	String tag_json_obj = "json_obj_req";
	public static String nama;
	SessionManager sessionManager;
	private DatabaseHelper db;
	private NamaAdapter nameAdapter;



	public MyJobService() {
	}

	@Override
	public boolean onStartJob(JobParameters params) {
//

		sessionManager = new SessionManager(getApplicationContext());
		final HashMap<String, String> user = sessionManager.getUserDetails();
		nama = user.get(SessionManager.kunci_user);
		db = new DatabaseHelper(this);
		CekGPS();
		track();

		return false;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Toast.makeText(this,
				"MyJobService.onStopJob()",
				Toast.LENGTH_SHORT).show();
		return false;
	}


	public void CekGPS() {

		// Google Play Service Tersedia
		try {
			LocationManager locationManager = (LocationManager)
					getSystemService(LOCATION_SERVICE);

			// Membuat Kriteria Untuk Penumpangan Provider
			Criteria criteria = new Criteria();

			// Mencari Provider Terbaik
			String provider = locationManager.getBestProvider(criteria, true);

			// Mendapatkan Lokasi Terakhir
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

			if (location != null) {
				onLocationChanged(location);
			}
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			locationManager.requestLocationUpdates(provider, 5000, 0, (LocationListener) this);
			} catch (Exception e){
			}
		}

	public void onLocationChanged(Location lokasi){
		//TODO Auto-generated method stub
		latitude = lokasi.getLatitude()+"";
		longitude = lokasi.getLongitude()+"";
	}


	protected void track() {
		final String trackLoading="Track Pending";

		// Creating string request with post method.
		StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.TRACK_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String ServerResponse) {


//						Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						saveToLocalStorage(trackLoading,1,NAME_NOT_SYNCED_WITH_SERVER,nama,getTanggal(),getJam(),latitude,longitude,"");
//					    	Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();

					}
				}) {


			@Override
			protected Map<String, String> getParams() {

				Map<String, String> params = new HashMap<String, String>();
				params.put(AppVar.USER, nama);
				params.put(AppVar.TANGGAL, getTanggal());
				params.put(AppVar.JAM, getJam());
				params.put(AppVar.LONGITUDE, longitude);
				params.put(AppVar.LATITUDE, latitude);

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


	private void saveToLocalStorage(String name,int group, int status,  String user, String tanggal, String jam,
									String latitude, String longitude, String kunjungan )
	{

		db.addTrack(name,group,status,user,tanggal,jam,latitude,longitude,kunjungan);


	}



 }
