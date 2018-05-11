package id.co.olaga.gosales.recycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.co.olaga.gosales.R;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{

    private ArrayList<String> namaList; //Digunakan untuk Nama
    private Context context; //Membuat Variable Context
    View V;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    public RecyclerViewAdapter2(ArrayList<String> namaList){
        this.namaList = namaList;

    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Nama;
        private ImageView Overflow;

        ViewHolder(View itemView) {
            super(itemView);

            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();

            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            Nama = itemView.findViewById(R.id.name);
            Overflow = itemView.findViewById(R.id.overflow);
             }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
         V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_desain_produk, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String Nama = namaList.get(position);//Mengambil data (Nama) sesuai dengan posisi yang telah ditentukan
        holder.Nama.setText(Nama);


        //Mengimplementasikan Menu Popup pada Overflow (ImageView)
        holder.Overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent toNext= new Intent(V.getContext() ,ViewDataProduk3.class);
                toNext.putExtra("kirimtype",Nama);
                V.getContext().startActivity(toNext);


            }
        });
    }

    @Override
    public int getItemCount() {
        return namaList.size();

}



}

