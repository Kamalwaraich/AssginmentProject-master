package blackriders.assginmentproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Administrator on 10/16/2017.
 */

public class AddEventActivitySubmitFragment extends Fragment {
    Button btn_submit, btn_cancel;
    OnButtonClickListener onButtonClickListener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.add_event_activity_submit, container, false);
        onButtonClickListener = (OnButtonClickListener)getActivity();
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClickListener.onSubmitClick();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonClickListener.onCancelClick();
            }
        });

        return view;
    }
    interface OnButtonClickListener{
        void onSubmitClick();
        void onCancelClick();
    }
}
