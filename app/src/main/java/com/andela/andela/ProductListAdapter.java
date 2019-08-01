package com.andela.andela;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by KIPCHU on 31/07/2019.
 */


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolders> {

    private final LayoutInflater mLayoutInflator;
    private final Context mContext;
    private final ArrayList<Product> mProductArrayList;

    public ProductListAdapter(Context context,ArrayList<Product>productArrayList) {
        mContext = context;
        mProductArrayList = productArrayList;
        mLayoutInflator = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ProductViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflator.inflate(R.layout.item_photos,viewGroup,false);
        return new ProductViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolders productViewHolders, int position) {
        Product product = mProductArrayList.get(position);
        Glide.with(mContext)
               .load(Uri.parse(product.getImage()))
                .into(productViewHolders.productImage);
    }

    @Override
    public int getItemCount() {
        return mProductArrayList.size();
    }

    public class ProductViewHolders extends RecyclerView.ViewHolder {
        private final ImageView productImage;
        public ProductViewHolders(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
