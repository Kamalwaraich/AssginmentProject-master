package blackriders.assginmentproject;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 10/16/2017.
 */

public class ProfileActivityEditFragment extends Fragment {

    public static String TAG = "ProfileEditFragment";

    EditText ed_name, ed_email, ed_comment;
    RadioGroup radioGrp;
    Button btn_save;

    RadioButton radioMale, radioFemale;
    String getName, getEmail, getCommnets, getGender = "", userId;
    ImageView img_back;

    OnButtonClickListener onButtonClickListener;

    SQLitDB sqLitDB;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(getActivity());
        return sqLitDB;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.profile_activity_edit, container, false);
        onButtonClickListener = (OnButtonClickListener) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
        }


        Log.e(TAG, "User Id: " + userId);

        img_back = (ImageView) view.findViewById(R.id.img_back);

        ed_comment = (EditText) view.findViewById(R.id.ed_comment);
        ed_email = (EditText) view.findViewById(R.id.ed_email);
        ed_name = (EditText) view.findViewById(R.id.ed_name);

        radioGrp = (RadioGroup) view.findViewById(R.id.radioGrp);
        radioMale = (RadioButton) view.findViewById(R.id.radioMale);
        radioFemale = (RadioButton) view.findViewById(R.id.radioFemale);


        btn_save = (Button) view.findViewById(R.id.btn_save);


        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                closeKeyboard();
                RadioButton rb = (RadioButton) view.findViewById(i);
                getGender = rb.getText().toString();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                if (isValid()) {
                    onButtonClickListener.onSaveClick(getName, getEmail, getGender, getCommnets);
                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                onButtonClickListener.onEditBackClick();
            }
        });

        if (!userId.equalsIgnoreCase("0")) {
            ed_name.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getName());
            ed_email.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getEmail());
            ed_comment.setText(getSqLitDB().getUserByID(Integer.parseInt(userId)).getComment());

            String gndr = getSqLitDB().getUserByID(Integer.parseInt(userId)).getGender();
            if (gndr.equalsIgnoreCase("male")) {
                radioMale.setChecked(true);
                radioFemale.setChecked(false);
            } else {
                radioMale.setChecked(false);
                radioFemale.setChecked(true);
            }
        }

    }

    private boolean isValid() {

        getName = ed_name.getText().toString().trim();
        getEmail = ed_email.getText().toString().trim();
        getCommnets = ed_comment.getText().toString().trim();

        if (getName.equalsIgnoreCase("")) {
            ed_name.setError("enter name");
            return false;
        }
        if (getEmail.equalsIgnoreCase("")) {
            ed_email.setError("enter email id");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()) {
            ed_email.setError("Invalid email address!");
            return false;
        }
        if (getGender.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "select gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (getCommnets.equalsIgnoreCase("")) {
            ed_comment.setError("enter comments");
            return false;
        } else
            return true;
    }

    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

        interface OnButtonClickListener
        {
            void onSaveClick(String name, String email, String gender, String comment);
            void onEditBackClick();
        }
    }
