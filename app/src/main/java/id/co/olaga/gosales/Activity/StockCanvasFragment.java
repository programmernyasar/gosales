package id.co.olaga.gosales.Activity;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import id.co.olaga.gosales.App.AppController;
import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.Sqlite.DatabaseHelper;
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
    private Location location;
    public static double latitude;
    public static double longitude;
    TextView testjuga;
    Geocoder geocoder;
    List<Address> addresses;

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
        View fragmentview = inflater.inflate(R.layout.fragment_stock_canvas, container, false);

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

                callVolleyStock();
                Toast.makeText(getActivity(), "Refresh Your Stock", Toast.LENGTH_LONG).show();
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


    private void callVolleyStock(){

        DatabaseHelper db= new DatabaseHelper(getActivity());
        db.deleteProduk();

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(AppVar.ADD_STOCK, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DatabaseHelper db = new DatabaseHelper(getActivity());

                        db.addStockCanvaser(obj.getString(AppVar.TAG_STOCK_CODE_CANVAS),obj.getInt(AppVar.TAG_STOCK_QTY),
                                obj.getString(AppVar.TAG_STOCK_UOM), obj.getInt(AppVar.TAG_STOCK_QTYX),
                                obj.getString(AppVar.TAG_STOCK_UOMX));


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


}
