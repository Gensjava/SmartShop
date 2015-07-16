package ua.smartshop.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.github.machinarius.preferencefragment.PreferenceFragment;
import ua.smartshop.R;
import ua.smartshop.Services.StatusService;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 07.03.2015.
 */
public class MainPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences mSettings;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        mSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Регистрируем этот OnSharedPreferenceChangeListener
        Context context = getActivity().getApplicationContext();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mSettings.contains(getActivity().getString(R.string.preferences_status))) {
            boolean status = mSettings.getBoolean(getActivity().getString(R.string.preferences_status), false);
            if (status){
                getActivity().startService(new Intent(getActivity(), StatusService.class));
            } else {
                getActivity().stopService(new Intent(getActivity(), StatusService.class));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Сonstants.mPositionMenu = 5;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {

        if (key.equals(getActivity().getString(R.string.preferences_status))) {

            boolean status = mSettings.getBoolean(getActivity().getString(R.string.preferences_status), false);

            if (status){
                getActivity().startService(new Intent(getActivity(), StatusService.class));
            } else {
                getActivity().stopService(new Intent(getActivity(), StatusService.class));
            }
        }
    }
}
