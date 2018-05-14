package id.co.olaga.gosales.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "olaga";

    // Table Permanen
    private static final String TABLE_TRACK = "track";
    private static final String TABLE_ABSEN_MASUK = "absen_masuk";
    private static final String TABLE_ABSEN_PULANG = "absen_pulang";
    private static final String TABLE_CUSTOMER = "customer";
    private static final String TABLE_TIPE_CUSTOMER = "tipe_customer";
    private static final String TABLE_PRODUK = "produk";
    private static final String TABLE_KOTA = "kota";
    private static final String TABLE_UOM = "uom";


    // Table Sehari Hilang
    private static final String TABLE_STOCK_CANVAS = "stock";
    private static final String TABLE_RUTE = "rute";

    //Table 3 Hari Hilang
    private static final String TABLE_PENJUALAN = "penjualan";
    private static final String TABLE_ORDER = "order";
    private static final String TABLE_ADD_CUSTOMER = "customer_baru";


    // Coloumn Table Track
    public static final String USER_TRACK = "user_track";
    public static final String LATITUDE_TRACK = "latitude";
    public static final String LONGITUDE_TRACK = "longitude";
    public static final String KUNJUNGAN_TRACK = "kunjungan";

    // Coloum Table Absen

    public static final String USER_ABSEN = "user_absen";
    public static final String FOTO= "foto";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    // Coloumn Table Add Customer
    public static final String CUST_CODE = "cust_code";
    public static final String CUST_COMPANY = "cust_company";
    public static final String CUST_ADDRESS = "cust_address";
    public static final String CUST_CITY= "cust_city";
    public static final String CUST_PHONE = "cust_phone";
    public static final String CUST_FAX = "cust_fax";
    public static final String CUST_MOBILE = "cust_mobile";
    public static final String CUST_ID = "cust_id";
    public static final String CUST_ID_ADDRESS = "cust_id_address";
    public static final String CUST_NAME = "cust_name";
    public static final String CUST_ID_CITY = "cust_id_city";
    public static final String CUST_NPWP = "cust_npwp";
    public static final String CUST_NPWP_ADDRESS = "cust_npwp_address";
    public static final String CUST_NPWP_NAME = "cust_npwp_name";
    public static final String CUST_NPPKP = "cust_nppkp";
    public static final String CUST_PAYMENT = "cust_payment";
    public static final String CUST_LIMIT = "cust_limit";
    public static final String CUST_TOP = "cust_top";
    public static final String CUST_TIPE = "cust_tipe";
    public static final String CUST_SINCE = "cust_since";
    public static final String CUST_SALES_ID = "cust_sales_id";
    public static final String CUST_LATITUDE = "cust_latitude";
    public static final String CUST_LONGITUDE = "cust_longitude";
    public static final String CUST_PIC = "cust_pic";
    public static final String CUST_ID_PIC = "cust_id_pic";
    public static final String CUST_SIGNATURE = "cust_signature";

    // Coloumn Table  Produk
    public static final String STOCK_CODE = "stock_code";
    public static final String STOCK_NAME = "stock_name";
    public static final String STOCK_BRAND= "stock_brand";
    public static final String STOCK_CATEGORY1 = "stock_category1";
    public static final String STOCK_CATEGORY2= "stock_category2";
    public static final String STOCK_TYPE = "stock_type";
    public static final String STOCK_UOM1 = "stock_uom1";
    public static final String STOCK_UOM2 = "stock_uom2";
    public static final String STOCK_UOM3 = "stock_uom3";
    public static final String STOCK_UOMPRIMARY = "stock_uomprimary";
    public static final String STOCK_UOM_KONVERSI1 = "stock_uom_konversi1";
    public static final String STOCK_UOM_KONVERSI2 = "stock_uom_konversi2";
    public static final String STOCK_UOM_KONVERSI3 = "stock_uom_konversi3";

    // Coloumn table stock_canvaser

    public static final String STOCK_CODE_CANVAS = "stock_code";
    public static final String STOCK_QTY = "stock_qty";
    public static final String STOCK_UOM = "stock_uom";
    public static final String STOCK_QTYX = "stock_qtyx";
    public static final String STOCK_UOMX = "stock_uomx";


    // Coloumn Table Type Customer
    public static final String NAMA_TIPE = "nama_tipe_customer";


    // Coloumn Table kota
    public static final String NAMA_KOTA = "nama_kota";


    // Coloumn Table type uom
    public static final String NAMA_UOM = "nama_tipe_uom";





    // Coloumn All
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GROUP = "grub";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TANGGAL = "tanggal";
    public static final String COLUMN_JAM = "jam";


    // Table Create Statements
    // Todo table create statement
    private static final String TRACK = "CREATE TABLE "
            + TABLE_TRACK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
            + " VARCHAR, " +  COLUMN_GROUP + " INTEGER, " + COLUMN_STATUS + " TINYINT, " + USER_TRACK
            + " VARCHAR, " + COLUMN_TANGGAL+ " DATE, " + COLUMN_JAM + " TIME, "
            + LATITUDE_TRACK + " VARCHAR, " + LONGITUDE_TRACK
            + " VARCHAR, "+ KUNJUNGAN_TRACK
            + " VARCHAR);";

    private static final String ABSEN_MASUK = "CREATE TABLE "
            + TABLE_ABSEN_MASUK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
            + " VARCHAR, " + COLUMN_GROUP + " INTEGER, " + COLUMN_STATUS + " TINYINT, " + USER_ABSEN
            + " VARCHAR, " + COLUMN_TANGGAL + " DATE, " + COLUMN_JAM + " TIME, " + FOTO + " BLOB, " + LATITUDE + " VARCHAR, "
            + LONGITUDE + " VARCHAR);";



    private static final String ABSEN_PULANG = "CREATE TABLE "
            + TABLE_ABSEN_PULANG + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
            + " VARCHAR, " + COLUMN_GROUP + " INTEGER, " + COLUMN_STATUS + " TINYINT, " + USER_ABSEN
            + " VARCHAR, " + COLUMN_TANGGAL + " DATE, " + COLUMN_JAM + " TIME, " + FOTO + " BLOB, " + LATITUDE + " VARCHAR, "
            + LONGITUDE + " VARCHAR);";

    private static final String ADD_CUSTOMER = "CREATE TABLE "
            + TABLE_ADD_CUSTOMER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
            + " VARCHAR, " + COLUMN_GROUP + " INTEGER, " + COLUMN_STATUS + " TINYINT, " +  COLUMN_TANGGAL + " DATE, " + COLUMN_JAM + " TIME, " + CUST_COMPANY
            + " VARCHAR, "+ CUST_NAME + " VARCHAR, "+ CUST_ADDRESS + " VARCHAR, "+ CUST_CITY
            + " VARCHAR, "+  CUST_PHONE + " VARCHAR, "+ CUST_FAX + " VARCHAR, "+ CUST_MOBILE
            + " VARCHAR, "+ CUST_ID + " VARCHAR, "+CUST_ID_ADDRESS + " VARCHAR, "+ CUST_ID_CITY+  " VARCHAR, "+ CUST_NPWP
            + " VARCHAR, "+ CUST_NPWP_NAME + " VARCHAR, "+ CUST_NPWP_ADDRESS + " VARCHAR, "+ CUST_NPPKP
            + " VARCHAR, "+ CUST_PAYMENT + " VARCHAR, "+ CUST_LIMIT+ " VARCHAR, "+ CUST_TOP
            + " VARCHAR, "+ CUST_TIPE + " VARCHAR, "+ CUST_SINCE + " DATE, "+ CUST_SALES_ID
            + " VARCHAR, "+ CUST_LATITUDE + " VARCHAR, "+ CUST_LONGITUDE + " VARCHAR, "+ CUST_PIC
            + " BLOB, "+ CUST_ID_PIC + " BLOB, "+ CUST_SIGNATURE+ " BLOB);";

    private static final String CUSTOMER = "CREATE TABLE "
            + TABLE_CUSTOMER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUST_CODE
            + " VARCHAR, " + COLUMN_GROUP +" INTEGER, "+ CUST_COMPANY
            + " VARCHAR, "+ CUST_NAME + " VARCHAR, "+ CUST_ADDRESS + " VARCHAR, "+ CUST_CITY
            + " VARCHAR, "+  CUST_PHONE + " VARCHAR, "+ CUST_FAX + " VARCHAR, "+ CUST_MOBILE
            + " VARCHAR, "+ CUST_ID_ADDRESS + " VARCHAR, "+ CUST_ID_CITY+  " VARCHAR, "+ CUST_NPWP
            + " VARCHAR, "+ CUST_NPWP_NAME + " VARCHAR, "+ CUST_NPWP_ADDRESS + " VARCHAR, "+ CUST_NPPKP
            + " VARCHAR, "+ CUST_PAYMENT + " VARCHAR, "+ CUST_LIMIT+ " VARCHAR, "+ CUST_TOP
            + " VARCHAR, "+ CUST_TIPE + " VARCHAR, "+ CUST_SINCE + " DATE, "+ CUST_SALES_ID
            + " VARCHAR, "+ CUST_LATITUDE + " VARCHAR, "+ CUST_LONGITUDE + " VARCHAR, "+ CUST_PIC
            + " BLOB, "+ CUST_ID_PIC + " BLOB, "+ CUST_SIGNATURE+ " BLOB);";


    private static final String TIPE_CUSTOMER = "CREATE TABLE "
            + TABLE_TIPE_CUSTOMER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA_TIPE
            + " VARCHAR);";

    private static final String TIPE_UOM = "CREATE TABLE "
            + TABLE_UOM + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA_UOM
            + " VARCHAR);";

    private static final String KOTA = "CREATE TABLE "
            + TABLE_KOTA + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAMA_KOTA
            + " VARCHAR);";

    private static final String PRODUK = "CREATE TABLE "
            + TABLE_PRODUK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STOCK_CODE
            + " VARCHAR, " + STOCK_NAME + " VARCHAR, "+ STOCK_BRAND + " VARCHAR, "+ STOCK_CATEGORY1
            + " VARCHAR, "+ STOCK_CATEGORY2 + " VARCHAR, "+ STOCK_TYPE
            + " VARCHAR, "+ STOCK_UOM1 + " INTEGER, "+ STOCK_UOM2 + " INTEGER, "+ STOCK_UOM3
            + " INTEGER, "+ STOCK_UOMPRIMARY + " INTEGER, "+ STOCK_UOM_KONVERSI1 + " INTEGER, "+ STOCK_UOM_KONVERSI2
            + " INTEGER, "+ STOCK_UOM_KONVERSI3 + " INTEGER);";


      private static final String STOCK_CANVASER = "CREATE TABLE "
            + TABLE_STOCK_CANVAS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STOCK_CODE_CANVAS + " VARCHAR, "+ STOCK_QTY + " INT, "+ STOCK_UOM + " VARCHAR, "
            + STOCK_QTY + " INT, "+ STOCK_UOMX
            + " VARCHAR);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TRACK);
        db.execSQL(ABSEN_MASUK);
        db.execSQL(ABSEN_PULANG);
        db.execSQL(CUSTOMER);
        db.execSQL(ADD_CUSTOMER);
        db.execSQL(KOTA);
        db.execSQL(TIPE_CUSTOMER);
        db.execSQL(TIPE_UOM);
        db.execSQL(PRODUK);
        db.execSQL(STOCK_CANVASER);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String track_table = "DROP TABLE IF EXISTS "+TABLE_TRACK;
        String absen_masuk_table = "DROP TABLE IF EXISTS "+TABLE_ABSEN_MASUK;
        String absen_pulang_table = "DROP TABLE IF EXISTS "+TABLE_ABSEN_PULANG;
        String add_customer = "DROP TABLE IF EXISTS "+TABLE_ADD_CUSTOMER;
        String customer = "DROP TABLE IF EXISTS "+TABLE_CUSTOMER;
        String tipe_customer = "DROP TABLE IF EXISTS "+TABLE_TIPE_CUSTOMER;
        String kota = "DROP TABLE IF EXISTS "+TABLE_KOTA;
        String tipe_uom = "DROP TABLE IF EXISTS "+TABLE_UOM;
        String produk = "DROP TABLE IF EXISTS "+TABLE_PRODUK;
        String stock = "DROP TABLE IF EXISTS "+TABLE_STOCK_CANVAS;

        //eksekusinya

        db.execSQL(track_table);
        db.execSQL(absen_masuk_table);
        db.execSQL(absen_pulang_table);
        db.execSQL(add_customer);
        db.execSQL(customer);
        db.execSQL(tipe_customer);
        db.execSQL(kota);
        db.execSQL(tipe_uom);
        db.execSQL(produk);
        db.execSQL(stock);
        onCreate(db);
    }

    public boolean addTrack(String name, int group,  int status , String user, String tanggal, String jam, String latitude, String longitude, String kunjungan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GROUP, group);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(USER_TRACK, user);
        contentValues.put(COLUMN_TANGGAL, tanggal);
        contentValues.put(COLUMN_JAM, jam);
        contentValues.put(LATITUDE_TRACK, latitude);
        contentValues.put(LONGITUDE_TRACK, longitude);
        contentValues.put(KUNJUNGAN_TRACK, kunjungan);

        db.insert(TABLE_TRACK, null, contentValues);
        db.close();
        return true;
    }

    public boolean addAbsenMasuk(String name, int group, int status, String user, String tanggal
            , String jam, byte[] foto
            , String latitude, String longitude ){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GROUP, group);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(USER_ABSEN, user);
        contentValues.put(COLUMN_TANGGAL, tanggal);
        contentValues.put(COLUMN_JAM, jam);
        contentValues.put(FOTO, foto);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);


        db.insert(TABLE_ABSEN_MASUK, null, contentValues);
        db.close();
        return true;
    }

    public boolean addAbsenPulang(String name, int group, int status, String user, String tanggal
            , String jam, byte[] foto
            , String latitude, String longitude ){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GROUP, group);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(USER_ABSEN, user);
        contentValues.put(COLUMN_TANGGAL, tanggal);
        contentValues.put(COLUMN_JAM, jam);
        contentValues.put(FOTO, foto);
        contentValues.put(LATITUDE, latitude);
        contentValues.put(LONGITUDE, longitude);


        db.insert(TABLE_ABSEN_PULANG, null, contentValues);
        db.close();
        return true;
    }

    public boolean addcustomerpending(String name, int group, int status, String tanggal, String jam, String cust_company, String
                                      cust_address, String cust_city, String cust_phone, String
                                      cust_fax, String cust_mobile,String cust_id, String cust_name, String
                                      cust_id_address, String cust_id_city, String cust_npwp, String
                                      cust_npwp_name, String cust_npwp_address, String cust_nppkp, String
                                      cust_payment, String cust_limit, String cust_top, String cust_tipe_customer, String
                                      cust_since, String cust_sales_id, String cust_longitude, String cust_latitude, byte[]
                                      cust_pic, byte[] cust_ktp, byte[] cust_signature){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GROUP, group);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_TANGGAL, tanggal );
        contentValues.put(COLUMN_JAM, jam);
        contentValues.put(CUST_COMPANY, cust_company);
        contentValues.put(CUST_ADDRESS, cust_address);
        contentValues.put(CUST_CITY, cust_city);
        contentValues.put(CUST_PHONE, cust_phone);
        contentValues.put(CUST_FAX, cust_fax);
        contentValues.put(CUST_MOBILE, cust_mobile);
        contentValues.put(CUST_ID, cust_id);
        contentValues.put(CUST_NAME, cust_name);
        contentValues.put(CUST_ID_ADDRESS, cust_id_address);
        contentValues.put(CUST_ID_CITY, cust_id_city);
        contentValues.put(CUST_NPWP, cust_npwp);
        contentValues.put(CUST_NPWP_NAME, cust_npwp_name);
        contentValues.put(CUST_NPWP_ADDRESS, cust_npwp_address);
        contentValues.put(CUST_NPPKP, cust_nppkp);
        contentValues.put(CUST_PAYMENT, cust_payment);
        contentValues.put(CUST_LIMIT, cust_limit);
        contentValues.put(CUST_TOP, cust_top);
        contentValues.put(CUST_TIPE, cust_tipe_customer);
        contentValues.put(CUST_SINCE, cust_since);
        contentValues.put(CUST_SALES_ID, cust_sales_id);
        contentValues.put(CUST_LATITUDE, cust_latitude);
        contentValues.put(CUST_LONGITUDE, cust_longitude);
        contentValues.put(CUST_PIC, cust_pic);
        contentValues.put(CUST_ID_PIC, cust_ktp);
        contentValues.put(CUST_SIGNATURE, cust_signature);

        db.insert(TABLE_ADD_CUSTOMER, null, contentValues);
        db.close();
        return true;
    }

    public boolean addcustomer(String cust_code, int group, String cust_company, String
            cust_address, String cust_city, String cust_phone, String
                                              cust_fax, String cust_mobile, String cust_name, String
                                              cust_id_address, String cust_id_city, String cust_npwp, String
                                              cust_npwp_name, String cust_npwp_address, String cust_nppkp, String
                                              cust_payment, String cust_limit, String cust_top, String cust_tipe_customer, String
                                              cust_since, String cust_sales_id, String cust_longitude, String cust_latitude, byte[]
                                              cust_pic, byte[] cust_ktp, byte[] cust_signature){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CUST_CODE, cust_code);
        contentValues.put(COLUMN_GROUP, group);
        contentValues.put(CUST_COMPANY, cust_company);
        contentValues.put(CUST_ADDRESS, cust_address);
        contentValues.put(CUST_CITY, cust_city);
        contentValues.put(CUST_PHONE, cust_phone);
        contentValues.put(CUST_FAX, cust_fax);
        contentValues.put(CUST_MOBILE, cust_mobile);
        contentValues.put(CUST_NAME, cust_name);
        contentValues.put(CUST_ID_ADDRESS, cust_id_address);
        contentValues.put(CUST_ID_CITY, cust_id_city);
        contentValues.put(CUST_NPWP, cust_npwp);
        contentValues.put(CUST_NPWP_NAME, cust_npwp_name);
        contentValues.put(CUST_NPWP_ADDRESS, cust_npwp_address);
        contentValues.put(CUST_NPPKP, cust_nppkp);
        contentValues.put(CUST_PAYMENT, cust_payment);
        contentValues.put(CUST_LIMIT, cust_limit);
        contentValues.put(CUST_TOP, cust_top);
        contentValues.put(CUST_TIPE, cust_tipe_customer);
        contentValues.put(CUST_SINCE, cust_since);
        contentValues.put(CUST_SALES_ID, cust_sales_id);
        contentValues.put(CUST_LATITUDE, cust_latitude);
        contentValues.put(CUST_LONGITUDE, cust_longitude);
        contentValues.put(CUST_PIC, cust_pic);
        contentValues.put(CUST_ID_ADDRESS, cust_ktp);
        contentValues.put(CUST_SIGNATURE, cust_signature);




        db.insert(TABLE_ADD_CUSTOMER, null, contentValues);
        db.close();
        return true;
    }


    public boolean addTipeCustomer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAMA_TIPE, name);

        db.insert(TABLE_TIPE_CUSTOMER, null, contentValues);
        db.close();
        return true;
    }


    public boolean addTipeUom(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAMA_UOM, name);

        db.insert(TABLE_UOM, null, contentValues);
        db.close();
        return true;
    }


    public boolean addKota(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAMA_KOTA, name);

        db.insert(TABLE_KOTA, null, contentValues);
        db.close();
        return true;
    }

    public boolean addProduk(String stock_code, String stock_name,String stock_brand, String stock_category1,
                             String stock_category2, String stock_type, String stock_uom1, String stock_uom2, String stock_uom3,
                             String stock_uomprimary, int stock_konversi1, int stock_konversi2,
                             int stock_konversi3 ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STOCK_CODE, stock_code);
        contentValues.put(STOCK_NAME, stock_name);
        contentValues.put(STOCK_BRAND, stock_brand);
        contentValues.put(STOCK_CATEGORY1, stock_category1);
        contentValues.put(STOCK_CATEGORY2, stock_category2);
        contentValues.put(STOCK_TYPE, stock_type);
        contentValues.put(STOCK_UOM1, stock_uom1);
        contentValues.put(STOCK_UOM2, stock_uom2);
        contentValues.put(STOCK_UOM3, stock_uom3);
        contentValues.put(STOCK_UOMPRIMARY, stock_uomprimary);
        contentValues.put(STOCK_UOM_KONVERSI1, stock_konversi1);
        contentValues.put(STOCK_UOM_KONVERSI2, stock_konversi2);
        contentValues.put(STOCK_UOM_KONVERSI3, stock_konversi3);

        db.insert(TABLE_PRODUK, null, contentValues);
        db.close();
        return true;
    }


    public boolean addStockCanvaser(String stock_code, int stock_qty, String stock_uom, int stock_qtyx,
                                    String stock_uomx) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(STOCK_CODE_CANVAS, stock_code);
        contentValues.put(STOCK_QTY, stock_qty);
        contentValues.put(STOCK_UOM, stock_uom);
        contentValues.put(STOCK_QTYX, stock_qtyx);
        contentValues.put(STOCK_UOMX, stock_uomx);

        db.insert(TABLE_STOCK_CANVAS, null, contentValues);
        db.close();
        return true;
    }


    // bagian bagian update update

    public boolean updateTrackStatus(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_TRACK, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    public boolean updateAbsenMasuk(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_ABSEN_MASUK, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }

    public boolean updateAbsenPulang(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_ABSEN_PULANG, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }


    public boolean updateAddCustomer(int id, int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        db.update(TABLE_ADD_CUSTOMER, contentValues, COLUMN_ID + "=" + id, null);
        db.close();
        return true;
    }



    //bagian bagian yang history, sinkronasi dan  yang butuh joining

    // Class yang butuh joining


    // Riwayat Pekerjaan ( Pakai UNION ALL ajah )

    public Cursor getAllHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT "+ COLUMN_NAME + ", "+COLUMN_STATUS+", "+COLUMN_TANGGAL+", "+ COLUMN_JAM+" FROM "
                + TABLE_ABSEN_MASUK + " UNION ALL " +"SELECT "+ COLUMN_NAME + ", "+COLUMN_STATUS+", "+COLUMN_TANGGAL+", "+ COLUMN_JAM+" FROM "
                + TABLE_ABSEN_PULANG + " UNION ALL " +"SELECT " + COLUMN_NAME + ", "+COLUMN_STATUS+", "+COLUMN_TANGGAL+", "+ COLUMN_JAM+" FROM "
                + TABLE_ADD_CUSTOMER + " ORDER BY " + COLUMN_TANGGAL +" DESC, " +COLUMN_JAM+" DESC   ;";
         Cursor c = db.rawQuery(sql, null);
        return c;
    }

    // ini buat pacuan untuk sinkron ke MYSQL

    public Cursor unsyncedTrack() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_TRACK + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }



    public Cursor unsyncedAbsenMasuk() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ABSEN_MASUK + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }



    public Cursor unsyncedAbsenPulang() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ABSEN_PULANG + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    public Cursor unsyncedAddCustomer() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_ADD_CUSTOMER + " WHERE " + COLUMN_STATUS + " = 0;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    //spinner sqlite

    public List<String> spinKota(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " +NAMA_KOTA +
                "  FROM "+TABLE_KOTA;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;

    }

    public List<String> spinTipeCustomer(){
        List<String> labels = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT " +NAMA_TIPE +
                "  FROM "+TABLE_TIPE_CUSTOMER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public List<String> spinTipeUom(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT " +NAMA_UOM +
        "  FROM "+TABLE_UOM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }



    // Delete All table to want upgrade

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_KOTA);
        db.execSQL("delete from "+ TABLE_TIPE_CUSTOMER);
        db.execSQL("delete from "+ TABLE_PRODUK);
        db.close();
    }


    public void deleteProduk()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_PRODUK);
        db.close();
    }


    public void deleteStock()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_STOCK_CANVAS);
        db.close();
    }

}
