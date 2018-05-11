package id.co.olaga.gosales.tabswipe;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import id.co.olaga.gosales.Activity.AddCustomer;
import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class owner extends Fragment {


    public static final String TITLE = "Owner";
    EditText no_ktp;
    EditText nm_owner;
    EditText alamat_owner;
    Spinner kota_owner;
    AddCustomer activity;
    private DatabaseHelper db;

    public static owner newInstance() {

        return new owner();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // daftarkan fragment mu
        View fragmentview =  inflater.inflate(R.layout.fragment_owner, container, false);
        db = new DatabaseHelper(getActivity());



        // inisialisasi yang ada di layout
        no_ktp = (EditText)fragmentview.findViewById(R.id.no_ktp);
        nm_owner = (EditText)fragmentview.findViewById(R.id.owner);
        alamat_owner = (EditText)fragmentview.findViewById(R.id.alm_owner);
        kota_owner = (Spinner) fragmentview.findViewById(R.id.kota_owner);

        // membuat textwatcher
        no_ktp.addTextChangedListener(watcher_noktp);
        nm_owner.addTextChangedListener(watcher_nm_owner);
        alamat_owner.addTextChangedListener(watcher_alamat_owner);

        loadSpinnerKota();


        kota_owner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerKirim=kota_owner.getSelectedItem().toString();
                activity.getGv().setCust_kota_ktp(spinnerKirim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return fragmentview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AddCustomer) context;

    }

    private final TextWatcher watcher_noktp = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=no_ktp.getText().toString();
            activity.getGv().setCust_ktp(hasil);

        }
    };

    private final TextWatcher watcher_nm_owner= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=nm_owner.getText().toString();
            activity.getGv().setCust_nama_ktp(hasil);

        }
    };

    private final TextWatcher watcher_alamat_owner = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=alamat_owner.getText().toString();
            activity.getGv().setCust_ktp_alamat(hasil);

        }
    };



    private void loadSpinnerKota() {

        // Spinner Drop down elements
        List<String> lables = db.spinKota();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        kota_owner.setAdapter(dataAdapter);
    }

//    private void loadSpinnerData(){
//        DatabaseHelper db = new DatabaseHelper(getActivity());
//        List<String> categories = db.spinKota();
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        kota_owner.setAdapter(dataAdapter);
//    }
}
