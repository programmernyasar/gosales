package id.co.olaga.gosales.ListAdapter;

/**
 * Created by root on 15/01/18.
 */

public class Nama {
    private String nama;
    private int status;
    private String tanggal;
    private String jam;

    public Nama(String name, int status, String tanggal, String jam) {
        this.nama = name;
        this.status = status;
        this.tanggal = tanggal;
        this.jam = jam;
    }

    public String getName() {
        return nama ;
    }

    public int getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal ;
    }

    public String getJam() {
        return jam ;
    }






}
