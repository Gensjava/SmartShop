package ua.smartshop.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import ua.smartshop.Activity.MainActivity;
import ua.smartshop.R;


public class MainContactFragment extends android.support.v4.app.Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_contact, container,
                false);

       //MainActivity.ui_bar.setVisibility(View.VISIBLE);
        WebView webDescription = (WebView) rootView.findViewById(R.id.contact_web_view);
        WebSettings settings = webDescription.getSettings();
       /// включаем поддержку JavaScript
        settings.setJavaScriptEnabled (true);
        settings.setDefaultTextEncodingName("UTF-8");
        webDescription.loadUrl("file:///android_res/raw/contact.html");

        MainActivity.ui_bar.setVisibility(View.INVISIBLE);

        //((ActionBarActivity)getActivity()).getSupportActionBar().getCustomView().setVisibility(View.INVISIBLE);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.ui_bar.setVisibility(View.INVISIBLE);
    }
}
