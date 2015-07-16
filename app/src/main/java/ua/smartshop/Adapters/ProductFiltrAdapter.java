package ua.smartshop.Adapters;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;

import ua.smartshop.Models.ProductFiltr;
import ua.smartshop.R;

/**
 * Created by Gens on 17.06.2015.
 */
public class ProductFiltrAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater lInflater;
    private ArrayList<ProductFiltr> mProductFiltr;

    public ProductFiltrAdapter(Context context, ArrayList<ProductFiltr> products) {
        mContext = context;
        mProductFiltr = products;
        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    // кол-во элементов
    @Override
    public int getCount() {
        return mProductFiltr.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return mProductFiltr.get(position);
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
        final ViewHolder viewHolder;
        final ProductFiltr item = (ProductFiltr) getItem(position);
        final boolean isSpinner = item.isSpinner();
        final int countMone =  mProductFiltr.size() - 2;
        final int count = getCount() - 1;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            if (position == 0) {

                convertView = lInflater.inflate(R.layout.product_filtr_item_seek_bar, parent, false);

                viewHolder.product_seekBar = (DiscreteSeekBar) convertView.findViewById(R.id.product_filtr_seekBar);
                viewHolder.product_filtr_seekBar_textView_end = (TextView) convertView.findViewById(R.id.product_filtr_seekBar_textView_end);
                viewHolder.product_filtr_seekBar_textView_start = (TextView) convertView.findViewById(R.id.product_filtr_seekBar_textView_start);
                viewHolder.product_filtr_seekBar_textView_on = (TextView) convertView.findViewById(R.id.product_filtr_seekBar_textView_on);
                viewHolder.product_filtr_seekBar_switch = (SwitchCompat) convertView.findViewById(R.id.product_filtr_seekBar_switch);

            }else  if(position == countMone) {

                convertView = lInflater.inflate(R.layout.product_filtr, parent, false);
                viewHolder.product_filtr_switch_end = (SwitchCompat) convertView.findViewById(R.id.product_filtr_switch_end);
                viewHolder.product_filtr_switch_end.setVisibility(View.VISIBLE);

            }else  if(position == count) {

                convertView = lInflater.inflate(R.layout.product_filtr, parent, false);
                viewHolder.product_filtr_button_del = (ImageView) convertView.findViewById(R.id.product_filtr_button_del);
                viewHolder.product_filtr_button_del.setVisibility(View.VISIBLE);
                viewHolder.product_filtr_button_run = (ImageView) convertView.findViewById(R.id.product_filtr_button_run);
                viewHolder.product_filtr_button_run.setVisibility(View.VISIBLE);

            }else {

                convertView = lInflater.inflate(R.layout.product_filtr, parent, false);

                viewHolder.product_spinner = (Spinner) convertView.findViewById(R.id.product_filtr_spinner);
                viewHolder.product_checkBox = (CheckBox) convertView.findViewById(R.id.product_filtr_checkBox);
                viewHolder.product_textView = (TextView) convertView.findViewById(R.id.product_filtr_textView);

                if (isSpinner){
                    viewHolder.product_spinner.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.product_checkBox.setVisibility(View.VISIBLE);
                    viewHolder.product_textView.setVisibility(View.VISIBLE);
                }

            }
            convertView.setTag(viewHolder);
        }  else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item!= null){

            if (position == 0) {

               viewHolder.product_seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(final DiscreteSeekBar discreteSeekBar, final int i, final boolean b) {
                        viewHolder.product_filtr_seekBar_textView_start.setText(String.valueOf(i));
                    }

                    @Override
                    public void onStartTrackingTouch(final DiscreteSeekBar discreteSeekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(final DiscreteSeekBar discreteSeekBar) {

                    }
                });
            }else  if(position == countMone) {
                viewHolder.product_filtr_switch_end.setText(item.getName());

            }else  if(position == count) {
                //ignore

            }else {
                viewHolder.product_textView.setText(item.getName());

                if (isSpinner){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.product_filtr_spinner_item, R.id.product_filtr_item_text, item.getSpinner());
                    viewHolder.product_spinner.setAdapter(adapter);
                }
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        public DiscreteSeekBar product_seekBar;
        public Spinner product_spinner;
        public CheckBox product_checkBox;
        public TextView product_filtr_seekBar_textView_end;
        public TextView product_filtr_seekBar_textView_start;
        public TextView product_textView;
        public TextView product_filtr_seekBar_textView_on;
        public SwitchCompat product_filtr_seekBar_switch;
        public SwitchCompat product_filtr_switch_end;
        public ImageView product_filtr_button_del;
        public ImageView product_filtr_button_run;

    }
}
