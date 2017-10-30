package blackriders.assginmentproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ProfileActivityShowFragment extends Fragment {
    TextView txt_name, txt_email, txt_comment;
    RadioGroup radioGrp;
    RadioButton radioMale, radioFemale;
    Button btn_edit;
    String userId;
    SQLitDB sqLitDB;
    ImageView img_back;
    OnButtonClickListener onButtonClickListener;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(getActivity());
        return sqLitDB;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.profile_activity_show, container, false);

        img_back = (ImageView) view.findViewById(R.id.img_back);

        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_email = (TextView) view.findViewById(R.id.txt_email);
        txt_comment = (TextView) view.findViewById(R.id.txt_comment);
        radioGrp = (RadioGroup) view.findViewById(R.id.radioGrp);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);

        radioMale = (RadioButton) view.findViewById(R.id.radioMale);
        radioFemale = (RadioButton) view.findViewById(R.id.radioFemale);

        radioGrp.setEnabled(false);
        radioMale.setEnabled(false);
        radioFemale.setEnabled(false);

        onButtonClickListener = (OnButtonClickListener)getActivity();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClickListener.onEditClick();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClickListener.onBackClick();
            }
        });

        setData();
        return view;
    }

    public void setData() {

        if (getSqLitDB().getUserCount() == 0)
            return;
        userId = "1";
        btn_edit.setVisibility(View.VISIBLE);
        txt_name.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getName());
        txt_email.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getEmail());
        txt_comment.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getComment());

        String gndr = getSqLitDB().getUserByID(Integer.parseInt(userId)).getGender();
        if (gndr.equalsIgnoreCase("male")) {
            radioMale.setChecked(true);
            radioFemale.setChecked(false);
        } else {
            radioMale.setChecked(false);
            radioFemale.setChecked(true);
        }
    }

    interface OnButtonClickListener{
        void onEditClick();
        void onBackClick();
    }

}
