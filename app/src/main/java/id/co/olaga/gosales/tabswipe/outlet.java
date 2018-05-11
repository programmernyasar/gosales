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
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class outlet extends Fragment {

    public static final String TITLE = "Outlet";
    EditText vnmToko;
    EditText valamatToko;
    EditText vhpToko;
    EditText vfaxToko;
    EditText vteleponToko;
    Spinner vkotaToko;
    Spinner vtipeToko;
    AddCustomer activity;
    private DatabaseHelper db;


    public static outlet newInstance() {

        return new outlet();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // daftarkan fragment
        View fragmentview = inflater.inflate(R.layout.fragment_outlet, container, false);
        db = new DatabaseHelper(getActivity());

        // inisialisasi yang di layout
        vnmToko = (EditText) fragmentview.findViewById(R.id.nm_toko);
        valamatToko = (EditText) fragmentview.findViewById(R.id.alm_toko);
        vhpToko = (EditText) fragmentview.findViewById(R.id.nm_toko);
        vfaxToko = (EditText) fragmentview.findViewById(R.id.nm_toko);
        vteleponToko = (EditText) fragmentview.findViewById(R.id.nm_toko);
        vkotaToko =(Spinner)fragmentview.findViewById(R.id.kota_outlet);
        vtipeToko =(Spinner)fragmentview.findViewById(R.id.tipe_outlet);

        // membuat textwatcher
        vnmToko.addTextChangedListener(watcher_nmtoko);
        valamatToko.addTextChangedListener(watcher_alm_toko);
        vhpToko.addTextChangedListener(watcher_hp_toko);
        vfaxToko.addTextChangedListener(watcher_fax_toko);
        vteleponToko.addTextChangedListener(watcher_telepon_toko);

        loadSpinnerKota();
        loadSpinnerTipe();

        vkotaToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerKirim=vkotaToko.getSelectedItem().toString();
                activity.getGv().setCust_kota(spinnerKirim);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vtipeToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerKirim=vtipeToko.getSelectedItem().toString();
                activity.getGv().setCust_tipe(spinnerKirim);
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

    private final TextWatcher watcher_nmtoko = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=vnmToko.getText().toString();
            activity.getGv().setCust_company(hasil);

        }
    };

    private final TextWatcher watcher_alm_toko = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=valamatToko.getText().toString();
            activity.getGv().setCust_alamat(hasil);

        }
    };

    private final TextWatcher watcher_hp_toko = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=vhpToko.getText().toString();
            activity.getGv().setCust_hp(hasil);

        }
    };

    private final TextWatcher watcher_fax_toko = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=vfaxToko.getText().toString();
            activity.getGv().setCust_fax(hasil);

        }
    };

    private final TextWatcher watcher_telepon_toko = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=vteleponToko.getText().toString();
            activity.getGv().setCust_telepon(hasil);

        }
    };


    private void loadSpinnerKota() {

        // Spinner Drop down elements
        List<String> lablesKota = db.spinKota();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterKota = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lablesKota);

        // Drop down layout style - list view with radio button
        dataAdapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
     vkotaToko.setAdapter(dataAdapterKota);
    }

    private void loadSpinnerTipe() {

        // Spinner Drop down elements
        List<String> lablesTipe = db.spinTipeCustomer();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterTipe = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lablesTipe);

        // Drop down layout style - list view with radio button
        dataAdapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        vtipeToko.setAdapter(dataAdapterTipe);
    }

}
