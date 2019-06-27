package com.example.findlostchildren.Constrollers.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findlostchildren.Models.VictimModel;
import com.example.findlostchildren.R;
import java.util.ArrayList;

public class AdapterNotificationListView extends ArrayAdapter {

        ArrayList<VictimModel> mlist;

        public AdapterNotificationListView(@NonNull Context context, int resource, @NonNull ArrayList objects) {
            super(context, resource, objects);

            mlist = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.item_listview_notification, parent,false);

            TextView textdisc = convertView.findViewById(R.id.poster_name_noti);
            TextView textTime = convertView.findViewById(R.id.victim_name_noti);
            ImageView images = convertView.findViewById(R.id.poster_photo_noti);



            return convertView;
        }

}
