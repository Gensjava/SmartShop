package ua.smartshop.Adapters;

import android.content.Context;
import android.graphics.Color;
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

import ua.smartshop.Models.Cart;
import ua.smartshop.Models.ProductFiltr;
import ua.smartshop.R;

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

        convertView = lInflater.inflate(R.layout.main_menu, parent, false);

        switch (position) {
            case 0:

                ImageView imageViewHeadr =  (ImageView) convertView.findViewById(R.id.main_menu_image_header);
                imageViewHeadr = ProfileAdapter.mImageViewPhoto;
                break;
            default:
                convertView = lInflater.inflate(R.layout.main_menu_header, parent, false);
                TextView textView =  (TextView) convertView.findViewById(R.id.main_menu_header_text);
                textView.setText(mMenu[position]);


                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMainSelectItem = (mainSelectItem) mContext;
                        mMainSelectItem.mainSelectItem(position);
                    }
                });

                ImageView imageViewPosition =  (ImageView) convertView.findViewById(R.id.main_menu_header_image);
                imageViewPosition.setImageResource(mPictures[position - 1]);

                if (position == 2){
                    TextView textViewCount =  (TextView) convertView.findViewById(R.id.main_menu_header_count);
                    if (Cart.mCart.size() > 0){
                        textViewCount.setText(Integer.toString(Cart.mCart.size()));
                        textViewCount.setVisibility(View.VISIBLE);
                    }
                }
                break;

        }
        return convertView;
    }
    public interface mainSelectItem {
        public void mainSelectItem(int position);
    }

}
