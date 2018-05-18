package id.co.olaga.gosales.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.olaga.gosales.R;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapterStock extends RecyclerView.Adapter<RecyclerViewAdapterStock.ViewHolder> {

    //Digunakan untuk Nama
    private ArrayList<String> kodeList;
    private ArrayList<String> namaList;
    private ArrayList<String> stockKrt;
    private ArrayList<String> uom;
    private ArrayList<String> stockBks;
    private ArrayList<String> uomx;

    private Context context; //Membuat Variable Context

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    public RecyclerViewAdapterStock(ArrayList<String> kodeList, ArrayList<String> namaList,
                                    ArrayList<String> stockKrt, ArrayList<String> uom,
                                    ArrayList<String> stockBks, ArrayList<String> uomx){
        this.kodeList = kodeList;
        this.namaList = namaList;
        this.stockKrt = stockKrt;
        this.uom = uom;
        this.stockBks = stockBks;
        this.uomx = uomx;

    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Kode;
        private TextView Nama;
        private TextView Krt;
        private TextView Uom;


        private ImageView Overflow;

        ViewHolder(View itemView) {
            super(itemView);

            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();

            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            Kode = itemView.findViewById(R.id.kodeproduk);
            Nama = itemView.findViewById(R.id.namaproduk);
            Krt = itemView.findViewById(R.id.stockkarton);
            Uom = itemView.findViewById(R.id.stockuom);


            Overflow = itemView.findViewById(R.id.overflow);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_desain_stock, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String Kode = kodeList.get(position);
        final String Nama = namaList.get(position);
        final String Krt= stockKrt.get(position);
        final String Uom = uom.get(position);
        final String Bks = stockBks.get(position);
        final String Uomx = uomx.get(position);


        holder.Kode.setText(Kode);
        holder.Nama.setText(Nama);

        int ket= Integer.parseInt(Bks);

        if(ket<40){

            holder.Krt.setText(Bks);
            holder.Uom.setText(Uomx);

        }else{

            holder.Krt.setText(Krt);
            holder.Uom.setText(Uom);

        }

        //Mengimplementasikan Menu Popup pada Overflow (ImageView)
        holder.Overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return namaList.size();

    }



}

