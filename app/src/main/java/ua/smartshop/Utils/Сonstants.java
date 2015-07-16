package ua.smartshop.Utils;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ua.smartshop.Models.CategoryProduct;
import ua.smartshop.Models.Product;
import ua.smartshop.Models.Profile;

/**
 * Created by Gens on 01.03.2015.
 */
 public  class Сonstants {

    // Имена узлов JSON
    public static final String TAG_NAME = "name";
    public static final String TAG_USER_NAME = "username";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_DISCRIPTION = "description";
    public static final String TAG_CONTACT = "contact";
    public static final String TAG_PRODUCT ="product";
    public static final String TAG_PASWWORD = "password";
    public static final String TAG_PHONE = "phone";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ADDRESS = "DeliveryAddress";
    public static final String TAG_ICQ_SKYPE = "icq_skype";
    //
    public static final String VALUE_KEY_ITEM_ID = "idItem";
    public static final String VALUE_KEY_ITEM_NUMBER = "itemnumber";
    public static final String VALUE_COUNT = "count";

    public static CategoryProduct[] categoryProduct = new CategoryProduct[0];
    public static Profile mProfile;
    public static byte mPositionMenu = 1;
    public static Bitmap mImageViewPhoto;

    public static final String APP_PREFERENCES_NAME = "NAME";
    public static final String APP_PREFERENCES_EMAIL = "EMAIL";

    public static Map<String, Float> hashMapProductRating = new HashMap<String, Float>();
    public static Map<String, Boolean> hashMapProductHeart =  new HashMap<String, Boolean>();
    public static HashSet<String> mHistoryViewProduct = new HashSet<String>();
    public static HashSet<String> mHistoryNotice = new HashSet<String>();
    public static HashSet<String> mHistoryMydesires = new HashSet<String>();


    public static Bitmap GetCurveImage(Bitmap bitmap) {
        // Bitmap myCoolBitmap = ... ; // <-- Your bitmap you
        // want rounded

        int w = bitmap.getWidth(), h = bitmap.getHeight();

        // We have to make sure our rounded corners have an
        // alpha channel in most cases
        Bitmap rounder = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rounder);


        // We're going to apply this paint eventually using a
        // porter-duff xfer mode.
        // This will allow us to only overwrite certain pixels.
        // RED is arbitrary. This
        // could be any color that was fully opaque (alpha =
        // 255)
        Paint xferPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xferPaint.setColor(Color.RED);

        // We're just reusing xferPaint to paint a normal
        // looking rounded box, the 20.f
        // is the amount we're rounding by.
        canvas.drawRoundRect(new RectF(0, 0, w, h), 180.0f, 180.0f, xferPaint);

        // Now we apply the 'magic sauce' to the paint
        xferPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        Bitmap result = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas resultCanvas = new Canvas(result);
        resultCanvas.drawBitmap(bitmap, 0, 0, null);
        resultCanvas.drawBitmap(rounder, 0, 0, xferPaint);

        return result;
    }


}
