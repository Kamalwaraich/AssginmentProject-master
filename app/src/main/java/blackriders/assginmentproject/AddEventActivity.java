package blackriders.assginmentproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AddEventActivity extends AppCompatActivity implements AddEventActivitySubmitFragment.OnButtonClickListener{

    private String TAG = "AddEventActivity";
    AddEventActivityDetailFragment addEventActivityDetailFragment;
    AddEventActivitySubmitFragment addEventActivitySubmitFragment;
    AddEventActivityTitleFragment addEventActivityTitleFragment;

    SQLitDB sqLitDB;
    String id;

    double latitude, longitude;
    String picturePath = "", title, place, duration, comment, activity_type, str_date, str_time,
            user_location;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(AddEventActivity.this);
        return sqLitDB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);


        Bundle b = getIntent().getExtras();
        if(b != null)
            id = b.getString("id"); //get activity id


        addEventActivityDetailFragment = new AddEventActivityDetailFragment();
        addEventActivitySubmitFragment = new AddEventActivitySubmitFragment();
        addEventActivityTitleFragment = new AddEventActivityTitleFragment();

        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        addEventActivityTitleFragment.setArguments(bundle);
        addEventActivityDetailFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.fragment_title, addEventActivityTitleFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_detail, addEventActivityDetailFragment, addEventActivityDetailFragment.TAG).commit();
        getFragmentManager().beginTransaction().add(R.id.fragment_submit, addEventActivitySubmitFragment).commit();



    }

     @Override
    public void onSubmitClick()
    {

        if (addEventActivityTitleFragment.isAllFilled() && addEventActivityDetailFragment.isAllFilled()) {

            title = addEventActivityTitleFragment.getTitle();
            picturePath = addEventActivityTitleFragment.getPicturePath();

            activity_type = addEventActivityDetailFragment.getActivity_type();
            str_date = addEventActivityDetailFragment.getStr_date();
            str_time = addEventActivityDetailFragment.getStr_time();
            user_location = addEventActivityDetailFragment.getUser_location();
            place = addEventActivityDetailFragment.getPlace();
            duration = addEventActivityDetailFragment.getDuration();
            comment = addEventActivityDetailFragment.getComment();

            Log.d(TAG, "--------------");

            Log.d(TAG, "Title-->         " + title);
            Log.d(TAG, "Photo Path-->    " + picturePath);
            Log.d(TAG, "Activity Type--> " + activity_type);
            Log.d(TAG, "Date-->          " + str_date);
            Log.d(TAG, "Time-->          " + str_time);
            Log.d(TAG, "Location-->      " + user_location);
            Log.d(TAG, "Place-->         " + place);
            Log.d(TAG, "Duration-->      " + duration);
            Log.d(TAG, "Comment-->       " + comment);

            Log.d(TAG, "--------------");

            String dateTime = str_date.concat(" ,").concat(str_time);

            Events events = new Events(title, activity_type, place, duration, comment,
                    String.valueOf(latitude), String.valueOf(longitude), dateTime, picturePath);

            if (!id.equalsIgnoreCase("0")) {
                Log.d(TAG, "Calling Update");
                getSqLitDB().updateEvents(id, events);
            } else {
                Log.d(TAG, "Calling Add");
                getSqLitDB().addEvent(events);
            }
            finish();
  //          getMainActivity().setList();
  //          getActivity().getSupportFragmentManager().popBackStackImmediate();

        }
    }


    @Override
    public void onCancelClick()
    {
        finish();
    }
}
