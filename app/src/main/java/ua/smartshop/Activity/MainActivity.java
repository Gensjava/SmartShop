package ua.smartshop.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import ua.smartshop.Adapters.CartAdapter;
import ua.smartshop.Adapters.CategoryAdapter;
import ua.smartshop.Adapters.MainAdapter;
import ua.smartshop.Adapters.MainMenuAdapter;
import ua.smartshop.Adapters.MainProductAdapter;
import ua.smartshop.Adapters.MainPagerAdapter;
import ua.smartshop.Adapters.ProductItemAdapter;
import ua.smartshop.Adapters.ProfileAdapter;
import ua.smartshop.Adapters.ProfileRegistrtionAdapter;
import ua.smartshop.Enums.TypeRequest;
import ua.smartshop.Fragments.CartRootFragment;
import ua.smartshop.Fragments.OrderSendFragment;
import ua.smartshop.Fragments.ProductShareFragment;
import ua.smartshop.Fragments.ProfileFragment;
import ua.smartshop.Fragments.ProfileRegistrationFragment;
import ua.smartshop.Fragments.ProfileRootFragment;
import ua.smartshop.Fragments.SearchProductFragment;
import ua.smartshop.Models.Product;
import ua.smartshop.Services.StatusService;
import ua.smartshop.Utils.AsyncWorker;
import ua.smartshop.Fragments.CartFragment;
import ua.smartshop.Fragments.CategoryProductFragment;
import ua.smartshop.Fragments.CategoryProductRootFragment;
import ua.smartshop.Fragments.ErrorFragment;
import ua.smartshop.Fragments.MainContactFragment;
import ua.smartshop.Fragments.MainLogoFragment;
import ua.smartshop.Fragments.MainPreferenceFragment;
import ua.smartshop.Fragments.ProductDiscriptionFragment;
import ua.smartshop.Fragments.ProductItemRootFragment;
import ua.smartshop.Fragments.MainFragment;
import ua.smartshop.Models.Cart;
import ua.smartshop.Models.Profile;
import ua.smartshop.Fragments.ProductFragment;
import ua.smartshop.R;
import ua.smartshop.Utils.Dialocs;
import ua.smartshop.Utils.ErrorInfo;
import ua.smartshop.Utils.Сonstants;


public class MainActivity extends ActionBarActivity implements

        MainAdapter.onSomeEventListener,
        MainPagerAdapter.onSomeEventListener,
        View.OnClickListener,
        CategoryAdapter.onSomeEventListener,
        AdapterView.OnItemSelectedListener,
        Dialocs.onUpDataCartListener,
        Dialocs.onUpDataItemProductListener,
        AsyncWorker.onSomeEventListener,
        MainProductAdapter.onSomeEventListener,
        MainMenuAdapter.mainSelectItem,
        ProductItemAdapter.onButtonListener,
        MainProductAdapter.onButtonListener,
        ProfileAdapter.onStartActivityForResultListener,
        ProfileRegistrtionAdapter.onRegistrtionEventListener,
        CartRootFragment.onSomeEventListener,
        CartAdapter.onVaultEventListener

