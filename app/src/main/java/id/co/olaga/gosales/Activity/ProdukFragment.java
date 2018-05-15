package id.co.olaga.gosales.Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
import id.co.olaga.gosales.recycle.RecyclerViewAdapter;
import id.co.olaga.gosales.recycle.RecyclerViewAdapterStock;

import static id.co.olaga.gosales.App.AppController.TAG;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProdukFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProdukFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProdukFragment extends Fragment {

    FloatingActionButton fabrefresh;
    private DatabaseHelper MyDatabase;
    View fragmentview;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> NamaList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProdukFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProdukFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProdukFragment newInstance(String param1, String param2) {
        ProdukFragment fragment = new ProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MenuActivity)getActivity()).setActionBarTitle("Produk");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        fragmentview = inflater.inflate(R.layout.fragment_produk, container, false);
        NamaList = new ArrayList<>();
        MyDatabase = new DatabaseHelper(getActivity());
        recyclerView = fragmentview.findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(NamaList);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);



        // ini fab buat refresh data sqlite
        fabrefresh = (FloatingActionButton) fragmentview.findViewById(R.id.refreshproduk);
        fabrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callVolleyProduk();
                refreshAll();
                Toast.makeText(getActivity(), "Refresh Your Produk", Toast.LENGTH_LONG).show();
            }
        });

//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (dy > 0 && fabrefresh.getVisibility() == View.VISIBLE) {
//                    fabrefresh.hide();
//                } else if (dy < 0 && fabrefresh.getVisibility() != View.VISIBLE) {
//                    fabrefresh.show();
//                }
//
//
//
//            }
//        });


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


    // mulai dari sini function function penting


    private void callVolleyProduk(){

        DatabaseHelper db= new DatabaseHelper(getActivity());
        db.deleteProduk();

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(AppVar.ADD_PRODUK, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DatabaseHelper db = new DatabaseHelper(getActivity());

                        db.addProduk(obj.getString(AppVar.TAG_STOCK_CODE),obj.getString(AppVar.TAG_STOCK_NAME),
                                obj.getString(AppVar.TAG_STOCK_BRAND), obj.getString(AppVar.TAG_STOCK_CATEGORY1),
                                obj.getString(AppVar.TAG_STOCK_CATEGORY2), obj.getString(AppVar.TAG_STOCK_TYPE),
                                obj.getString(AppVar.TAG_STOCK_UOM1), obj.getString(AppVar.TAG_STOCK_UOM2),
                                obj.getString(AppVar.TAG_STOCK_UOM3), obj.getString(AppVar.TAG_STOCK_UOMPRIMARY),
                                obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI1), obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI2),
                                obj.getInt(AppVar.TAG_STOCK_UOM_KONVERSI3));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        // menambah request ke request queue
        AppController.getInstance(getActivity()).addToRequestQueue(jArr);
    }

    protected void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT DISTINCT "+ AppVar.TAG_STOCK_CATEGORY1 + " FROM produk ",null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir

            NamaList.add(cursor.getString(0));//Menambil Data Dari Kolom 1 (Nama)

        }
    }

    private  void refreshAll(){
        NamaList.clear();
        NamaList = new ArrayList<>();
        MyDatabase = new DatabaseHelper(getActivity());
        recyclerView = fragmentview.findViewById(R.id.recycler);
        getData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(NamaList);

        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    
}
