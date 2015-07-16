package ua.smartshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ua.smartshop.Activity.ProfileActivity;
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
    public static ImageView mImageViewPhoto = null;

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
                mImageViewPhoto =  (ImageView) convertView.findViewById(R.id.profile_avatar);
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

                if (position == 6){
                    TextView textViewCount =  (TextView) convertView.findViewById(R.id.main_menu_header_count);
                    if (Cart.mCart.size() > 0){
                        textViewCount.setText(Integer.toString(Cart.mCart.size()));
                        textViewCount.setVisibility(View.VISIBLE);
                    }
                } else if (position == 4 || position == 9 ){
                    TextView textViewspace =  (TextView) convertView.findViewById(R.id.main_menu_header_space);
                    textViewspace.setVisibility(View.VISIBLE);

                }

                break;
        }
        return convertView;
    }

    private static class ViewHolder {

        public ImageView main_product_new_goods;
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
                default:
                    break;
            }
        }
        return data;
    }
}
