package ua.smartshop.Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ua.smartshop.Adapters.ProductFiltrAdapter;
import ua.smartshop.Models.ProductFiltr;
import ua.smartshop.Utils.AsyncWorker;
import ua.smartshop.Adapters.CategoryAdapter;
import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Utils.SerializedPhpParser;
import ua.smartshop.interfaces.AsyncWorkerInterface;
import ua.smartshop.Activity.MainActivity;
import ua.smartshop.Models.CategoryProduct;
import ua.smartshop.R;

public class CategoryProductFragment extends Fragment implements AsyncWorkerInterface {

    private ArrayList<CategoryProduct> mPoducts = new ArrayList<CategoryProduct>();
    private ArrayList<ProductFiltr> mProductFiltr = new ArrayList<ProductFiltr>();
    private CategoryAdapter mCategoryAdapter;
    private String mItem_id;
    private ListView mDrawerListRight;
    private ProductFiltrAdapter productFiltrAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_list, container,
                false);

      productFiltrAdapter = new ProductFiltrAdapter(getActivity(), mProductFiltr);
      mDrawerListRight = (ListView) getActivity().findViewById(R.id.right_drawer);

        mDrawerListRight.setAdapter(productFiltrAdapter);

        if (mPoducts.size() == 0 ){
            Bundle bundle = getArguments();
            if (bundle != null) {
                mItem_id = bundle.getString(MainActivity.KEY_ITEM);
                if(!(mItem_id == null)){
                    doSomethingAsyncOperaion( CategoryProduct.getParamsUrl(mItem_id), getString(R.string.url_get_category_products), TypeRequest.GET);
                }
            }
        }

        mCategoryAdapter = new CategoryAdapter(getActivity(), R.layout.main_list,  mPoducts);

        // настраиваем список
        ListView lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setAdapter(mCategoryAdapter);

        return rootView;
    }

    private void doSomethingAsyncOperaion(HashMap paramsUrl,String url, TypeRequest typeRequest) {

        new AsyncWorker(this, paramsUrl, url, typeRequest, getActivity()) {
        }.execute();
    }

    @Override
    public void onBegin() {
        MainActivity.ui_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(final JSONArray mPJSONArray) {
        try {
            // проходим в цикле через все товары
            CategoryProduct categoryProduct;
            JSONObject json = null;

            for (int i = 0; i < mPJSONArray.length(); i++) {
                categoryProduct =  new Gson().fromJson(mPJSONArray.getJSONObject(i).toString(), CategoryProduct.class);
                mPoducts.add(categoryProduct);

                if(!(categoryProduct.getFiltr().length() == 0)){
                    SerializedPhpParser serializedPhpParser = new SerializedPhpParser(
                            categoryProduct.getFiltr());
                    LinkedHashMap linkedHashMap = (LinkedHashMap)serializedPhpParser.parse();
                    //
                    Iterator<Map.Entry<Integer, LinkedHashMap>> itrOne = linkedHashMap.entrySet().iterator();

                    while (itrOne.hasNext()) {
                        Map.Entry<Integer, LinkedHashMap> entry = itrOne.next();
                        //
                        LinkedHashMap linkedHashMapOne = entry.getValue();
                        List<String> mScreenTitles = new ArrayList<>() ;
                        Iterator<Map.Entry<Integer, HashMap>> itrTwo = linkedHashMapOne.entrySet().iterator();
                        //
                        itrTwo.hasNext();
                        Map.Entry<Integer, HashMap> entry2 = itrTwo.next();
                        ProductFiltr mFiltrProduct = new ProductFiltr(String.valueOf(entry2.getValue()), mScreenTitles);

                        itrTwo.hasNext();
                        entry2 = itrTwo.next();
                        byte n = 0;
                        while (itrTwo.hasNext()) {

                            entry2 = itrTwo.next();
                            n++;
                            if (n == 1 ){
                                mFiltrProduct.setIsSpinner(isSpinner( String.valueOf(entry2.getValue())));
                            }
                            mScreenTitles.add(String.valueOf(entry2.getValue()));
                        }

                        mProductFiltr.add(mFiltrProduct);
                    }

                    List<String> mScreenTitles  = new ArrayList<>();
                    ProductFiltr mFiltrProduct = new ProductFiltr("",  mScreenTitles);
                    mProductFiltr.add(mFiltrProduct);
                    mFiltrProduct = new ProductFiltr("",  mScreenTitles);
                    mProductFiltr.add(mFiltrProduct);
                }
                productFiltrAdapter.notifyDataSetChanged();
            }
            mCategoryAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainActivity.ui_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailure(final Throwable t) {

        if (mPoducts.size() == 0 & getView()!= null){
            ((TextView)  getView().findViewById(R.id.lvMain_text)).setText(getString(R.string.no_data));
        }
        MainActivity.ui_bar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onEnd() {

    }

    private boolean isSpinner(String word){

        return !word.equals("Есть") & !word.equals("Да");
    }

}
