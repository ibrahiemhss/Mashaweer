package com.mashaweer.ibrahim.mashaweer.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mashaweer.ibrahim.mashaweer.MainActivityDrivers;
import com.mashaweer.ibrahim.mashaweer.ShowForUseActivity;
import com.mashaweer.ibrahim.mashaweer.R;
import com.mashaweer.ibrahim.mashaweer.model.DriverModel;
import com.mashaweer.ibrahim.mashaweer.model.BookModels;

import java.util.List;

/**
 * Created by Administrator on 08/07/2017.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ClientHolder> {

    List<DriverModel> forClientsModels;
    Context context;
    AlertDialog.Builder builder;


    SharedPreferences pref,prefsh;
    SharedPreferences.Editor editor,editorsh;

    String user_id,sh_id;

  //  float foo = 3.5;

    public DriverAdapter(List<DriverModel> forClientsModels, Context context) {
        super();
        this.context=context;
        this.forClientsModels = forClientsModels;


    }

    public DriverAdapter(List<BookModels> bookModels, MainActivityDrivers context) {
    }




    /* public CommentAdapter(List<ListComments> mylist) {
     }
 */


    @Override
    public DriverAdapter.ClientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.list_item_driver, parent, false);
        view.setLayoutParams(new RecyclerView.LayoutParams( RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        DriverAdapter.ClientHolder holder = new ClientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DriverAdapter.ClientHolder holder, final int position) {
        DriverModel SH = forClientsModels.get(position);

        holder.ShName.setText(SH.getNAME());
     //   holder.ShPhone.setText(SH.getPHONE());
        holder.model_car.setText(SH.getMODEL_CAR());
       // holder.distance.setText(SH.getDistance());
      //  holder.rbRating.setRating((SH.getRating()));

       // holder.sh_idText.setText(SH.getSH_ID());
       // final String sh_id = holder.sh_idText.getText().toString();



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {




                final String name=holder.ShName.getText().toString();
              //  final String phone=holder.ShPhone.getText().toString();
                final String model_car=holder.model_car.getText().toString();
                builder = new AlertDialog.Builder(DriverAdapter.this.context);


                builder.setTitle("عرض معلومات السائق");
                builder.setMessage("");
                builder.setPositiveButton("نعم ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       /* Intent intent = new Intent(DriverAdapter.this.context, OnLineShofier.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name",name );
                        bundle.putString("model_car",model_car );

                        intent.putExtras(bundle);
                        context.startActivity(intent);

*/
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


        holder.riverDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          String i;

               i =forClientsModels.get(position).getSH_ID();

                context. startActivity(new Intent(context, ShowForUseActivity.class));

                Toast.makeText(DriverAdapter.this.context,i , Toast.LENGTH_LONG).show();

              //  rating_dailoge DD=new rating_dailoge(context,i);

              //  DD.show();
            }
        });

        ///////////////////////////////






    }
    @Override
    public int getItemCount()
    {
        if (forClientsModels != null) {
            return forClientsModels.size();

        }
        return 0;



    }


   public class ClientHolder extends RecyclerView.ViewHolder  {






     //   TextView ShPhone;
        TextView ShName;
        TextView model_car;
        Button riverDetails;
        ImageView imageCarKind ;
      //  TextView distance;
     //  TextView sh_idText;
      // Button btn_rate;

       public RatingBar rbRating;
       private ClientHolder(View itemView) {
            super(itemView);


            ShName = (TextView) itemView.findViewById( R.id.ShName);

          //  ShPhone = (TextView) itemView.findViewById( R.id.ShPhone);
            model_car = (TextView) itemView.findViewById( R.id.model_car);
           imageCarKind=itemView.findViewById( R.id.imageCarKind );
           riverDetails=itemView.findViewById( R.id.riverDetails );

         //  distance=(TextView)itemView.findViewById( R.id.distance1);
          // sh_idText=(TextView)itemView.findViewById( R.id.sh_id);
         //  rbRating = (RatingBar) itemView.findViewById( R.id.ratingBarID);
         //  btn_rate= (Button) itemView.findViewById( R.id.btn_rate);


       }



   }

   }







