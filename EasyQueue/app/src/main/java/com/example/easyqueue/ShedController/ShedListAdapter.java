package com.example.easyqueue.ShedController;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.easyqueue.R;
import java.util.List;

public class ShedListAdapter extends ArrayAdapter<Shed> {

    Context mCtx;
    int resource;
    List<Shed> shedList;

    public static final String UID_EXTRA = "";

    public ShedListAdapter(Context mCtx, int resource, List<Shed> shedList) {
        super(mCtx, resource,shedList);

        this.mCtx = mCtx;
        this.resource = resource;
        this.shedList = shedList;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(resource,null);

        final TextView roomNo = view.findViewById(R.id.lb_roomNo4);
        final TextView rType = view.findViewById(R.id.lb_Rtype);
        TextView price = view.findViewById(R.id.lb_price);
        TextView f1 = view.findViewById(R.id.lb_f1);
        TextView f2 = view.findViewById(R.id.lb_f2);
        TextView f3 = view.findViewById(R.id.lb_f3);
        TextView f4 = view.findViewById(R.id.lb_f4);
        ImageView image = view.findViewById(R.id.imageView2);


        Button btnBook = view.findViewById(R.id.btn_booking);


//        btnBook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                Room r2 = roomList.get(position);
////                String UID = String.valueOf(r2.getUid());
////
////
////
////                Intent intent = new Intent(mCtx, AddBookingDetails.class);
////                intent.putExtra("roomNo",roomNo.getText().toString());
////                intent.putExtra("RType",rType.getText().toString());
////                intent.putExtra("ID",UID);
////
////                // Toast.makeText(, "", Toast.LENGTH_SHORT).show();
////
////                mCtx.startActivity(intent);
//
//            }
//        });

        Shed rl = shedList.get(position);

        roomNo.setText(String.valueOf(rl.getId()));
        rType.setText(rl.getName());
        price.setText(rl.getAddress());
        f1.setText(rl.getStatus());
        f2.setText(rl.getQueueStartTime());
        f3.setText(rl.getQueueEndTime());
        f4.setText(rl.getName());
        image.setImageDrawable(mCtx.getResources().getDrawable(rl.getImage()));



        return view;
    }
}
