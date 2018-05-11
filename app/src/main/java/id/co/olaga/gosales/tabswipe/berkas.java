package id.co.olaga.gosales.tabswipe;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import id.co.olaga.gosales.Activity.AddCustomer;
import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class berkas extends Fragment {

    public static final String TITLE = "NPWP";
    EditText no_npwp;
    EditText alamat_npwp;
    EditText nm_npwp;
    EditText no_nppkp;
    AddCustomer activity;
    public static berkas newInstance() {

        return new berkas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview = inflater.inflate(R.layout.fragment_berkas, container, false);


        // inisialisasi pada layout
        no_npwp = (EditText) fragmentview.findViewById(R.id.no_npwp);
        alamat_npwp = (EditText)fragmentview.findViewById(R.id.alm_npwp);
        nm_npwp = (EditText)fragmentview.findViewById(R.id.nm_npwp);
        no_nppkp = (EditText)fragmentview.findViewById(R.id.no_nppkp);

        // membuat textwatcher
        no_npwp.addTextChangedListener(watcher_nonpwp);
        alamat_npwp.addTextChangedListener(watcher_alamat_npwp);
        nm_npwp.addTextChangedListener(watcher_nm_npwp);
        no_nppkp.addTextChangedListener(watcher_nonppkp);



        return fragmentview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AddCustomer) context;

    }

    private final TextWatcher watcher_nonpwp = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=no_npwp.getText().toString();
            activity.getGv().setCust_npwp(hasil);

        }
    };
    private final TextWatcher watcher_alamat_npwp = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=alamat_npwp.getText().toString();
            activity.getGv().setCust_alamat_npwp(hasil);

        }
    };
    private final TextWatcher watcher_nm_npwp = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=nm_npwp.getText().toString();
            activity.getGv().setCust_nama_npwp(hasil);

        }
    };
    private final TextWatcher watcher_nonppkp = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=no_nppkp.getText().toString();
            activity.getGv().setCust_nppkp(hasil);

        }
    };

}
