package com.mashaweer.ibrahim.mashaweer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mashaweer.ibrahim.mashaweer.Fragments.OnlineClients;
import com.mashaweer.ibrahim.mashaweer.R;
import com.mashaweer.ibrahim.mashaweer.model.BookModels;

import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 28/06/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyHolder> {

    List<BookModels> bookModelses;
    Context context;
    AlertDialog.Builder builder;

    public BookAdapter(List<BookModels> bookModelses, Context context) {
        super();
        this.context=context;
        this.bookModelses=bookModelses;
    }



    /* public CommentAdapter(List<ListComments> mylist) {
     }
 */


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item_booking, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams( RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final BookModels SH = bookModelses.get(position);

        holder.username.setText(SH.getUsername());
        holder.phone.setText(SH.getPhone());
        holder.whereFace.setText(SH.getFace());
        holder.traveTimeBB.setText(SH.getTraveTime());
        holder.id_user_to_Dr.setText(SH.getId());
        holder.wherefrom.setText(SH.getWherefrom());

//        holder.user_id.setText(SH.getUser_id());
     //   holder.car_id.setText(SH.getCar_id());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String latitude,longitude;
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)",
                        Double.toString(bookModelses.get(position).getLatitude()), Double.toString(bookModelses.get(position).getLongitude()),bookModelses.get(position).getUsername());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.ShowForUseActivity");
                context.startActivity(intent);

                final String username=holder.username.getText().toString();
                final String phone=holder.phone.getText().toString();
                final String traveTimeBB=holder.traveTimeBB.getText().toString();
                final String whereFace=holder.whereFace.getText().toString();
                final String id=holder.id_user_to_Dr.getText().toString();
                final String wherF=holder.wherefrom.getText().toString();

                builder = new AlertDialog.Builder(BookAdapter.this.context);


                builder.setTitle("عرض معلومات الزبون");
                builder.setMessage("");
                builder.setPositiveButton("نعم ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BookAdapter.this.context, OnlineClients.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name",username );
                        bundle.putString("phone",phone );
                        bundle.putString("traveTimeBB",traveTimeBB );
                        bundle.putString("whereFace",whereFace );
                        bundle.putString("id_user_to_Dr",id );
                        bundle.putString("wherefrom",wherF);
                        intent.putExtras(bundle);
                        context.startActivity(intent);


                    } });

                builder.setNegativeButton("لا ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });
                builder.show();

                return true;
            }
        });
    }
    @Override
    public int getItemCount()
    {
        if(bookModelses!=null){
            return bookModelses.size();

        }
        return 0 ;

    }



    public class MyHolder extends RecyclerView.ViewHolder{

        // Typeface customTypeOne = Typeface.createFromAsset(itemView.getContext().getAssets(), "DroidNaskh-Regular.ttf");


        TextView username;
        TextView phone;
        TextView whereFace;
        TextView traveTimeBB;
        TextView id_user_to_Dr;
        TextView wherefrom;

        MyHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById( R.id.username);
            phone = (TextView) itemView.findViewById( R.id.phone);
            whereFace = (TextView) itemView.findViewById( R.id.whereFace);
            traveTimeBB = (TextView) itemView.findViewById( R.id.traveTimeBB);
            id_user_to_Dr = (TextView) itemView.findViewById( R.id.id_user_to_Dr);
            wherefrom = (TextView) itemView.findViewById( R.id.wherefrom);
        }

    }}