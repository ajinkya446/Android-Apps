package com.ajinkya.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private SearchView search;
    public static ImageView imgl,img2,img3,img4,img5,img6,img7,img8,image1,pic;
    CardView c;
    public static TextView name;
    public static TextView id1,bname,cname,kg,price,day,day1,price1,kg1,day2,price2,kg2;
    public static TextView id2;
    public static TextView id3;
    public static TextView id4;
    public static TextView id5;
    public static TextView id6;
    public static TextView id7;
    public static TextView id8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=(SearchView)findViewById(R.id.search_bar);
        search.setBackgroundResource(R.drawable.search_round);

        bname=(TextView)findViewById(R.id.textView4);
        day=(TextView)findViewById(R.id.textView5);
        price=(TextView)findViewById(R.id.textView6);
        kg=(TextView)findViewById(R.id.textView7);

        day2=(TextView)findViewById(R.id.tx1);
        price2=(TextView)findViewById(R.id.tx2);
        kg2=(TextView)findViewById(R.id.tx3);

        day1=(TextView)findViewById(R.id.t1);
        price1=(TextView)findViewById(R.id.t2);
        kg1=(TextView)findViewById(R.id.t3);

        cname=(TextView)findViewById(R.id.textView3);

        imgl=(ImageView)findViewById(R.id.img1);
        pic=(ImageView)findViewById(R.id.pic);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        img5=(ImageView)findViewById(R.id.img5);
        img6=(ImageView)findViewById(R.id.img6);
        img7=(ImageView)findViewById(R.id.img7);
        img8=(ImageView)findViewById(R.id.img8);
        image1=(ImageView)findViewById(R.id.new_im);

        name=(TextView)findViewById(R.id.id1);
        id2=(TextView)findViewById(R.id.id2);
        id4=(TextView)findViewById(R.id.id4);
        id3=(TextView)findViewById(R.id.id3);
        id5=(TextView)findViewById(R.id.id5);
        id6=(TextView)findViewById(R.id.id6);
        id7=(TextView)findViewById(R.id.id7);
        id8=(TextView)findViewById(R.id.id8);

        fetch pro=new fetch(imgl);
        pro.execute();

        fetch1 pro1=new fetch1(imgl,img2,img3,img4,img5,img6,img7,img8);
        pro1.execute();

        buyfetch g=new buyfetch();
        g.execute();

        buy b=new  buy(pic);
        b.execute();


    }
}
