package ua.smartshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ua.smartshop.Models.Cart;
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 17.06.2015.
 */
public class ProfileAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater lInflater;
    private String[] mMenu;

    private onStartActivityForResultListener startActivityForResult ;
    public final static int REQUEST_GALLERY = 1;
    public final static int CAMERA_RESULT = 0;

    private Integer[] mPictures = new Integer[]
            {R.drawable.ic_launcher_avatar_b,
                    R.drawable.ic_launcher_phone,
                    R.drawable.ic_launcher_email,
                    R.drawable.ic_launcher_address,
                    R.drawable.ic_action_orders,
                    R.drawable.ic_action_cart,
                    R.drawable.ic_action_browsing_history,
                    R.drawable.ic_launcher_massege,
                    R.drawable.ic_action_notification,
                    R.drawable.ic_launcher_heart_desires,
                    R.drawable.ic_action_exit};
    public ProfileAdapter(Context context, String[] menu) {
        mContext = context;
        mMenu = menu;
        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return mMenu.length;
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return mMenu[position];
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        switch (position) {
            case 0:
                convertView = lInflater.inflate(R.layout.profile, parent, false);
                ImageView imageView   =  (ImageView) convertView.findViewById(R.id.profile_avatar);

                if (Сonstants.mImageViewPhoto != null) {
                    imageView.setImageBitmap(Сonstants.mImageViewPhoto);
                }

                View buttonPhoto = convertView.findViewById(R.id.profile_photo);
                buttonPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                        startActivityForResult = (onStartActivityForResultListener) mContext;
                        startActivityForResult.startActivityForResult(CAMERA_RESULT);

                    }
                });

                View buttonGalery = convertView.findViewById(R.id.profile_galery);
                buttonGalery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        startActivityForResult = (onStartActivityForResultListener) mContext;
                        startActivityForResult.startActivityForResult(REQUEST_GALLERY);

                    }
                });
                break;

            default:
                convertView = lInflater.inflate(R.layout.main_menu_header, parent, false);
                TextView textView =  (TextView) convertView.findViewById(R.id.main_menu_header_text);

                if (position  > 4){
                    textView.setText(mMenu[position]);
                } else {
                    textView.setText(dataProfile( position));
                }

                ImageView imageViewPosition =  (ImageView) convertView.findViewById(R.id.main_menu_header_image);
                imageViewPosition.setImageResource(mPictures[position - 1]);

                switch (position) {
                    case 6:
                        TextView textViewCount =  (TextView) convertView.findViewById(R.id.main_menu_header_count);
                        if (Cart.mCart.size() > 0){
                            textViewCount.setText(Integer.toString(Cart.mCart.size()));
                            textViewCount.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 5:
                    case 7:
                    case 9:
                    case 10:
                        textViewCount =  (TextView) convertView.findViewById(R.id.main_menu_header_count);
                        String number = dataProfile(position);

                        if ((number != null )){
                            if (!(number.equals("0") )){
                                textViewCount.setText(number);
                                textViewCount.setVisibility(View.VISIBLE);
                            }
                        }
                        if (position == 10){
                            TextView textViewspace =  (TextView) convertView.findViewById(R.id.main_menu_header_space);
                            textViewspace.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 4:
                        TextView textViewspace =  (TextView) convertView.findViewById(R.id.main_menu_header_space);
                        textViewspace.setVisibility(View.VISIBLE);
                        break;

                    default:
                        break;
                }

                break;
        }

        return convertView;
    }

    private String dataProfile(int position  ){

        String data = null;

        if (Сonstants.mProfile != null) {
            switch (position) {
                case 1:
                    data = Сonstants.mProfile.getSNP();
                    break;
                case 2:
                    data = Сonstants.mProfile.getPhone();
                    break;
                case 3:
                    data = Сonstants.mProfile.getEmail();
                    break;
                case 4:
                    data = Сonstants.mProfile.getDeliveryAddress();
                    break;
                case 5:
                    data = Сonstants.mProfile.getAllOrders();
                    break;
                case 7:
                    data = String.valueOf(Сonstants.mHistoryViewProduct.size());
                    break;
                case 9:
                    data = String.valueOf(Сonstants.mHistoryNotice.size());
                    break;
                case 10:
                    data = String.valueOf(Сonstants.mHistoryMydesires.size());
                    break;
                default:
                    break;
            }
        }
        return data;
    }
    public interface onStartActivityForResultListener {
        public void startActivityForResult(int view_id);

    }

}
