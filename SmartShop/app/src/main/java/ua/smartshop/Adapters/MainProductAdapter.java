package ua.smartshop.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Models.Product;
import ua.smartshop.R;

/**
 * Created by Gens on 09.04.2015.
 */
public class MainProductAdapter extends BaseAdapter {

    private Context mContext;
    private onSomeEventListener someEventListener ;
    public static final String ACTION_ITEM = "ACTION_ITEM";//клик на товар imageView
    private Product[] mPoducts;
    private LayoutInflater inflater;
    private int mCount;
    private onButtonListener ButtonListener;
    private boolean bIcon = true;

    public MainProductAdapter(Context c, final Product[] Products,  int count) {
        mContext = c;
        mPoducts = Products;
        mCount = count;
        inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mPoducts.length;
    }

    public Object getItem(int position) {
        return mPoducts[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder viewHolder;
        final Product item = (Product) getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();

            switch (mCount) {
                case 1:
                    convertView = inflater.inflate(R.layout.product_list_item_from_category, parent, false);

                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.product_from_category_photo);
                    viewHolder.priceView = (TextView) convertView.findViewById(R.id.product_from_category_price);
                    viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.product_from_category_age);
                    viewHolder.textViewName = (TextView) convertView.findViewById(R.id.product_from_category_name);
                    viewHolder.main_product_item_by = (ImageView) convertView.findViewById(R.id.product_from_category_im_cart);
                    viewHolder.product_from_category_im_heart = (ImageView) convertView.findViewById(R.id.product_from_category_im_heart);
                    viewHolder.product_from_category_im_star = (ImageView) convertView.findViewById(R.id.product_from_category_im_star);
                    viewHolder.product_from_category_share = (ImageView) convertView.findViewById(R.id.product_from_category_share);
                    viewHolder.product_from_category_new_goods = (ImageView) convertView.findViewById(R.id.product_from_category_new_goods);

                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.product_list_item, parent, false);

                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.main_product_item_imege);
                    viewHolder.priceView = (TextView) convertView.findViewById(R.id.main_product_item_price);
                    viewHolder.textViewName = (TextView) convertView.findViewById(R.id.main_product_item_name);
                    viewHolder.main_product_item_by = (ImageView) convertView.findViewById(R.id.main_product_item_by);
                    viewHolder.main_product_share = (ImageView) convertView.findViewById(R.id.main_product_share);
                    viewHolder.main_product_new_goods = (ImageView) convertView.findViewById(R.id.main_product_new_goods);

                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item != null){

            final String URL = item.getWayImage();

            Picasso.with(mContext)
                    .load(URL)
                    .into(viewHolder.imageView);

            CardView CardView = (CardView) convertView.findViewById(R.id.cv);

            CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    someEventListener = (onSomeEventListener) mContext;
                    someEventListener.someEvent(ACTION_ITEM, String.valueOf(item.getId()));
                }
            });

            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    someEventListener = (onSomeEventListener) mContext;
                    someEventListener.someEvent(ACTION_ITEM, String.valueOf(item.getId()));
                }
            });

            viewHolder.main_product_item_by.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ButtonListener = (onButtonListener) mContext;
                    ButtonListener.ButtonOnClick(viewHolder.main_product_item_by.getId(), item);
                }
            });

            viewHolder.priceView.setText(String.valueOf(mContext.getString(R.string.price) + item.getPrice()+ mContext.getString(R.string.currency)));
            viewHolder.textViewName.setText(item.getName());

            if (mCount == 1){

                viewHolder.textViewDescription.setText(item.getDescription());

                viewHolder.product_from_category_im_star.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ButtonListener = (onButtonListener) mContext;
                        ButtonListener.ButtonOnClick(viewHolder.product_from_category_im_star.getId(), item);
                    }
                });

                viewHolder.product_from_category_im_heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        final Animation animScale = AnimationUtils.loadAnimation(mContext, R.anim.scale_button);
                        ButtonListener = (onButtonListener) mContext;
                        ButtonListener.ButtonOnClick(viewHolder.product_from_category_im_heart.getId(), item);

                        v.startAnimation(animScale);

                        if (bIcon) {
                            viewHolder.product_from_category_im_heart.setImageResource(R.drawable.ic_launcher_heart_red_b);
                        } else {
                            // возвращаем первую картинку
                            viewHolder.product_from_category_im_heart.setImageResource(R.drawable.ic_launcher_heart_grey);
                        }
                        bIcon = !bIcon;
                    }
                });

                if (item.getShare().equals("Y")){
                    viewHolder.product_from_category_share.setVisibility(View.VISIBLE);
                }
                if (item.getNew().equals("Y")){
                    viewHolder.product_from_category_new_goods.setVisibility(View.VISIBLE);
                }

            }else {
                if (item.getShare().equals("Y")){
                    viewHolder.main_product_share.setVisibility(View.VISIBLE);
                }
                if (item.getNew().equals("Y")){
                    viewHolder.main_product_new_goods.setVisibility(View.VISIBLE);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView priceView;
        public TextView textViewName;
        public TextView textViewDescription;
        public ImageView imageView;
        public ImageView main_product_item_by;
        public ImageView product_from_category_im_heart;
        public ImageView product_from_category_im_star;
        public ImageView main_product_share;
        public ImageView product_from_category_share;
        public ImageView product_from_category_new_goods;
        public ImageView main_product_new_goods;
    }

    public interface onSomeEventListener {
        public void someEvent(String view_id, String item_id);
    }
    public interface onButtonListener {
        public void ButtonOnClick(int item, Product product);

    }

}
