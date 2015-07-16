package ua.smartshop.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Models.Cart;
import ua.smartshop.Models.ProductFiltr;
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 17.06.2015.
 */
public class MainMenuAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater lInflater;
    private String[] mMenu;
    private mainSelectItem mMainSelectItem ;

    private Integer[] mPictures = new Integer[]
            {R.drawable.ic_action_home,
                    R.drawable.ic_launcher_avatar_b,
                    R.drawable.ic_action_cart,
                    R.drawable.ic_action_contact,
                    R.drawable.ic_action_setings,
                    R.drawable.ic_action_exit};
    public MainMenuAdapter(Context context, String[] menu) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;
        final String item = (String) getItem(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();

            switch (position) {
                case 0:
                    convertView = lInflater.inflate(R.layout.main_menu, parent, false);
                    viewHolder.main_menu_text_account =  (TextView) convertView.findViewById(R.id.main_menu_text_account);
                    viewHolder.main_menu_email =  (TextView) convertView.findViewById(R.id.main_menu_email);
                    viewHolder.main_menu_image_account =  (ImageView) convertView.findViewById(R.id.main_menu_image_account);

                    break;
                 default:
                    convertView = lInflater.inflate(R.layout.main_menu_header, parent, false);

                        viewHolder.main_menu_header_space =  (TextView) convertView.findViewById(R.id.main_menu_header_space);
                        viewHolder.main_menu_header_text =  (TextView) convertView.findViewById(R.id.main_menu_header_text);
                        viewHolder.main_menu_header_image =  (ImageView) convertView.findViewById(R.id.main_menu_header_image);
                        viewHolder.main_menu_header_count =  (TextView) convertView.findViewById(R.id.main_menu_header_count);

                    break;
            }
            convertView.setTag(viewHolder);
        }  else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item!= null){
            switch (position) {
                case 0:
                    if (MainActivity.mSettings.contains(Сonstants.APP_PREFERENCES_NAME) & MainActivity.mSettings.contains(Сonstants.APP_PREFERENCES_EMAIL) & Profile.getAllAccount(mContext) ){

                        viewHolder.main_menu_text_account.setText(MainActivity.mSettings.getString(Сonstants.APP_PREFERENCES_NAME, ""));
                        viewHolder.main_menu_email.setText(MainActivity.mSettings.getString(Сonstants.APP_PREFERENCES_EMAIL, ""));

                    } else {
                        viewHolder.main_menu_text_account.setText(viewHolder.main_menu_text_account.getText());
                        viewHolder.main_menu_email.setText(viewHolder.main_menu_email.getText());
                    }

                   if (Сonstants.mImageViewPhoto != null){

                       Bitmap bmHalf = Bitmap.createScaledBitmap(Сonstants.mImageViewPhoto, 80,
                              80, false);
                       Bitmap bitmap = Сonstants.GetCurveImage(bmHalf);
                       viewHolder.main_menu_image_account.setImageBitmap(bitmap);

                   }

                    break;

                default:

                    viewHolder.main_menu_header_text.setText(item.toString());
                    viewHolder.main_menu_header_image.setImageResource(mPictures[position - 1]);

                    if (Сonstants.mPositionMenu == (byte) position){
                        convertView.setBackgroundColor(Color.parseColor("#e6e6e6"));
                    } else {
                        convertView.setBackgroundColor(Color.WHITE);
                    }

                    viewHolder.main_menu_header_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMainSelectItem = (mainSelectItem) mContext;
                            mMainSelectItem.mainSelectItem(position);
                            Сonstants.mPositionMenu = (byte) position;
                        }
                    });

                    if (position == 3){

                        if (Cart.mCart.size() > 0){
                            viewHolder.main_menu_header_count.setText(Integer.toString(Cart.mCart.size()));
                            viewHolder.main_menu_header_count.setVisibility(View.VISIBLE);
                        }else {
                            viewHolder.main_menu_header_count.setVisibility(View.INVISIBLE);
                        }
                    }
                    if (position == 5){
                        viewHolder.main_menu_header_space.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }

        return convertView;
    }
    public interface mainSelectItem {
        public void mainSelectItem(int position);
    }

    private static class ViewHolder {

        public TextView main_menu_header_text;
        public TextView main_menu_header_count;
        public ImageView main_menu_header_image;
        public TextView main_menu_text_account;
        public TextView main_menu_email;
        public TextView main_menu_header_space;
        public ImageView main_menu_image_account;

    }


}