{

    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String COUNT_GOODS = "COUNT_GOODS";
    public static final String TITLE_DIALOG_ADD_CART = "Добавить в корзину";
    public static final String TITLE_DIALOG_SET_REITING = "Установить рейтинг";
    private static final String ACTION_SEARCH = "ACTION_SEARCH";
    public static final String URL_KEY = "URL_KEY";
    private  final int count_one = 1 ;
    private  final int count_two = 2 ;
    public static View ui_bar;

    private String[] mScreenTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private boolean openMain;
    private MainMenuAdapter mMainMenuAdapter;
    //
    private Fragment fragment;
    private android.support.v4.app.FragmentTransaction ft;
    public static TextView ui_hot = null;
    public static ImageView ui_cart = null;
    private boolean openDrawer;
    public static SharedPreferences mSettings;
    private static final String APP_PREFERENCES = "mysettings";

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mTitle = mDrawerTitle = getTitle();
        mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //
        mMainMenuAdapter = new MainMenuAdapter(this, mScreenTitles);
        mDrawerList.setAdapter(mMainMenuAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        String serviseStatus = getIntent().getStringExtra(StatusService.ACTION_SERVICE_STATUS);

        if (serviseStatus != null){
            selectItem(2);
        } else {
            if (!openMain){
                onOpenFragment(new MainLogoFragment());

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        openMain = true;
                        onOpenFragment(new MainFragment());
                        Сonstants.mPositionMenu = 1;
                    }
                }, 5000); //
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description */
                R.string.drawer_close /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); //
                openDrawer = false;
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); //
                //обновляем корзину
                mMainMenuAdapter.notifyDataSetChanged();
                openDrawer = true;
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hotlist_bell:
                selectItem(3);
                Сonstants.mPositionMenu = 3;
                break;
            case R.id.lvMain_up:

                MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(MainFragment.class.toString());
                ProductFragment productFragment = (ProductFragment) getSupportFragmentManager().findFragmentByTag(ProductFragment.class.toString());
                SearchProductFragment searchProductFragment = (SearchProductFragment) getSupportFragmentManager().findFragmentByTag(SearchProductFragment.class.toString());

                if (checkFragment(mainFragment)){
                    mainFragment.onListScrolPosition(0);
                }

                if (checkFragment(productFragment)){
                    productFragment.onListScrolPosition(0);
                }

                if (checkFragment(searchProductFragment)){
                    searchProductFragment.onListScrolPosition(0);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {

    }

    @Override
    public void onNothingSelected(final AdapterView<?> parent) {

    }

    @Override
    public void UpDataCart() {
        updateHotCount();
    }

    @Override
    public void mainSelectItem(final int position) {
        selectItem(position);
    }

    @Override
    public void ButtonOnClick(final int item, Product product) {
        switch (item) {
            case R.id.product_item_image_cart_new:
            case R.id. main_product_item_by:
            case R.id. product_from_category_im_cart:

                if (!Profile.getAllAccount(this)){
                    fragment = new ProfileRootFragment();
                } else {
                    Dialocs.showCustomAlertDialogEnterNumber(TITLE_DIALOG_ADD_CART, this, product);
                }

                break;
            case R.id.product_item_image_star:
            case R.id.product_from_category_im_star:

                if (!Profile.getAllAccount(this)){
                    fragment = new ProfileRootFragment();
                } else {
                    Dialocs.showCustomAlertDialogEnterNumber(TITLE_DIALOG_SET_REITING, this, product);
                }

                break;
            default:
                break;
        }
        if (fragment != null & !Profile.mAuthorization) {
            onOpenFragment(fragment);
        }
    }

    @Override
    public void startActivityForResult(final int view_id) {
        switch (view_id){
            case ProfileAdapter.CAMERA_RESULT:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, ProfileAdapter.CAMERA_RESULT);
                break;
            case ProfileAdapter.REQUEST_GALLERY:
                Intent galeryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galeryIntent, ProfileAdapter.REQUEST_GALLERY);
            default:
                break;
        }

    }

    @Override
    public void someVaultEvent(final String view_id) {
        switch (view_id){
           case CartAdapter.TEG_GART_TOTAL:
           case CartFragment.TEG_GART_TOTAL_FRAGMENT:

               updateHotCount();

               CartRootFragment cartRootFragment = (CartRootFragment) getSupportFragmentManager().findFragmentByTag(CartRootFragment.class.toString());

                ((TextView) cartRootFragment.getView().findViewById(R.id.cart_total_sum)).setText(String.valueOf(Cart.getTotalSum()));

                if (Cart.getmCart().size() == 0 ){
                    ((TextView)  cartRootFragment.getView().findViewById(R.id.lvMain_text)).setText(getString(R.string.cart_in_empty));
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void UpDataItemProduct(final float rating) {

        ProductItemRootFragment producttItemRootFragment = (ProductItemRootFragment) getSupportFragmentManager().findFragmentByTag(ProductItemRootFragment.class.toString());
        if (checkFragment(producttItemRootFragment)){
            RatingBar ratingBar  = (RatingBar) producttItemRootFragment.getView().findViewById(R.id.ratingBar);
            ratingBar.setRating(rating);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        fragment = null;

        switch (position) {
            case 0:
            case 2:
                if (!Profile.getAllAccount(this)){
                    fragment = new ProfileRootFragment();
                } else {
                    fragment = new ProfileFragment();
                }
                break;
            case 1:
                fragment = new MainFragment();
                break;
            case 3:
                //проверка регистрации
                if (!Profile.getAllAccount(this)){
                    fragment = new ProfileRootFragment();
                } else {
                    fragment = new CartRootFragment();
                }
                break;
            case 4:
                fragment = new MainContactFragment();
                break;
            case 5:
                fragment = new MainPreferenceFragment();
                break;
            case 6:
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            onOpenFragment(fragment);

            mDrawerList.setItemChecked(position, true);
            setTitle(mScreenTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e(this.getClass().getName(), "Error. object is not created");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        mMainMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final MenuItem customBar = menu.add(0, R.id.menu_bar, 0,"");
        customBar.setActionView(R.layout.progress_bar);
        customBar.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        View menu_hotlistBar = menu.findItem(R.id.menu_bar).getActionView();
        ui_bar = (View) menu_hotlistBar.findViewById(R.id.menu_bar);

        //
        final MenuItem custom = menu.add(0, R.id.menu_search, 0,"");
        custom.setActionView(R.layout.main_action_bar_search);
        custom.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        final View actionView = menuItem.getActionView();

        final SearchView ButtonSearch = (SearchView) actionView.findViewById(R.id.searchView);
        //меняем значок поиска
        int searchIconId = ButtonSearch.getContext().getResources().
                getIdentifier("android:id/search_button", null, null);
        ImageView searchIcon = (ImageView) ButtonSearch.findViewById(searchIconId);
        searchIcon.setImageResource(R.drawable.abc_ic_search_api_mtrl_alpha);

        ButtonSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                SearchProductFragment searchProductFragment = (SearchProductFragment) getSupportFragmentManager().findFragmentByTag(SearchProductFragment.class.toString());

                if (searchProductFragment == null) {
                    someEvent(ACTION_SEARCH, s);
                }else {
                    searchProductFragment.newInstance(getString(R.string.url_get_search_products), s, count_one);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(final String s) {
                return false;
            }
        });

        //////////////////////////////////////////////////////////////////////////////////

        final MenuItem customCart = menu.add(0, R.id.menu_hotlist, 0,"");
        customCart.setActionView(R.layout.main_action_bar_cart);
        customCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        final View menu_hotlist = menu.findItem(R.id.menu_hotlist).getActionView();

        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        ui_cart = (ImageView) menu_hotlist.findViewById(R.id.hotlist_bell);

        updateHotCount();

        final MenuItem menuItemCart = menu.findItem(R.id.menu_hotlist);
        final View actionViewCart = menuItemCart.getActionView();

        final View ButtonCart = actionViewCart.findViewById(R.id.hotlist_bell);
        ButtonCart.setOnClickListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //boolean drawerOpen =  (Сonstants.mPositionMenu == 3);
        //menu.findItem(R.id.menu_hotlist).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_bar).setVisible(Сonstants.mPositionMenu == 1 & !openDrawer);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //обновляем корзину
        mMainMenuAdapter.notifyDataSetChanged();

        switch (item.getItemId())
        {
            case android.R.id.home:
                //При нажатии вызывается обработчик кнопки назад
                //Он по умолчанию должен будет вернуться по списку фрагментов назад
                onBackPressed();
                return true;
        }
        return true;
    }
    @Override
    public void someEvent(final String key, final String value) {

        Bundle bundleItem = new Bundle();
        bundleItem.putString(KEY_ITEM,  value);

        switch (key) {// обрабатывам клик на товар
            case MainProductAdapter.ACTION_ITEM:

                fragment = new ProductItemRootFragment();
                break;
            case ProductItemAdapter.ACTION_DISRIPTION:// просмотр хар-к товара

                fragment = new ProductDiscriptionFragment();
                break;
            case MainAdapter.ACTION_CATEGORY_ALL:// открываем корень категорий

                fragment = new CategoryProductRootFragment();
                break;
            ///case MainPagerAdapter.ACTION_ONCLIK_ITEM_PEGER_ADAPTER://банер

               // fragment = new ProductFragment();
               // bundleItem.putString(URL_KEY, getString(R.string.url_get_slider_main_page_category));
               // bundleItem.putInt(COUNT_GOODS, count_two);
               // break;
            case ACTION_SEARCH://поиск потовару
            case MainPagerAdapter.ACTION_ONCLIK_ITEM_PEGER_ADAPTER://банер

                fragment = new SearchProductFragment();

                bundleItem.putString(URL_KEY, getString(R.string.url_get_search_products));
                bundleItem.putInt(COUNT_GOODS, count_one);
                break;
            case CategoryAdapter.ACTION_FROM_CATEGORY_PRODUCT:

                fragment = new ProductFragment();
                bundleItem.putString(URL_KEY, getString(R.string.url_get_cproducts_from_category));
                bundleItem.putInt(COUNT_GOODS, count_one);
                break;
            case CategoryAdapter.ACTION_ONCLIK_ITEM_CATEGORY_ADAPTER://категория товаров
            case MainAdapter.ACTION_ONCLIK_ITEM_CATEGORY_ADAPTER_MAIN:

                fragment = new CategoryProductFragment();
                break;
            case AsyncWorker.ERROR_JSON:
                fragment = new ErrorFragment();
                break;
            case MainAdapter.ACTION_SHARES:
                fragment = new ProductShareFragment();
                break;
            case CartRootFragment.ACTION_ORDER_SEND:
                fragment = new OrderSendFragment();
                Cart.mCart.clear();
                updateHotCount();
                break;
            default:
                break;
        }
        if (fragment!= null ) {
            fragment.setArguments(bundleItem);
            onOpenFragment(fragment);
            //делаем стрелу вместо меню
            if(!fragment.getClass().equals(ErrorFragment.class)
             & !fragment.getClass().equals(ProfileFragment.class)
             & !fragment.getClass().equals(OrderSendFragment.class) ){
                mDrawerToggle.setDrawerIndicatorEnabled(false);
            }
        }
    }
    void  onOpenFragment(Fragment fragment) {

        if (fragment!= null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            ft = fragmentManager.beginTransaction();

            if(fragment.getClass().equals(MainLogoFragment.class) || openMain){
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
                openMain = false;
            } else {
                ft.addToBackStack(fragment.getClass().toString());
            }
            ft.replace(R.id.content_frame, fragment, fragment.getClass().toString());
            ft.commit();
        } else {
            // Error
            Log.e(this.getClass().getName(), "Error. Fragment is not created");
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static  void updateHotCount() {

        if (ui_hot == null) return;

        if (Cart.mCart.size() == 0) {
            ui_hot.setVisibility(View.INVISIBLE);
        } else {
            ui_hot.setVisibility(View.VISIBLE);
            ui_hot.setText(Integer.toString(Cart.mCart.size()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        updateHotCount();
        mMainMenuAdapter.notifyDataSetChanged();

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(MainFragment.class.toString());
        OrderSendFragment orderSendFragment = (OrderSendFragment) getSupportFragmentManager().findFragmentByTag(OrderSendFragment.class.toString());

        if (checkFragment(mainFragment)){
            mDrawerToggle.setDrawerIndicatorEnabled(true); //меняемс стрелку в меню
        } else if(checkFragment(orderSendFragment)){
            onOpenFragment(mainFragment);
        }
        setTitle(mScreenTitles[Сonstants.mPositionMenu]);
        mDrawerList.setItemChecked(Сonstants.mPositionMenu, true);
    }

    private boolean checkFragment(Fragment fragment){
        boolean checkMain = false;

        if (fragment != null){
            checkMain = fragment.isVisible();
        }
        return checkMain;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap galleryBitmap = null;

        if (resultCode == RESULT_OK) {

            ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.toString());

            switch (requestCode) {
                case ProfileAdapter.CAMERA_RESULT:
                    galleryBitmap = (Bitmap) data.getExtras().get("data");
                    if (galleryBitmap != null){
                        Сonstants.mImageViewPhoto = galleryBitmap;
                        profileFragment.upDataProfileAdapter();
                    }
                    mMainMenuAdapter.notifyDataSetChanged();
                    break;

                case ProfileAdapter.REQUEST_GALLERY:

                    Uri selectedImageUri = data.getData();
                    try {
                        galleryBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                                selectedImageUri);
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }

                    Сonstants.mImageViewPhoto = galleryBitmap;
                    if (profileFragment != null){
                        profileFragment.upDataProfileAdapter();
                    }
                    mMainMenuAdapter.notifyDataSetChanged();
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
               onBackPressed();
                break;
        }

    }
}