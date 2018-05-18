package id.co.olaga.gosales.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.App.SessionManager;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.recycle.RecyclerViewAdapterStock;

import static id.co.olaga.gosales.App.AppController.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StockCanvasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StockCanvasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockCanvasFragment extends Fragment {
    FloatingActionButton fab;
    FloatingActionButton fabrefresh;
    private DatabaseHelper MyDatabase;
    View fragmentview;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> KodeList;
    private ArrayList<String> NamaList;
    private ArrayList<String> StockBks;
    private ArrayList<String> uom;
    private ArrayList<String> StockKrt;
    private ArrayList<String> uomx;


    SessionManager sessionManager;
    public static String nama;

    public static final String STOCK_CODE_CANVAS = "stock_code";
    public static final String STOCK_NAME_CANVAS = "stock_name";
    public static final String STOCK_QTY = "stock_qty";
    public static final String STOCK_UOM = "stock_uom";
    public static final String STOCK_QTYX = "stock_qtyx";
    public static final String STOCK_UOMX = "stock_uomx";
    private static final String TABLE_STOCK_CANVAS = "stock";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StockCanvasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockCanvasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockCanvasFragment newInstance(String param1, String param2) {
        StockCanvasFragment fragment = new StockCanvasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MenuActivity)getActivity()).setActionBarTitle("Stock Canvas");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentview = inflater.inflate(R.layout.fragment_stock_canvas, container, false);
        KodeList = new ArrayList<>();
        NamaList = new ArrayList<>();
        StockKrt = new ArrayList<>();
        uom = new ArrayList<>();
        StockBks = new ArrayList<>();
        uomx = new ArrayList<>();

        MyDatabase = new DatabaseHelper(getActivity());
        recyclerView = fragmentview.findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapterStock(KodeList, NamaList, StockKrt, uom, StockBks, uomx);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        final HashMap<String, String> user = sessionManager.getUserDetails();
        nama = user.get(SessionManager.kunci_user);

        fab = (FloatingActionButton) fragmentview.findViewById(R.id.fabpenjualan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputPenjualan.class);
                getActivity().startActivity(intent);
            }
        });


        fabrefresh = (FloatingActionButton) fragmentview.findViewById(R.id.refreshstock);
        fabrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uprading Your Data....", "Please wait...", false, false);

                ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {

                    //if connected to wifi or mobile data plan
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                        // Di Tambah Lagi Data nya
                        callVolley();

                        loading.dismiss();
                        Toast.makeText(getActivity(), "Data Stock Berhasil Di Perbaharui",Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Periksa Koneksi Jaringan Anda",Toast.LENGTH_LONG).show();
                }




            }
        });


        final SwipeRefreshLayout dorefresh = (SwipeRefreshLayout)fragmentview.findViewById(R.id.swipeRefresh);
        dorefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        /*event ketika widget dijalankan*/
        dorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshItem();
            }

            void refreshItem() {
               refreshAll();
                onItemLoad();
            }

            void onItemLoad() {
                dorefresh.setRefreshing(false);
            }

        });



        return fragmentview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    protected void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT "+ STOCK_CODE_CANVAS +", "+STOCK_NAME_CANVAS
                                            + ", "+ STOCK_QTY+ ", "+STOCK_UOM+ ", "+STOCK_QTYX
                                            + ", "+STOCK_UOMX + " FROM "+TABLE_STOCK_CANVAS,null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            KodeList.add(cursor.getString(0));//Menambil Data Dari Kolom 1 (Nama)
            NamaList.add(cursor.getString(1));
            StockKrt.add(cursor.getString(2));
            uom.add(cursor.getString(3));
            StockBks.add(cursor.getString(4));
            uomx.add(cursor.getString(5));
        }
    }

    private void callVolley(){
        DatabaseHelper db= new DatabaseHelper(getActivity());
        db.deleteStock();

        StringRequest eventoReq = new StringRequest(Request.Method.POST,AppVar.ADD_STOCK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());

                        try{
                            JSONArray j= new JSONArray(response);

                            // Parsea json
                            for (int i = 0; i < j.length(); i++) {
                                try {
                                    JSONObject obj = j.getJSONObject(i);

                                    DatabaseHelper db = new DatabaseHelper(getActivity());

                                    db.addStockCanvaser(obj.getString(AppVar.TAG_STOCK_CODE_CANVAS),obj.getString(AppVar.TAG_STOCK_NAME_CANVAS),obj.getInt(AppVar.TAG_STOCK_QTY),
                                            obj.getString(AppVar.TAG_STOCK_UOM), obj.getInt(AppVar.TAG_STOCK_QTYX),
                                            obj.getString(AppVar.TAG_STOCK_UOMX));


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put(AppVar.USER, "JA02");
                params.put(AppVar.TANGGAL, "2018-03-01");
                return params;
            }
        };

        // Añade la peticion a la cola
        AppController.getInstance(getActivity()).addToRequestQueue(eventoReq);
    }


    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private  void refreshAll(){

        KodeList = new ArrayList<>();
        NamaList = new ArrayList<>();
        StockKrt = new ArrayList<>();
        uom = new ArrayList<>();
        StockBks = new ArrayList<>();
        uomx = new ArrayList<>();

        MyDatabase = new DatabaseHelper(getActivity());
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapterStock(KodeList, NamaList, StockKrt, uom, StockBks, uomx);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

}
