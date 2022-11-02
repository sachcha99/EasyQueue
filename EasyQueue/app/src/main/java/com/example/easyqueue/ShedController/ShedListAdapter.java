package com.example.easyqueue.ShedController;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


        final TextView shedName = view.findViewById(R.id.txt_shedName);
        final TextView address = view.findViewById(R.id.txt_shedAddress);
        TextView shedOpen = view.findViewById(R.id.txt_shedStatus);
        TextView petrolStatus = view.findViewById(R.id.txt_petrolStatus);
        TextView dieselStatus = view.findViewById(R.id.txt_dieselStatus);
        TextView greenDot = view.findViewById(R.id.txt_statusDotGreen);
        TextView redDot = view.findViewById(R.id.txt_statusDotRed);


//        TextView pAvailable = view.findViewById(R.id.txt_statusPet);
//        TextView pQueueTime = view.findViewById(R.id.txt_queueStart);
//        TextView pCount = view.findViewById(R.id.txt_noVehicle);
//
//        TextView dAvailable = view.findViewById(R.id.txt_statusDes);
//        TextView dQueueTime = view.findViewById(R.id.txt_queueStartDes);
//        TextView dCount = view.findViewById(R.id.txt_noVehicleDes);

//
//
//        Button joinDiesel = view.findViewById(R.id.btn_joinDes);
//        Button joinPetrol = view.findViewById(R.id.btn_JoinPet);

//        joinPetrol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showConfirmBox(view,inflater, "Diesel");
//            }
//        });
//
//        joinDiesel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showConfirmBox(view,inflater,"Petrol");
//            }
//        });




        Shed shedObj = shedList.get(position);

        shedName.setText(String.valueOf(shedObj.getName()));
        address.setText(shedObj.getAddress());
        shedOpen.setText(shedObj.getStatus());
        petrolStatus.setText(shedObj.getPetrolStatus());
        dieselStatus.setText(shedObj.getDieselStatus());

        if(shedObj.getStatus().equals("Closed")){
            greenDot.setVisibility(View.GONE);
            redDot.setVisibility(View.VISIBLE);
        }

//        pAvailable.setText("Petrol "+shedObj.getStatus());
//        pQueueTime.setText(shedObj.getQueueStartTime());
//        pCount.setText(shedObj.getQueueEndTime());
//
//        dAvailable.setText("Diesel "+"Not Available");
//        dQueueTime.setText("15.55");
//        dCount.setText("15");
//
//        image.setImageDrawable(mCtx.getResources().getDrawable(shedObj.getImage()));
//
//        if(shedObj.getName()=="name1"){
//            if(shedObj.getStatus()=="petrol"){
//                dieselView.setVisibility(View.GONE);
//                joinPetrol.setText("Exit From Queue");
//
//            }else{
//                petrolView.setVisibility(View.GONE);
//                joinDiesel.setText("Exit From Queue");
//            }
//        }

        return view;
    }

    private void showConfirmBox(View view, LayoutInflater inflater, String type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(view.getRootView().getContext());
        final View dialogView = inflater.inflate(R.layout.activity_queueinbox, null);
        dialogBuilder.setView(dialogView);


        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.btn_confirm);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

        // dialogBuilder.setTitle(bookingId);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type=="Petrol"){
                    System.out.println("P");
                }else{
                    System.out.println("D");
                }
                b.dismiss();
//                Intent intent = new Intent(Booking_DetailsList.this,EditBookingDetails.class);
//                intent.putExtra("Bookingid",bookingId);
//                intent.putExtra("Roomno",roomNo);
//                intent.putExtra("Adultno",adultNo);
//                intent.putExtra("Childno",childNo);
//                intent.putExtra("Checkin",checkIn);
//                intent.putExtra("Checkout",checkOut);
//                startActivity(intent);


            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b.dismiss();
            }
        });
    }

}
