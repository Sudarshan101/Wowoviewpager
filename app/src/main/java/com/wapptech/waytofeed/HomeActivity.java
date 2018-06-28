package com.wapptech.waytofeed;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wapptech.waytofeed.Fragment.EventFragment;
import com.wapptech.waytofeed.Fragment.HomeFragment;
import com.wapptech.waytofeed.utlity.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    public static int navItemIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addMenuItemInNavMenuDrawer();
        displaySelectedScreen(100);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {
        //creating fragment object
        Fragment fragment = null;
        Log.e("100", String.valueOf(itemId));
        //initializing the fragment object which is selected
        switch (itemId) {
            case 100:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case 101:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case 102:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case 103:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case 104:
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            case 0:
                fragment = new EventFragment();
                break;
            case 1:
                fragment = new EventFragment();
                break;
            case 2:
                fragment = new EventFragment();
                break;
            case R.id.home:
                fragment = new HomeFragment();
                break;
            default:
                fragment = new HomeFragment();
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void addMenuItemInNavMenuDrawer() {
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navView.getMenu();
        final Menu submenu = menu.addSubMenu("Categoryies");

        String url = "http://www.waytofeed.com/Api/getCategory";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (obj.getBoolean("status")) {
                                JSONArray jsonImages = obj.getJSONArray("data");
                                 for (int i = 0; i < jsonImages.length(); i++) {
                                    JSONObject jsonObject = jsonImages.getJSONObject(i);
                                    Log.e("jsonObject", String.valueOf(jsonObject));
                                    submenu.add(1, i ,0, jsonObject.getString("category"));
                                 }
                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("Something Went Wrong..."), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(JSONException e) {
                            Log.e("JSONException", String.valueOf(e.getMessage()));
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("onErrorResponse", String.valueOf(volleyError.getMessage()));
                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("limit", "4");
//                params.put("limit", String.valueOf(limit));
//                params.put("offset", String.valueOf(offset));
                return params;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



        Menu menu1 = navView.getMenu();

        Menu submenu1 = menu1.addSubMenu("Extra");
        submenu1.add(1, 100 ,0, "Login");
        submenu1.add(1, 101 ,0,"Setting");
        submenu1.add(1, 102 ,0,"Help");
        submenu1.add(1, 103 ,0,"Contact us");
        submenu1.add(1, 104 ,0,"About us");

        navView.invalidate();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }
}
