package ua.smartshop.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.smartshop.Adapters.CartAdapter;
import ua.smartshop.Models.Cart;
import ua.smartshop.R;

/**
 * Created by Gens on 29.06.2015.
 */
public class ProductShareFragment extends android.support.v4.app.Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_list, container,
                false);

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), createSensorsList(),
                R.layout.product_share_list_item,
                new String[] {"share"},
                new int[] {R.id.product_share_item_imege});


        ListView lvCart = (ListView) rootView.findViewById(R.id.lvMain);

        lvCart.setAdapter(adapter);

        return rootView;
    }

    private List<Map<String, ?>> createSensorsList()
    {
        Integer[] mPictures = new Integer[]
                {R.drawable.share_smartfon,
                        R.drawable.share_duble_smartfon,
                        R.drawable.share_nout,
                        R.drawable.share_tv,
                        R.drawable.share_tv_b,
                };

        List<Map<String, ?>> items = new ArrayList<Map<String, ?>>();

        for (byte i = 0; i < 5; i++ ){

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("share", mPictures[i]);

            items.add(map);

        }

        return items;
    }

}
