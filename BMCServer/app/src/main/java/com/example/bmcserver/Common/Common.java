package com.example.bmcserver.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.bmcserver.Model.Request;
import com.example.bmcserver.Model.User;
import com.example.bmcserver.Remote.IGeoCordinates;
import com.example.bmcserver.Remote.RetrofitClient;

public class Common {
    public static User CurrentUser;
    public static Request CurrentRequest;
    public static String UPDATE="Update";
    public static String DELETE="Delete";

    public static final int PICK_IMAGE_REQUEST=1;

    public static final String baseUrl= "https://maps.googleapis.com";

    public static final String ConertCodeToStatus(String code){
        if(code.equals("0"))
        {
            return "Placed";
        }
        else if(code.equals("1"))
        {
            return "On My Way";
        }
        else
        {
            return  "Shipped.";
        }
    }

    public static IGeoCordinates getGeoCodeService()
    {
        return RetrofitClient.getClient(baseUrl).create(IGeoCordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap,int newWidth,int newHeight)
    {
        Bitmap scaleBitmap = Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);
        float scaleX = newWidth/(float)bitmap.getWidth();
        float scaleY = newHeight/(float)bitmap.getHeight();
        float pivotx=0,pivotY=0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotx,pivotY);


        Canvas canvas = new Canvas(scaleBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaleBitmap;
    }
}
