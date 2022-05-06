package skripsi.android.andrian.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import skripsi.android.andrian.Adapter.model.products;
import skripsi.android.andrian.Productdetails;
import skripsi.android.andrian.R;

public class productAdapter extends RecyclerView.Adapter<productAdapter.productViewHolder> {

Context context;
List<products> productsList;

    public productAdapter(Context context, List<products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.products_row_item, parent, false);
        return new productViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {

        holder.prodImage.setImageResource(productsList.get(position).getImageUrl());
        holder.prodName.setText(productsList.get(position).getProductName());
        holder.prodQty.setText(productsList.get(position).getProductQty());
        holder.prodPrice.setText(productsList.get(position).getProductPrice());

        holder.itemView.setOnClickListener(view -> {

            Intent i = new Intent(context, Productdetails.class);
            Pair[] pairs =new Pair[1];
            pairs[0] = new Pair<View, String>(holder.prodImage,"image");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);
            context.startActivity(i, activityOptions.toBundle());

        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static final class productViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImage;
        TextView prodName, prodQty,prodPrice;
    public productViewHolder(@NonNull View itemView){

        super(itemView);

        prodImage = itemView.findViewById(R.id.prod_image);
        prodName = itemView.findViewById(R.id.prod_name);
        prodQty = itemView.findViewById(R.id.prod_qty);
        prodPrice = itemView.findViewById(R.id.prod_price);


    }


}


}
