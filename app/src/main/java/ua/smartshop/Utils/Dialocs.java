package ua.smartshop.Utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Models.Cart;
import ua.smartshop.Models.Product;
import ua.smartshop.R;

/**
 * Created by Gens on 25.06.2015.
 */
public class Dialocs {

    private static TextView number,sum;
    private static Product mProduct;
    private static onUpDataCartListener  mUpDataCartListener;
    private static onUpDataItemProductListener  mUpDataItemProductListener;
    private static RatingBar ratingBar;

    //Создаем открываем диалог
    public static void  showCustomAlertDialogEnterNumber(final String title, final Context context, final Product product) {

        //получаем Inflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View numberView;

        mProduct = product;

        if (mProduct == null){
            return;
        }
        if (title == MainActivity.TITLE_DIALOG_ADD_CART){
            //каст макет
            numberView = layoutInflater.inflate(R.layout.dialog_number, null);
            //поля из макета
            final ImageView numberMinus =  (ImageView) numberView.findViewById(R.id.dialog_number_minus);
            final ImageView numberPlus =  (ImageView) numberView.findViewById(R.id.dialog_number_plus);
            final TextView price =  (TextView) numberView.findViewById(R.id.dialog_number_price);
            number =  (TextView) numberView.findViewById(R.id.dialog_number_number);
            sum =  (TextView) numberView.findViewById(R.id.dialog_number_sum);

            price.setText(String.valueOf(mProduct.getPrice()));
            sum.setText(String.valueOf(mProduct.getPrice()));

            final double[] numberD = {1.0};
            final Animation animScale = AnimationUtils.loadAnimation(context, R.anim.scale_button);


            numberPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    numberD[0]++;
                    number.setText(String.valueOf(numberD[0]));
                    sum.setText(String.valueOf(mProduct.getPrice()* numberD[0]));
                    v.startAnimation(animScale);
                }
            });

            numberMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( numberD[0] > 1){
                        numberD[0]--;
                        number.setText(String.valueOf(numberD[0]));
                        sum.setText(String.valueOf(mProduct.getPrice()* numberD[0]));
                        v.startAnimation(animScale);
                    }
                }
            });
        } else
        {
            //каст макет
            numberView = layoutInflater.inflate(R.layout.product_ratingdialog, null);
            ratingBar = (RatingBar) numberView.findViewById(R.id.ratingbar);

            if (Сonstants.hashMapProductRating.size() != 0){
                if (Сonstants.hashMapProductRating.get(mProduct.getKod())!= null){
                    ratingBar.setRating(Сonstants.hashMapProductRating.get(mProduct.getKod()));
                }
            }
        }


        //открываем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(numberView);
        //кнопки
        builder .setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int id) {

                        if (title == MainActivity.TITLE_DIALOG_ADD_CART) {

                            double numberInDialog = Double.parseDouble(String.valueOf(number.getText()));
                            //заказ
                            Cart cart = new Cart(mProduct, String.valueOf(getDate()), mProduct.getPrice(), numberInDialog, mProduct.getPrice() * numberInDialog);
                            Cart.setmCart(cart);
                            dialog.cancel();
                            mUpDataCartListener = (onUpDataCartListener) context;
                            mUpDataCartListener.UpDataCart();

                        } else {

                            Сonstants.hashMapProductRating.put(mProduct.getKod(), ratingBar.getRating());

                            mUpDataItemProductListener = (onUpDataItemProductListener) context;
                            mUpDataItemProductListener.UpDataItemProduct(ratingBar.getRating());
                        }

                    }
                })
                .setNegativeButton("Отмена", null);

        final AlertDialog alert = builder.create();
        alert.show();

    }
    // Получаем текущее дату системы
    // Возвращаем дату "текущюю дату"
    private static Date getDate() {
        //текущая дата
        long curTime = System.currentTimeMillis();
        Date date = new Date(curTime);
        return date;
    }

    public interface onUpDataCartListener {
        public  void UpDataCart();
    }
    public interface onUpDataItemProductListener {
        public  void UpDataItemProduct(final float rating);
    }
}
