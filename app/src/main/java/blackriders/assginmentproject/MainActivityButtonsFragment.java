package blackriders.assginmentproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 10/16/2017.
 */

public class MainActivityButtonsFragment extends Fragment {

    private OnButtonClickListener onButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        View view;
        view = inflater.inflate(R.layout.main_activity_buttons, container, false);

        ImageView logButton = view.findViewById(R.id.img_add);
        ImageView settingButton = view.findViewById(R.id.img_setting);

        onButtonClickListener = (OnButtonClickListener)getActivity();

        logButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClickListener.onLogClick();
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClickListener.onSettingClick();
            }
        });

        return view;
    }

    interface OnButtonClickListener{
        void onLogClick();
        void onSettingClick();
    }
}
