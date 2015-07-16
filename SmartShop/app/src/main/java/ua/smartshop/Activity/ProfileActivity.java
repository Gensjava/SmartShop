package ua.smartshop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

import ua.smartshop.Adapters.ProfileAdapter;
import ua.smartshop.Adapters.ProfileRegistrtionAdapter;
import ua.smartshop.BuildConfig;
import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Fragments.ProfileRegistrationFragment;
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Utils.ErrorInfo;
import ua.smartshop.Utils.Сonstants;

public class ProfileActivity extends ActionBarActivity implements View.OnClickListener, ProfileRegistrtionAdapter.onRegistrtionEventListener{

    public final static String ACCOUNT_TYPE = BuildConfig.APPLICATION_ID;
    public final static String AUTH_TYPE = "AUTH_TYPE";
    public final static String IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";
    public final int REQUEST_GALLERY = 1;
    private final int CAMERA_RESULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //делаем стрелу вместо меню
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(!Profile.mAuthorization){

            setContentView(R.layout.profile_activity);

            final TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
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
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(tabs.getApplicationWindowToken(), 0);
                    setTabColor(tabs);
                }
            });

        } else {

            setContentView(R.layout.profile_activity_cabinet);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                //Надо вернуть иконку
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()){
            case  R.id.profile_photo:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_RESULT);
                break;

            case  R.id.profile_galery:

                Intent galeryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent, REQUEST_GALLERY);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap galleryBitmap = null;

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CAMERA_RESULT:
                    galleryBitmap = (Bitmap) data.getExtras().get("data");
                    if (galleryBitmap != null){
                        ProfileAdapter.mImageViewPhoto.setImageBitmap(galleryBitmap);
                    }
                    break;

                case REQUEST_GALLERY:

                    Uri selectedImageUri = data.getData();
                    try {
                        galleryBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                selectedImageUri);
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }

                    ProfileAdapter.mImageViewPhoto.setImageBitmap(galleryBitmap);
                    break;
                default:
                    break;

            }
        }

    }

    @Override
    public void onRegistrtionEventListener(final String s, final EditText[] arrEdit) {

        switch (s){
            case ProfileRegistrtionAdapter.ACTION_OK:

                if (!ErrorInfo.fieldValidationRegistration(arrEdit)){

                    HashMap<String, String> params = new HashMap<String, String>();
                    //
                    params.put(Сonstants.TAG_NAME, arrEdit[0].getText().toString());
                    params.put(Сonstants.TAG_USER_NAME, arrEdit[1].getText().toString());
                    params.put(Сonstants.TAG_PASWWORD, arrEdit[2].getText().toString());
                    params.put(Сonstants.TAG_EMAIL, arrEdit[3].getText().toString());
                    params.put(Сonstants.TAG_PHONE, arrEdit[4].getText().toString());
                    //params.put(Сonstants.TAG_ICQ_SKYPE, arrEdit[5].getText().toString());
                    //params.put(Сonstants.TAG_ADDRESS, arrEdit[6].getText().toString());

                    ProfileRegistrationFragment profileRegistrationFragmentFragment = (ProfileRegistrationFragment) getSupportFragmentManager().findFragmentByTag(ProfileRegistrationFragment.class.toString());
                    if (profileRegistrationFragmentFragment != null){
                        profileRegistrationFragmentFragment.doSomethingAsyncOperaion(params ,  getString(R.string.url_set_user_registration),  TypeRequest.POST);
                    }

                }
                break;
            default:
                finish();
                break;
        }

    }
}


