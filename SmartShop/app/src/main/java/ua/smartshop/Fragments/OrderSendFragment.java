package ua.smartshop.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.smartshop.R;

/**
 * Created by Gens on 25.06.2015.
 */
public class OrderSendFragment extends android.support.v4.app.Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View rootView = inflater.inflate(R.layout.order_send, container,
                false);


        return rootView;
    }
}
