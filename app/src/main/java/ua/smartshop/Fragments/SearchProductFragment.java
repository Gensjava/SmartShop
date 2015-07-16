package ua.smartshop.Fragments;



import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Models.Product;

/**
 * Created by Gens on 14.06.2015.
 */
public class SearchProductFragment extends ProductFragment {

    public  void newInstance(String url,String key_item, int count ) {
        setItemNumber(1);

        if (!getItem_id().equals(key_item)) {
            setItem_id(key_item);
            getPoducts().clear();
        }

        doSomethingAsyncOperaion(Product.getParamsUrlNumberItem( getItemNumber(), getItem_id(), count), url,  TypeRequest.GET);

    }

}
