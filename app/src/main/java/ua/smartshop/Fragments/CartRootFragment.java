package ua.smartshop.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import ua.smartshop.Models.Cart;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 08.07.2015.
 */
public class CartRootFragment extends android.support.v4.app.Fragment {

    public static final String ACTION_ORDER_SEND = "ACTION_ORDER_SEND";
    private byte tabsPage;
    private View nextPageRight;
    private View nextPageLeft;
    private TabHost tabs;
    private onSomeEventListener someEventListener;
    private  View rootView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.cart_activity, container, false);
            tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
            tabs.setup();

            TabHost.TabSpec spec = tabs.newTabSpec(getString(R.string.page_one));

            spec.setContent(R.id.cart_tab1);
            spec.setIndicator(getString(R.string.cart));
            tabs.addTab(spec);

            spec = tabs.newTabSpec(getString(R.string.page_two));
            spec.setContent(R.id.cart_tab2);
            spec.setIndicator(getString(R.string.design));
            tabs.addTab(spec);

            spec = tabs.newTabSpec(getString(R.string.page_three));
            spec.setContent(R.id.cart_tab3);
            spec.setIndicator(getString(R.string.pay));

            tabs.addTab(spec);

            tabs.setCurrentTab(tabsPage);

            nextPageRight = rootView.findViewById(R.id.cart_next_page_right);
            nextPageRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (tabsPage == 2 ){
                        if (Cart.mCart.size() > 0){
                            someEventListener = (onSomeEventListener) getActivity();
                            someEventListener.someEvent(ACTION_ORDER_SEND, null);
                        }   else {
                            Toast.makeText(getActivity(), "Корзина пустая!"
                                    , Toast.LENGTH_LONG).show();
                        }
                    } else {
                        tabs.setCurrentTab(tabsPage += 1);
                        nextVisibility();
                    }
                }

            });

            nextPageLeft =  rootView.findViewById(R.id.cart_next_page_left);
            nextPageLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    tabs.setCurrentTab(tabsPage -= 1);
                    nextVisibility();
                }
            });
            //
            ((TextView) rootView.findViewById(R.id.cart_total_sum)).setText(String.valueOf(Cart.getTotalSum()));

            setTabColor(tabs);
            nextVisibility();

            tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                public void onTabChanged(String tabId) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tabs.getApplicationWindowToken(), 0);

                    tabsPage = (byte) tabs.getCurrentTab();
                    setTabColor(tabs);
                    nextVisibility();
                }
            });


        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }


        return rootView;
    }

    private void nextVisibility(){
        if (tabsPage == 0){
            nextPageLeft.setVisibility(View.INVISIBLE);
            nextPageRight.setVisibility(View.VISIBLE);
        }else if (tabsPage == 1){
            nextPageLeft.setVisibility(View.VISIBLE);
            nextPageRight.setVisibility(View.VISIBLE);
        }else if (tabsPage == 2 ){
            nextPageLeft.setVisibility(View.VISIBLE);
        }
    }

    private void setTabColor(TabHost tabHost) {

        try {
            for (int i=0; i < tabHost.getTabWidget().getChildCount();i++) {
                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                if (tv != null) {
                    tv.setTextColor(Color.parseColor("#FFFFECB3"));
                }
                TextView tv2 = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title);
                if (tv2 != null) {
                    tv2.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        } catch (ClassCastException e) {

        }
    }
    public interface onSomeEventListener {
        public void someEvent(String view_id, String item_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onResume() {
        super.onResume();
        Сonstants.mPositionMenu = 3;
    }
}
