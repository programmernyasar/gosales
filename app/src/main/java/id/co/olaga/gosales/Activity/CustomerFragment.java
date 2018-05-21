package id.co.olaga.gosales.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.co.olaga.gosales.App.AppVar;
import id.co.olaga.gosales.R;
import id.co.olaga.gosales.App.globalVariable;
import id.co.olaga.gosales.recycle.RecyclerViewAdapterCustomer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFragment extends Fragment {

    private Context mContext;
    FloatingActionButton fabadd;
    SearchView searchView;
    RecyclerView listshowrcy;
    List<globalVariable> customerlist = new ArrayList<>();
    MenuItem item;
    RecyclerViewAdapterCustomer adapter;

    //volley


    JsonArrayRequest RequestOfJSonArray ;

    RequestQueue requestQueue ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerFragment newInstance(String param1, String param2) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        ((MenuActivity)getActivity()).setActionBarTitle("Pelanggan");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentview = inflater.inflate(R.layout.fragment_customer, container, false);

//
//        listshowrcy = (RecyclerView) fragmentview.findViewById(R.id.recycler);
//        listshowrcy.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        listshowrcy.setLayoutManager(linearLayoutManager);
//        adapter = new RecyclerViewAdapterCustomer(customerlist, mContext);
//        listshowrcy.setAdapter(adapter);


        fabadd = (FloatingActionButton) fragmentview.findViewById(R.id.addcustomer);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCustomer.class);
                getActivity().startActivity(intent);
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
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search, menu);
//
//        item = menu.findItem(R.id.search);
//        searchView = (SearchView) item.getActionView();
//        changeSearchViewTextColor(searchView);
//        ((EditText) searchView.findViewById(
//                android.support.v7.appcompat.R.id.search_src_text)).
//                setHintTextColor(getResources().getColor(R.color.putih));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean
//            onQueryTextSubmit(String query) {
//                if (!searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                item.collapseActionView();
//                return false;
//            }
//            @Override
//            public boolean
//            onQueryTextChange(String newText) {
//                final  List<globalVariable> filtermodelist=filter(customerlist,newText);
//                adapter.setfilter(filtermodelist);
//                return true;
//            }
//        });


//
//    }
//
//    //buat kata kunci yang dicari nya
//
//    private List<globalVariable> filter(List<globalVariable> pl,String query)
//    {
//        query=query.toLowerCase();
//        final List<globalVariable> filteredModeList=new ArrayList<>();
//        for (globalVariable model:pl)
//        {
//            final String text=model.getCust_company().toLowerCase();
//            if (text.startsWith(query))
//            {
//                filteredModeList.add(model);
//            }
//        }
//        return filteredModeList;
//    }
//
//
//    private void changeSearchViewTextColor(View view) {
//        if (view != null) {
//            if (view instanceof TextView) {
//                ((TextView) view).setTextColor(Color.WHITE);
//                return;
//            } else if (view instanceof ViewGroup) {
//                ViewGroup viewGroup = (ViewGroup) view;
//                for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                    changeSearchViewTextColor(viewGroup.getChildAt(i));
//                }
//            }
//        }
//    }


// the next
//
//    public void JSON_HTTP_CALL(){
//
//        RequestOfJSonArray = new JsonArrayRequest(AppVar.CUSTOMER,
//
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        ParseJSonResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        requestQueue = Volley.newRequestQueue(mContext);
//
//        requestQueue.add(RequestOfJSonArray);
//    }
//
//    public void ParseJSonResponse(JSONArray array){
//
//        for(int i = 0; i<array.length(); i++) {
//
//            globalVariable GetDataAdapter2 = new globalVariable());
//
//            JSONObject json = null;
//            try {
//
//                json = array.getJSONObject(i);
//
//                GetDataAdapter2.setImageTitle(json.getString(Image_Name_JSON));
//
//                // Adding image title name in array to display on RecyclerView click event.
//                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));
//
//                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));
//
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//            customerlist.add(GetDataAdapter2);
//        }
//
//    }
}



