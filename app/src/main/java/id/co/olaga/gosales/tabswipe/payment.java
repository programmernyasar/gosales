package id.co.olaga.gosales.tabswipe;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import id.co.olaga.gosales.Activity.AddCustomer;
import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class payment extends Fragment {

    public static final String TITLE = "Payment";
    Spinner payment;
    EditText limit;
    EditText top;
    AddCustomer activity;

    public static payment newInstance() {

        return new payment();
    }


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // daftarkan fragment
        View fragmentview = inflater.inflate(R.layout.fragment_payment, container, false);


        // inisialisasi yang di layout
        payment = (Spinner)fragmentview.findViewById(R.id.payment);
        limit = (EditText)fragmentview.findViewById(R.id.limit);
        top = (EditText)fragmentview.findViewById(R.id.top);



        //invisible
           limit.setVisibility(View.INVISIBLE);
           top.setVisibility(View.INVISIBLE);


        // membuat textwatcher
        limit.addTextChangedListener(watcher_limit);
        top.addTextChangedListener(watcher_top);

           payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String spinnerKirim=payment.getSelectedItem().toString();
                   activity.getGv().setCust_payment(spinnerKirim);
                   if(spinnerKirim.equals("KREDIT")) {
                       limit.setVisibility(View.VISIBLE);
                       top.setVisibility(View.VISIBLE);

                   }
                   else if (spinnerKirim.equals("CASH")){
                       limit.setVisibility(View.INVISIBLE);
                       top.setVisibility(View.INVISIBLE);

                   }
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

    private final TextWatcher watcher_limit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=limit.getText().toString();
            activity.getGv().setCust_limit(hasil);

        }
    };
    private final TextWatcher watcher_top = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String hasil=top.getText().toString();
            activity.getGv().setCust_top(hasil);

        }
    };


}
