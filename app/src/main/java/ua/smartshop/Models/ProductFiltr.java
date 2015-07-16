package ua.smartshop.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Gens on 17.06.2015.
 */
public class ProductFiltr {

    private String mName;
    private List<String> mSpinner = new ArrayList();
    private boolean mIsSpinner;

    public ProductFiltr(final String name, final List<String> spinner) {
        mName = name;
        mSpinner = spinner;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public List<String> getSpinner() {
        return mSpinner;
    }

    public void setSpinner(final List<String> spinner) {
        mSpinner = spinner;
    }

    public boolean isSpinner() {
        return mIsSpinner;
    }

    public void setIsSpinner(final boolean isSpinner) {
        mIsSpinner = isSpinner;
    }
}
