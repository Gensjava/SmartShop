package ua.smartshop.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ua.smartshop.R;
import ua.smartshop.Services.StatusService;

public class MainLogoFragment extends android.support.v4.app.Fragment  {

    private SharedPreferences mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_logo, container,
                false);
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //((ActionBarActivity)getActivity()).getSupportActionBar().hide();
        return rootView;
    }

    @Override
    public void onResume() {

        if (mSettings.contains(getActivity().getString(R.string.preferences_status))) {
            boolean status = mSettings.getBoolean(getActivity().getString(R.string.preferences_status), false);
            if (status){
                getActivity().startService(new Intent(getActivity(), StatusService.class));
            } else {
                getActivity().stopService(new Intent(getActivity(), StatusService.class));
            }
        }
        super.onResume();
    }
}
