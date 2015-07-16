package ua.smartshop.Adapters;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;

import ua.smartshop.Models.Cart;
import ua.smartshop.R;
import ua.smartshop.Utils.Сonstants;

/**
 * Created by Gens on 17.06.2015.
 */
public class ProfileRegistrtionAdapter extends BaseAdapter {

    public static final String ACTION_OK = "ACTION_OK";
    public static final String ACTION_CANCEL = "ACTION_CANCEL";
    private Context mContext;
    private LayoutInflater lInflater;
    private String[] mMenu;
    private EditText arrEdit [] = new EditText[6];
    private onRegistrtionEventListener registrtionEventListener ;

    private Integer[] mPictures = new Integer[]
            {R.drawable.ic_launcher_avatar_b,
                    R.drawable.ic_launcher_login,
                    R.drawable.ic_launcher_password,
                    R.drawable.ic_launcher_password_duble,
                    R.drawable.ic_launcher_email,
                    R.drawable.ic_launcher_phone,
                    R.drawable.ic_launcher_skype,
                    R.drawable.ic_launcher_address,
                    R.drawable.ic_launcher_address};

    public ProfileRegistrtionAdapter(Context context, String[] menu) {
        mContext = context;
        mMenu = menu;
        lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return mMenu.length;
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return mMenu[position];
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            if (position < 8) {

                convertView = lInflater.inflate(R.layout.profile_registration_list_item, parent, false);

                viewHolder.imageViewPosition =  (ImageView) convertView.findViewById(R.id.profile_registration_list_item_image);
                viewHolder.imageViewPosition.setImageResource(mPictures[position]);

                viewHolder.positionTextView =  (MultiAutoCompleteTextView) convertView.findViewById(R.id.profile_registration_list_item_text);

            }else  if (position == getCount() - 1)  {

                convertView = lInflater.inflate(R.layout.profile_registration_list_panel, parent, false);

                viewHolder.textD =  (TextView) convertView.findViewById(R.id.profile_registration_list_panel_text_d);
                viewHolder.text =  (TextView) convertView.findViewById(R.id.profile_registration_list_panel_text);

                viewHolder.radioNew =  (RadioButton) convertView.findViewById(R.id.profile_registration_list_panel_radio_new);
                viewHolder.radioIn =  (RadioButton) convertView.findViewById(R.id.profile_registration_list_panel_radio_in);

                viewHolder.imageViewCancel =  (ImageView) convertView.findViewById(R.id.profile_registration_list_panel_cancel);
                viewHolder.imageViewOk =  (ImageView) convertView.findViewById(R.id.profile_registration_list_panel_ok);

                viewHolder.imageViewCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        registrtionEventListener = (onRegistrtionEventListener) mContext;
                        registrtionEventListener.onRegistrtionEventListener(ACTION_CANCEL, arrEdit);
                    }
                });

                viewHolder.imageViewOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        registrtionEventListener = (onRegistrtionEventListener) mContext;
                        registrtionEventListener.onRegistrtionEventListener(ACTION_OK, arrEdit);
                    }
                });

            }
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Object item = getItem(position);

        if(item != null){

            if (position < 8) {
                viewHolder.positionTextView.setHint(item.toString());
                viewHolder.positionTextView.setInputType(getTextType(position));

                //список для проверки
                if (position < 6){
                    arrEdit [position] =   viewHolder.positionTextView;
                }
            }else  if (position == getCount() - 1)  {

            }

        }

        return convertView;
    }

    private int getTextType(int s){

        int editorInfo;

        switch (s){
            case 2:
            case 3:
                editorInfo = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
                break;
            case 4:
                editorInfo = EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                break;
            case 5:
                editorInfo = EditorInfo.TYPE_CLASS_PHONE;
                break;
            default:
                editorInfo = EditorInfo.TYPE_CLASS_TEXT;
                break;
        }
        return editorInfo;
    }

    public interface onRegistrtionEventListener {
        public void onRegistrtionEventListener( String s,EditText arrEdit []);
    }

    private static class ViewHolder {

        public ImageView imageViewPosition;
        public MultiAutoCompleteTextView positionTextView;
        public ImageView imageViewCancel;
        public ImageView imageViewOk;
        public TextView textD;
        public TextView text;
        public RadioButton radioNew;
        public RadioButton radioIn;
    }



}
