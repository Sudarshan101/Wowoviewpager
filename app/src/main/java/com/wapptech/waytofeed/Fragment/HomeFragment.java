package com.wapptech.waytofeed.Fragment;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wapptech.waytofeed.Adapter.HomeAdapter;
import com.wapptech.waytofeed.R;
import com.wapptech.waytofeed.utlity.Home;
import com.wapptech.waytofeed.utlity.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Home> homeList;
    private ProgressDialog pDialog;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        homeList = new ArrayList<>();
        adapter = new HomeAdapter(this, homeList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        prepareAlbums();
//
        return view;
    }

    private void prepareAlbums() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.waytofeed.com/Api/getpostdata",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pDialog.dismiss();
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            Log.e("data", String.valueOf(obj));
                            if (obj.getBoolean("status")) {
                                JSONArray jsonImages = obj.getJSONArray("postdata");
                                Log.e("data images", String.valueOf(jsonImages));
                                for (int i = 0; i < jsonImages.length(); i++) {
                                    JSONObject jsonObject = jsonImages.getJSONObject(i);
                                    Log.e("view count", String.valueOf(jsonObject.getInt("view_count")));
                                    Log.e("comment count", String.valueOf(jsonObject.getInt("comment_count")));
                                    Home  person = new Home();
                                    person.title = jsonObject.getString("title");
                                    person.id = jsonObject.getInt("id");
                                    person.category_id = jsonObject.getInt("category_id");
                                    person.slug = jsonObject.getString("slug");
                                    person.user_id = jsonObject.getInt("user_id");
                                    person.discription = jsonObject.getString("discription");
                                    person.short_discription = jsonObject.getString("short_discription");
                                    person.like_count = jsonObject.getInt("like_count");
                                    person.view_count = jsonObject.getInt("view_count");
                                    person.comment_count = jsonObject.getInt("comment_count");
                                    person.thumbnail_image = ("http://www.waytofeed.com/assets/uploads/"+jsonObject.getString("thumbnail_image"));
                                    person.tags = jsonObject.getString("tags");
                                    homeList.add(person);
                                }
                                adapter.notifyDataSetChanged();
                            }else{
                               Toast.makeText(getContext(), obj.getString("Something Went Wrong..."), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(JSONException e) {
                            pDialog.dismiss();
                            Log.e("JSONException", String.valueOf(e.getMessage()));
                            Toast.makeText(getActivity(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        pDialog.dismiss();
                        Log.e("onErrorResponse", String.valueOf(volleyError.getMessage()));
                        Toast.makeText(getActivity(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("limit", "10");
                params.put("offset", "0");
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
//
//        int[] covers = new int[]{
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed,
//                R.drawable.waytofeed};
//
//        Home a = new Home("True Romance", 13, covers[0]);
//        homeList.add(a);
//
//        a = new Home("Xscpae", 8, covers[1]);
//        homeList.add(a);
//
//        a = new Home("Maroon 5", 11, covers[2]);
//        homeList.add(a);
//
//        a = new Home("Born to Die", 12, covers[3]);
//        homeList.add(a);
//
//        a = new Home("Honeymoon", 14, covers[4]);
//        homeList.add(a);
//
//        a = new Home("I Need a Doctor", 1, covers[5]);
//        homeList.add(a);
//
//        a = new Home("Loud", 11, covers[6]);
//        homeList.add(a);
//
//        a = new Home("Legend", 14, covers[7]);
//        homeList.add(a);
//
//        a = new Home("Hello", 11, covers[8]);
//        homeList.add(a);
//
//        a = new Home("Greatest Hits", 17, covers[9]);
//        homeList.add(a);
//
//        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
