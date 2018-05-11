package id.co.olaga.gosales.App;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import id.co.olaga.gosales.Activity.LoginActivity;

/**
 * Created by tdev on 01/12/17.
 */

public class SessionManager {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String kunci_user = "keyuser";
    public static final String kunci_password = "keypassword";
    private static final String is_absen = "isabsen";


    // Add customer

    private  static final String nm_toko = "keynmtoko";
    private static final String telepon_toko = "keytelepon";
    private static final String hp_toko = "keyhp";
    private static final String fax_toko = "keyfax";
    private static final String alamat_toko = "keyalamat";
    private static final String kota_toko = "keykota";

    private static final String ktp_owner = "keyktp";
    private static final String nama_ktp = "keynamaktp";
    private static final String alamat_ktp = "keyalamattoko";
    private static final String kota_ktp = "keykotatoko";

    private static final String npwp_owner= "keynpwp";
    private static final String nama_npwp = "keynamanpwp";
    private static final String alamat_npwp = "keyalamatnpwp";
    private static final String nppkp_owner = "keynppkp";

    private static final String payment_toko = "keypayment";
    private static final String limit_toko = "keylimit";
    private static final String topt_toko ="keytop";
    
    private static final String foto_ktp="keyfotoktp";
    private static final String foto_toko="keyfototoko";
    private static final String ttd_owner="keyttd";


    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSessionLogin(String user, String password){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_user, user);
        editor.putString(kunci_password, password);
        editor.commit();
    }


    public void createSessionAbsen(){
        editor.putBoolean(is_absen, true);
        editor.commit();
    }

    public void outletAdd(String nmtoko, String telepon, String hp, String fax, String alamat,
                          String kota)

    {

        editor.putString(nm_toko, nmtoko);
        editor.putString(telepon_toko, telepon);
        editor.putString(hp_toko, hp);
        editor.putString(fax_toko, fax);
        editor.putString(alamat_toko, alamat);
        editor.putString(kota_toko, kota);
        editor.commit();
    }

    public void ownerAdd(String ktp, String nama, String alamat, String kota)

    {
        editor.putString(ktp_owner, ktp);
        editor.putString(nama_ktp, nama);
        editor.putString(alamat_ktp, alamat);
        editor.putString(kota_ktp, kota);
        editor.commit();
    }


    public void npwpAdd(String npwp, String alamat, String nama, String nppkp)

    {
        editor.putString(npwp_owner, npwp);
        editor.putString(nama_npwp, nama);
        editor.putString(alamat_npwp, alamat);
        editor.putString(nppkp_owner, nppkp);
        editor.commit();
    }

    public void paymentAdd(String payment, float limit, int top)

    {
        editor.putString(payment_toko, payment);
        editor.putFloat(limit_toko, limit );
        editor.putInt(topt_toko, top);
        editor.commit();
    }

    public void fotoAdd(String fototoko, String fotoktp, String ttd)

    {
        editor.putString(foto_toko, fototoko);
        editor.putString(kunci_password, fotoktp);
        editor.putString(kunci_password, ttd);
        editor.commit();
    }




    public boolean checkLogin(){
        return pref.getBoolean(is_login, false);
    }


    public boolean checkAbsen(){
        return pref.getBoolean(is_absen, false);
    }

    public boolean is_login() {
        return pref.getBoolean(is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_user, pref.getString(kunci_user, null));
        user.put(kunci_password, pref.getString(kunci_password, null));
        return user;
    }



}
