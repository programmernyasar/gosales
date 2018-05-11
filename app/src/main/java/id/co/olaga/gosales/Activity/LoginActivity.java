package id.co.olaga.gosales.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.R;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextUser;
    private EditText editTextPassword;
    private Context context;
    private ProgressDialog pDialog;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        //mengambil kontek untuk class ini
        context = LoginActivity.this;

        //deklarasi widget yang akan digunakan
        pDialog = new ProgressDialog(LoginActivity.this);
        editTextUser = (EditText) findViewById(R.id.txt_username);
        editTextPassword = (EditText) findViewById(R.id.txt_password);
        sessionManager = new SessionManager(getApplicationContext());

        //ini buat session kalau telah login
        if(sessionManager.checkLogin()){

            //intent ke menu activity

            Intent i = new Intent(context, MenuActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            context.startActivity(i);
        }


    }


    public void loginmasuk(View view) {
        login();
    }

    private void login() {
        //Getting values from edit texts
        final String user = editTextUser.getText().toString().trim();
        final String password = md5(editTextPassword.getText().toString().trim());
        pDialog.setMessage("Login Process...");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            hideDialog();
                            sessionManager.createSessionLogin(user,password);
                            gotoCourseActivity();


                        }
                        else if(response.contains(AppVar.LOCKED)){
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Username anda dikunci", Toast.LENGTH_LONG).show();

                        }
                        else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "username dan password salah", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(LoginActivity.this, "sambungan ke server bermasalah", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_USER, user);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
