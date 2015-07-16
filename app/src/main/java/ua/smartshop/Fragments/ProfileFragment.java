package ua.smartshop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.HashMap;

import ua.smartshop.Adapters.ProfileAdapter;
import ua.smartshop.BuildConfig;
import ua.smartshop.Utils.AsyncWorker;
import ua.smartshop.Utils.Сonstants;
import ua.smartshop.interfaces.AsyncWorkerInterface;
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Enums.TypeRequest;

/**
 * Created by Gens on 10.03.2015.
 */
public class ProfileFragment extends android.support.v4.app.Fragment implements AsyncWorkerInterface {

    private ListView lvMain;
    private  String mScreenTitles [];
    private ProfileAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_list, container,
                false);

        doSomethingAsyncOperaion( Profile.getParamsuserNameUrl(Profile.mUserName) , getString(R.string.url_get_user),  TypeRequest.POST);

        mScreenTitles = getResources().getStringArray(R.array.profile_array_name);
        itemAdapter = new ProfileAdapter(getActivity(), mScreenTitles);

        // настраиваем список
        lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setAdapter(itemAdapter);
        lvMain.setDividerHeight(0);

        return rootView;
    }

    private void doSomethingAsyncOperaion(HashMap paramsUrl,String url, TypeRequest typeRequest) {

        new AsyncWorker(this, paramsUrl, url, typeRequest, getActivity()) {
        }.execute();
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onSuccess(final JSONArray mPJSONArray) {
        try {

            for (int i = 0; i < mPJSONArray.length(); i++) {
                Сonstants.mProfile =  new Gson().fromJson(mPJSONArray.getJSONObject(i).toString(), Profile.class);
            }
            itemAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(final Throwable t) {

    }

    @Override
    public void onEnd() {

    }

    public void upDataProfileAdapter(){
        itemAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        Сonstants.mPositionMenu = 2;
    }

}
