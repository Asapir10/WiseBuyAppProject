package com.example.aviasa100.myandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aviasa100.myandroidproject.utils.ProductDetails;

import java.util.List;

/**
 * Created by Aviasa100 on 04/04/2018.
 */

public class ProductListAdapter extends BaseAdapter{

    private Context mContext;
    private List<ProductDetails> mProductList;

    //constructor

    public ProductListAdapter(Context mContext,List<ProductDetails> mProductList){
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    @Override
    public int getCount() {
        return mProductList.size();

    }

    @Override
    public ProductDetails getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup) {
        if (v == null)v = LayoutInflater.from(mContext).inflate(R.layout.item_product_list, null);
        //ImageView productPic = (ImageView)v.findViewById(R.id.productImage) ;
        TextView productName = (TextView)v.findViewById(R.id.product_name);
        TextView productPrice = (TextView)v.findViewById(R.id.product_price);
        TextView productAmount = (TextView)v.findViewById(R.id.product_chosen_amount);
        //set text for TextView

        productName.setText(mProductList.get(position).getName());
        productPrice.setText(""+mProductList.get(position).getPrice());
        productAmount.setText(""+mProductList.get(position).getAmount());
        //productPic.setImageResource(R.drawable.bananas);
        return v;
    }
}
