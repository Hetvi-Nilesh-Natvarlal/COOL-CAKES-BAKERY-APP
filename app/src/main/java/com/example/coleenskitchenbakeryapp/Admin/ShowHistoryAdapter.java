package com.example.coleenskitchenbakeryapp.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coleenskitchenbakeryapp.History;
import com.example.coleenskitchenbakeryapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowHistoryAdapter extends RecyclerView.Adapter<ShowHistoryAdapter.myviewholder> {
    Context context;
    ArrayList<History> list;
    DatabaseReference ref;
    FirebaseAuth auth;
    String Auth;

    public ShowHistoryAdapter(ArrayList<History> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showproduct, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        History History = list.get(position);
        holder.name.setText("Name:" + History.getName());
        holder.flavour.setText("Flavour:" + History.getFlavour());
        holder.price.setText("Rs." + History.getPrice());
        holder.deliprice.setText("Delivery Price:" + History.getDeliprice());
        holder.quantity.setText("Quantity:" + History.getQuantity());
        holder.wordings.setText("Wordings:" + History.getWordings());
        holder.desc.setText("Description Entered:" + History.getDesc());
        holder.pid.setText("Pid:" + History.getPid());
        holder.date.setText("Date Of Ordering:" + History.getDate());
        holder.uuid.setText("Order Id:" + History.getUuid());
        Picasso.get().load(History.getImg_url()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, flavour, price, deliprice, quantity, wordings, desc, pid, date, uuid;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.a_showimg);
            name = (TextView) itemView.findViewById(R.id.a_showName);
            flavour = (TextView) itemView.findViewById(R.id.a_showflavours);
            price = (TextView) itemView.findViewById(R.id.a_showPrice);
            deliprice = (TextView) itemView.findViewById(R.id.a_showdevprice);
            quantity = (TextView) itemView.findViewById(R.id.a_showquantity);
            wordings = (TextView) itemView.findViewById(R.id.a_showcatid);
            desc = (TextView) itemView.findViewById(R.id.a_showweight);
            pid = (TextView) itemView.findViewById(R.id.a_showpid);
            date = (TextView) itemView.findViewById(R.id.a_showcatname);
            uuid = (TextView) itemView.findViewById(R.id.a_showdesc);

        }
    }
}
