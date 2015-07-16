package ua.smartshop.Adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;
import ua.smartshop.Utils.AsyncWorker;
import ua.smartshop.interfaces.AsyncWorkerInterface;
import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Models.Product;
import ua.smartshop.R;
import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Utils.Сonstants;

public class ProductItemAdapter extends BaseAdapter implements AsyncWorkerInterface {

    private Context mContext;
    private LayoutInflater lInflater;
    private ArrayList<Product> objects;
    private ProductPagerAdapter adapter;
    private ViewPager viewPager;
    private onButtonListener ButtonListener;
    private CirclePageIndicator indicator;
    private ImageView imageViewCart;
    private ImageView imageViewStar;
    private ImageView imageViewHeart;
    private boolean bIcon = true;
    public static final String ACTION_DISRIPTION = "ACTION_DISRIPTION";

    public ProductItemAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        objects = products;
        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        final Product item = (Product) getItem(position);
        if(item != null){

            view = lInflater.inflate(R.layout.product_item, parent, false);

            TextView txtKod = (TextView) view.findViewById(R.id.item_text_kod);
            TextView txtName = (TextView) view.findViewById(R.id.item_text_name_product);
            TextView txtPrice = (TextView) view.findViewById(R.id.item_text_price);
            TextView txtDescription = (TextView) view.findViewById(R.id.item_text_full_discription);
            TextView txtNameB = (TextView) view.findViewById(R.id.product_item_textView);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            //
            txtKod.setText(item.getKod());
            txtName.setText(item.getName());
            txtNameB.setText(item.getName());
            txtPrice.setText(item.getPrice() + mContext.getString(R.string.currency));
            txtDescription.setText(item.getDescription());

            if (Сonstants.hashMapProductRating.size() != 0){
                if (Сonstants.hashMapProductRating.get(item.getKod())!= null){
                    ratingBar.setRating(Сonstants.hashMapProductRating.get(item.getKod()));
                }
            }

            HashMap<String, String> params = new HashMap<String, String>();
            params.put(Сonstants.VALUE_KEY_ITEM_ID,item.getId());

            doSomethingAsyncOperaion(params,  mContext.getString(R.string.url_get_category_product_file_imege) , TypeRequest.GET);

            viewPager = (ViewPager) view.findViewById(R.id.item_pager_product);
            indicator = (CirclePageIndicator) view.findViewById(R.id.indicator_item_titles);

            final Animation animScale = AnimationUtils.loadAnimation(mContext, R.anim.scale_button);

            imageViewCart = (ImageView) view.findViewById(R.id.product_item_image_cart_new);
            imageViewCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ButtonListener = (onButtonListener) mContext;
                    ButtonListener.ButtonOnClick(imageViewCart.getId(), item);
                }
            });

            imageViewStar = (ImageView) view.findViewById(R.id.product_item_image_star);
            imageViewStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ButtonListener = (onButtonListener) mContext;
                    ButtonListener.ButtonOnClick(imageViewStar.getId(), item);
                }
            });

            imageViewHeart = (ImageView) view.findViewById(R.id.product_item_image_heart);

            boolean bIconHert = false;

            if (Сonstants.hashMapProductHeart.size() != 0){
                if (Сonstants.hashMapProductHeart.get(item.getKod())!= null){
                     bIconHert = Сonstants.hashMapProductHeart.get(item.getKod());
                }
            }
            bIcon = !bIconHert;
            if (bIconHert) {
                imageViewHeart.setImageResource(R.drawable.ic_launcher_heart_red);
            } else {
                // возвращаем первую картинку
                imageViewHeart.setImageResource(R.drawable.ic_launcher_heart);

            }

            imageViewHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ButtonListener = (onButtonListener) mContext;
                    ButtonListener.ButtonOnClick(imageViewHeart.getId(), item);
                    v.startAnimation(animScale);

                    if (bIcon) {
                        imageViewHeart.setImageResource(R.drawable.ic_launcher_heart_red);
                        Сonstants.mHistoryMydesires.add(item.getKod());
                        } else {
                        // возвращаем первую картинку
                        imageViewHeart.setImageResource(R.drawable.ic_launcher_heart);
                        Сonstants.mHistoryMydesires.remove(item.getKod());

                    }
                    bIcon = !bIcon;

                    Сonstants.hashMapProductHeart.put(item.getKod(), !bIcon);
                }
            });

        }
        return view;
    }

    private void doSomethingAsyncOperaion(HashMap paramsUrl,String url, TypeRequest typeRequest) {

        new AsyncWorker(this, paramsUrl, url, typeRequest, mContext) {
        }.execute();
    }

    @Override
    public void onBegin() {
        MainActivity.ui_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(final JSONArray mPJSONArray) {
        try {
            Product[] product = new Product[mPJSONArray.length()];
            for (byte i = 0; i < mPJSONArray.length(); i++) {
                product[i] =  new Gson().fromJson(mPJSONArray.getJSONObject(i).toString(), Product.class);
            }
            adapter = new ProductPagerAdapter(mContext, product);

            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            indicator.setViewPager(viewPager);

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.ui_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailure(final Throwable t) {
        MainActivity.ui_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEnd() {

    }

    public interface onButtonListener {
        public void ButtonOnClick(int item, Product product);
    }

}
