package ua.smartshop.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.HashMap;

import ua.smartshop.Adapters.ProfileAdapter;
import ua.smartshop.Adapters.ProfileRegistrtionAdapter;
import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Models.Profile;
import ua.smartshop.R;
import ua.smartshop.Utils.AsyncWorker;
import ua.smartshop.Utils.ErrorInfo;
import ua.smartshop.Utils.Сonstants;
import ua.smartshop.interfaces.AsyncWorkerInterface;

/**
 * Created by Gens on 30.06.2015.
 */

public class ProfileRegistrationFragment extends android.support.v4.app.Fragment implements  AsyncWorkerInterface {

    private ListView lvMain;
    private  String mScreenTitles [];
    private ProfileRegistrtionAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.main_list, container,
                false);

        mScreenTitles = getResources().getStringArray(R.array.profile_registration_array);
        itemAdapter = new ProfileRegistrtionAdapter(getActivity(), mScreenTitles);

        // настраиваем список
        lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setAdapter(itemAdapter);
        lvMain.setDividerHeight(0);

        return rootView;
    }

    public void doSomethingAsyncOperaion(HashMap paramsUrl,String url, TypeRequest typeRequest) {

        new AsyncWorker(this, paramsUrl, url, typeRequest, getActivity()) {
        }.execute();
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onSuccess(final JSONArray mPJSONArray) {

        Profile profile = new Profile();
        profile.createAccount("Name","Password", getActivity().getBaseContext());

        Profile.mUserName = "Name";
        Profile.mAuthorization = true;

        if (Profile.mAuthorization){
            getActivity().finish();
        } else {
            Log.e(this.getClass().getName(), "Error. Registration");
            Toast.makeText(getActivity(), "Error. Registration"
                    , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(final Throwable t) {

        Log.e(this.getClass().getName(), "Error. Registration");
        Toast.makeText(getActivity(), "Error. Registration"
                , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEnd() {

    }
}
