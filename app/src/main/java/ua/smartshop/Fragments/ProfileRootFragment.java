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
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 08.07.2015.
 */
public class ProfileRootFragment extends android.support.v4.app.Fragment  {

    private  View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
        rootView = inflater.inflate(R.layout.profile_activity, container,
                false);

        final TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec(getString(R.string.page_one));

        spec.setContent(R.id.profile_tab1);
        spec.setIndicator(getString(R.string.input));
        tabs.addTab(spec);

        spec = tabs.newTabSpec(getString(R.string.page_two));
        spec.setContent(R.id.profile_tab2);
        spec.setIndicator(getString(R.string.registration));
        tabs.addTab(spec);
        tabs.setCurrentTab(0);

        setTabColor(tabs);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tabs.getApplicationWindowToken(), 0);
                setTabColor(tabs);
            }
        });


        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        return rootView;
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
    @Override
    public void onResume() {
        super.onResume();
        Сonstants.mPositionMenu = 2;
    }

}
