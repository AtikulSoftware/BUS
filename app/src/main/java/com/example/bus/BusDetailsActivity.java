package com.example.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BusDetailsActivity extends AppCompatActivity {

    ListView listView;
    public static List<BusDetailsModel> busDetailsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        // পরিচয় করিয়ে দেওয়া হয়েছে ।
        listView = findViewById(R.id.listView);

        // Adapter কে পরিচয় করিয়ে দেওয়া হয়েছে এবং listview তে set করে দেওয়া হয়েছে ।
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

    } // onCreate method end here ============

    private class MyAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return busDetailsModelList.size();
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

            TextView counterName = myView.findViewById(R.id.tvBusName);
            TextView counterNumber = myView.findViewById(R.id.tvRouteName);
            ImageView imgUrl = myView.findViewById(R.id.imgUrl);

            BusDetailsModel busDetailsModel = busDetailsModelList.get(position);
            counterName.setText(busDetailsModel.getCounterName());
            counterNumber.setText(busDetailsModel.getCounterNumber());
            Picasso.get().load(busDetailsModel.getImgUrl()).into(imgUrl);

            return myView;
        }

    } // MyAdapter end here =====================




} // public class end here ===================