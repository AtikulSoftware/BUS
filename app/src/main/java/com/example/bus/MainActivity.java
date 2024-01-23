package com.example.bus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;

    List<BusModel> busList = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // পরিচয় করিয়ে দেওয়া হয়েছে ।
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);

        myAdapter = new MyAdapter(busList);

        // Create a JsonObjectRequest
        String API_URL = "http://your_website.com/Apps/bus.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if ("success".equals(status)) {

                        // bus থেকে data get করা হয়েছে ।
                        JSONArray busArray = response.getJSONArray("bus");

                        // for loop দিয়ে সব data get করা হয়েছে ।
                        for (int i = 0; i < busArray.length(); i++) {
                            JSONObject busObject = busArray.getJSONObject(i);

                            // bus এর ata get করে string এর মধ্যে রাখা হয়েছে ।
                            String routeName = busObject.getString("routeName");
                            String busName = busObject.getString("busName");
                            String imgUrl = busObject.getString("imgUrl");

                            // busDetails array get করা হয়েছে ।
                            JSONArray busDetailsArray = busObject.getJSONArray("busDetails");
                            List<BusDetailsModel> busDetailsList = new ArrayList<>();

                            // busDetails এর সব data get করার জন্য loop দেওয়া হয়েছে ।
                            for (int j = 0; j < busDetailsArray.length(); j++) {
                                JSONObject busDetailsObject = busDetailsArray.getJSONObject(j);
                                String counterName = busDetailsObject.getString("counterName");
                                String counterNumber = busDetailsObject.getString("counterNumber");
                                String busDetailsImgUrl = busDetailsObject.getString("imgUrl");

                                // data get করে model class এর সাহায্যে list এর মধ্যে put করা হয়েছে ।
                                BusDetailsModel busDetailsModel = new BusDetailsModel(counterName, counterNumber, busDetailsImgUrl);
                                busDetailsList.add(busDetailsModel);
                            }

                            // main list এর মধ্যে data get করে রাখা হয়েছে ।
                            BusModel busModel = new BusModel(routeName, busName, imgUrl, busDetailsList);
                            busList.add(busModel);

                        } // for loop এখানে শেষ হয়েছে ।


                        listView.setAdapter(myAdapter);

                    } else {
                        Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Toast.makeText(MainActivity.this, "Error retrieving data" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );


        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

    } // onCreate end here ============

    private class MyAdapter extends BaseAdapter {

        List<BusModel> busList;

        public MyAdapter(List<BusModel> busList) {
            this.busList = busList;
        }

        public void setFilteredList(List<BusModel> filteredList) {
            this.busList = filteredList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return busList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View myView = layoutInflater.inflate(R.layout.bus_item, parent, false);

            // পরিচয় করিয়ে দেওয়া হয়েহে ।
            TextView tvBusName = myView.findViewById(R.id.tvBusName);
            TextView tvRouteName = myView.findViewById(R.id.tvRouteName);
            ImageView TvImgUrl = myView.findViewById(R.id.imgUrl);
            RelativeLayout itemLayout = myView.findViewById(R.id.itemLayout);

            // model কে call করা হয়েছে ।
            BusModel busModel = busList.get(position);

            // data get করে show করা হয়েছে ।
            tvBusName.setText(busModel.getBusName());
            tvRouteName.setText(busModel.getRouteName());
            Picasso.get().load(busModel.getImgUrl()).into(TvImgUrl);

            // item এর onClick লেখা হয়েছে ।
            itemLayout.setOnClickListener(v -> {
                // bus এর list get করে BusDetailsActivity তে পাঠিয়ে দেওয়া হলো ।
                BusDetailsActivity.busDetailsModelList = busModel.getBusDetails();
                startActivity(new Intent(MainActivity.this, BusDetailsActivity.class));
            });

            return myView;
        }
    } // MyAdapter end here =========


    private void fileList(String newText) {
        List<BusModel> filteredList = new ArrayList<>();
        for (BusModel detailsItem : busList) {
            if (detailsItem.getBusName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(detailsItem);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            myAdapter.setFilteredList(filteredList);
        }

    }


} // public class end here ============