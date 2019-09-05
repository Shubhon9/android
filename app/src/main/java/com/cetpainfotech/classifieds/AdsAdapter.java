package com.cetpainfotech.classifieds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ganeshbisht on 25/02/18.
 */

public class AdsAdapter extends BaseAdapter {

    ArrayList<AdsModel>adsList;
    Context context;
    LayoutInflater inflater;

    public AdsAdapter(Context context, ArrayList<AdsModel> adsList)

    {
        this.adsList = adsList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return adsList.size();
    }

    @Override
    public Object getItem(int position) {
        return adsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.custom_item, null);

        AppCompatTextView ads_title = (AppCompatTextView) view.findViewById(R.id.ads_title);
        AppCompatTextView ads_tags = (AppCompatTextView) view.findViewById(R.id.ads_tags);
        AppCompatTextView ads_date = (AppCompatTextView) view.findViewById(R.id.ads_date);
        AppCompatTextView ads_price = (AppCompatTextView) view.findViewById(R.id.ads_price);
        ImageView ads_image = (ImageView) view.findViewById(R.id.ads_icon);

        ads_title.setText(adsList.get(position).getTitle());
        ads_tags.setText(adsList.get(position).getTags());
        ads_date.setText(adsList.get(position).getDate());
        ads_price.setText(adsList.get(position).getPrice());

       // Picasso.with(context).load(adsList.get(position).getMainImage()).into(ads_image);

        return view;
    }
}
