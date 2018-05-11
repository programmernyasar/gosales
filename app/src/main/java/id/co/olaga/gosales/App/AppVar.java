package id.co.olaga.gosales.App;

/**
 * Created by tdev on 29/11/17.
 */

public class AppVar {

    // punya login dan menu
    public static final String URL = "https://67d206019ad8.sn.mynetname.net/web_services/";

    public static final String LOGIN_URL = "https://67d206019ad8.sn.mynetname.net/web_services/login.php";
    public static final String KEY_USER = "user";
    public static final String KEY_PASSWORD = "password";
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOCKED = "locked";


    // Punya Absen

    public static final String ABSEN_URL = "https://67d206019ad8.sn.mynetname.net/web_services/absen.php";
    public static final String ABSEN_UPDATE_URL = "https://67d206019ad8.sn.mynetname.net/web_services/absenupdate.php";
    public static final String TRACK_URL = "https://67d206019ad8.sn.mynetname.net/web_services/insert-location.php";

    // Customer Dan Penjualan

    public static final String CUSTOMER= "https://67d206019ad8.sn.mynetname.net/web_services/customer.php";
    public static final String ADD_CUSTOMER = "https://67d206019ad8.sn.mynetname.net/web_services/add-customer.php";
    public static final String ADD_ORDER = "https://67d206019ad8.sn.mynetname.net/web_services/add-order.php";
    public static final String ADD_PENJUALAN = "https://67d206019ad8.sn.mynetname.net/web_services/add-penjualan.php";
    public static final String ADD_KOTA = "https://67d206019ad8.sn.mynetname.net/web_services/kota.php";
    public static final String ADD_TIPE_CUSTOMER = "https://67d206019ad8.sn.mynetname.net/web_services/type-customer.php";

    // Produk

    public static final String ADD_PRODUK = "https://67d206019ad8.sn.mynetname.net/web_services/produk.php";

    // pengiriman post
    public static final String ID = "id";
    public static final String IMAGE = "image";
    public static final String USER= "user";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String TANGGAL = "tanggal";
    public static final String JAM = "jam";

    // sinkronasi data sukses atau tidak

    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final String DATA_SAVED_BROADCAST = "com.tdev.datasaved";

    // group join sqlite

    public static final int GROUP_JOIN = 1;


    // pengiriman post add customer

    public static final String NAMA_TOKO="nama_toko";
    public static final String TELEPON_TOKO="telepon_toko";
    public static final String HP_TOKO="hp_toko";
    public static final String FAX_TOKO="fax_toko";
    public static final String ALAMAT_TOKO="alamat_toko";
    public static final String KOTA_TOKO="kota_toko";






    public static final String KTP_CUSTOMER="ktp_customer";
    public static final String NAMA_CUSTOMER="nama_customer";
    public static final String ALAMAT_CUSTOMER="alamat_customer";
    public static final String KOTA_CUSTOMER="kota_customer";
    public static final String TIPE_CUSTOMER="tipe_customer";

    public static final String NPWP_CUSTOMER="npwp_customer";
    public static final String ALAMAT_NPWP_CUSTOMER="alamat_npwp_customer";
    public static final String NAMA_NPWP_CUSTOMER="nama_npwp_customer";
    public static final String NPPKP_CUSTOMER="nppkp_customer";

    public static final String PAYMENT_TOKO="payment_toko";
    public static final String LIMIT_TOKO="limit_toko";
    public static final String TOP_TOKO="top_toko";


    public static final String FOTO_TOKO="foto_toko";
    public static final String FOTO_KTP_TOKO="foto_ktp_toko";
    public static final String FOTO_SIGNATURE_TOKO="foto_signature_toko";


    // Perbaharuan Data Dari MYSQL


    // Kota

    public static final String TAG_CITY_NAME     = "city_name";

    // Tipe Customer

    public static final String TAG_TYPE_NAME     = "tipe_customer";

    // Produk

    public static final String TAG_STOCK_CODE           = "stock_code";
    public static final String TAG_STOCK_NAME           = "stock_name";
    public static final String TAG_STOCK_BRAND          = "stock_brand";
    public static final String TAG_STOCK_CATEGORY1      = "stock_category1";
    public static final String TAG_STOCK_CATEGORY2      = "stock_category2";
    public static final String TAG_STOCK_TYPE          = "stock_type";
    public static final String TAG_STOCK_UOM1          = "stock_UOM1";
    public static final String TAG_STOCK_UOM2          = "stock_UOM2";
    public static final String TAG_STOCK_UOM3           = "stock_UOM3";
    public static final String TAG_STOCK_UOMPRIMARY     = "stock_UOMPRIMARY";
    public static final String TAG_STOCK_UOM_KONVERSI1     = "stock_UOM_KONVERSI1";
    public static final String TAG_STOCK_UOM_KONVERSI2     = "stock_UOM_KONVERSI2";
    public static final String TAG_STOCK_UOM_KONVERSI3     = "stock_UOM_KONVERSI3";





}
