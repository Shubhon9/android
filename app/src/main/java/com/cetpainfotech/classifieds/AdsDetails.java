package com.cetpainfotech.classifieds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdsDetails extends AppCompatActivity {

    TextView title,price,contact,description;
    ImageView post_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_details);

        title=(TextView)findViewById(R.id.title);
        price=(TextView)findViewById(R.id.price);
        contact=(TextView)findViewById(R.id.contact);
        description=(TextView)findViewById(R.id.description);

        post_image=(ImageView)findViewById(R.id.image);



    }
}
