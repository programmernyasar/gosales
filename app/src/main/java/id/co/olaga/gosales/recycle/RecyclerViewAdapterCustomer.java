package id.co.olaga.gosales.recycle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import id.co.olaga.gosales.App.ImageAdapter;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.App.globalVariable;

/** * Created by Tdev on 17/05/18. */


public class RecyclerViewAdapterCustomer  extends
        RecyclerView.Adapter<RecyclerViewAdapterCustomer.Holderview>{


    private List<globalVariable> customerlist;
    ImageLoader imageLoader;
    private Context context;


    public RecyclerViewAdapterCustomer(List<globalVariable> customerlist, Context context) {
        this.customerlist = customerlist;
        this.context = context;
    }


    @Override    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_desain_customer,parent,false);

        return new Holderview(layout);
    }


    @Override    public void onBindViewHolder(Holderview holder, final int position) {
        holder.cust_code.setText(customerlist.get(position).getCust_code());
        holder.cust_name.setText(customerlist.get(position).getCust_ktp());
        holder.cust_company.setText(customerlist.get(position).getCust_company());
        holder.city.setText(customerlist.get(position).getCust_kota());

        globalVariable dataAdapterOBJ =  customerlist.get(position);

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        holder.image_customer,//Server Image
                        R.drawable.noimage,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        holder.image_customer.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "click on " + customerlist.get(position).getCust_company(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return customerlist.size();
    }


    public void setfilter(List<globalVariable> listitem)
    {
        customerlist=new ArrayList<>();
        customerlist.addAll(listitem);
        notifyDataSetChanged();
    }


    class Holderview extends RecyclerView.ViewHolder
    {
        NetworkImageView image_customer;
        TextView cust_code;
        TextView cust_company;
        TextView cust_name;
        TextView city;

        Holderview(View itemview)
        {
            super(itemview);

            image_customer =(NetworkImageView) itemview.findViewById(R.id.image_customer);
            cust_code = (TextView) itemView.findViewById(R.id.cust_code);
            cust_company = (TextView) itemView.findViewById(R.id.cust_company);
            cust_name = (TextView) itemView.findViewById(R.id.cust_name);
            city = (TextView) itemView.findViewById(R.id.cust_city);

        }
    }
}