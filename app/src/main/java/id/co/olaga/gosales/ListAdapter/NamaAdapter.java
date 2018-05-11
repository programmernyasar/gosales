package id.co.olaga.gosales.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.co.olaga.gosales.R;


/**
 * Created by Belal on 1/27/2017.
 */

public class NamaAdapter extends ArrayAdapter<Nama> {

    //storing all the names in the list
    private List<Nama> names;

    //context object
    private Context context;

    //constructor
    public NamaAdapter(Context context, int resource, List<Nama> names) {
        super(context, resource, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //getting the layoutinflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting listview itmes
        View listViewItem = inflater.inflate(R.layout.name, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewWaktu = (TextView) listViewItem.findViewById(R.id.textViewTanggal);
        ImageView imageViewStatus = (ImageView) listViewItem.findViewById(R.id.imageViewStatus);

        //getting the current name
       Nama name = names.get(position);

        //setting the name to textview
        textViewName.setText(name.getName());
        textViewWaktu.setText(name.getTanggal()+"  "+name.getJam());


        //if the synced status is 0 displaying
        //queued icon
        //else displaying synced icon
        if (name.getStatus() == 0)
            imageViewStatus.setBackgroundResource(R.drawable.stopwatch);
        else
            imageViewStatus.setBackgroundResource(R.drawable.success);

        return listViewItem;
    }
}
