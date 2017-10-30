package blackriders.assginmentproject;

import android.*;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Administrator on 10/16/2017.
 */

public class AddEventActivityDetailFragment extends Fragment {

    public static String TAG = "AddEventDetailFragment";

    EditText edit_place, edit_duration, edit_comment;
    Spinner spin_activity;
    static Button btn_date, btn_time;
    Button btn_get_location;
    TextView txt_lat_lng;
    List<String> activityList = new ArrayList<>();
    LocationManager locationManager;

    double latitude, longitude;
    String place, duration, comment, activity_type, str_date, str_time,
            user_location;
    String id;
    SQLitDB sqLitDB;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(getActivity());
        return sqLitDB;
    }


    public String getStr_date() {
        return str_date;
    }

    public void setStr_date(String str_date) {
        this.str_date = str_date;
    }

    public String getStr_time() {
        return str_time;
    }

    public void setStr_time(String str_time) {
        this.str_time = str_time;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getActivity_type() {

        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public double getLatitude() {

        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    int hrs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.add_event_activity_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_place = (EditText) view.findViewById(R.id.edit_place);
        edit_duration = (EditText) view.findViewById(R.id.edit_duration);
        edit_comment = (EditText) view.findViewById(R.id.edit_comment);
          /*Spinner*/
        spin_activity = (Spinner) view.findViewById(R.id.spin_activity);

        /*Buttons*/
        btn_date = (Button) view.findViewById(R.id.btn_date);
        btn_time = (Button) view.findViewById(R.id.btn_time);
        btn_get_location = (Button) view.findViewById(R.id.btn_get_location);

           /*Tetxview*/
        txt_lat_lng = (TextView) view.findViewById(R.id.txt_lat_lng);

        activityList.add(0, "Select Activity Type");
        activityList.add(1, "Work");
        activityList.add(2, "Study");
        activityList.add(3, "Leisure");
        activityList.add(4, "Sport");

        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                activityList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_activity.setAdapter(aa);

        checkLocationIsEnable();


        spin_activity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                closeKeyboard();
                if (i != 0) {
                    activity_type = activityList.get(i).trim();
                } else {
                    activity_type = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                getCurrentLocation();
            }
        });


        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                DialogFragment newFragment = new datePicker();
                newFragment.show(getActivity().getSupportFragmentManager() , "datePicker");
            }
        });


        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
                timePicker();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }


        if (!id.equalsIgnoreCase("0")) {

            edit_place.setText(getSqLitDB().getEventByID(Integer.parseInt(id)).getPlace());
            edit_duration.setText(getSqLitDB().getEventByID(Integer.parseInt(id)).getDuration());
            edit_comment.setText(getSqLitDB().getEventByID(Integer.parseInt(id)).getComment());

            String imgPath = getSqLitDB().getEventByID(Integer.parseInt(id)).getPhoto();

            String latlng = "Lat/Lang:(" + getSqLitDB().getEventByID(Integer.parseInt(id)).getLat()
                    .concat(getSqLitDB().getEventByID(Integer.parseInt(id)).getLng()).concat(")");
            txt_lat_lng.setText(latlng);


            Log.d(TAG, "Photo Path-->    " + imgPath);
            Log.d(TAG, "LatLng--> " + latlng);

        }
    }

    public void checkLocationIsEnable() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gps_enabled && !network_enabled) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
                dialog.setPositiveButton(getResources().getString(R.string.open_location_settings),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                            }
                        });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
                dialog.show();
            } else {
                getCurrentLocation();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    public void getCurrentLocation() {
        Log.e("Find Location", "in find_location");
        String location_context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {
                        }

                        public void onProviderDisabled(String provider) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }
                    });
            Location location = locationManager.getLastKnownLocation(provider);

            Log.d(TAG, "location --->" + location);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                Log.d(TAG, "latLng --->" + latLng);
                Log.d(TAG, "lat    --->" + latitude);
                Log.d(TAG, "long   --->" + longitude);
                txt_lat_lng.setText(String.valueOf(latLng));
            } else {
                Toast.makeText(getActivity(), "Enable to get your current location right now. Try after sometime.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class datePicker extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            view.setMaxDate(System.currentTimeMillis());
            month = month + 1;
            if (month < 10 && day < 10) {
                btn_date.setText("0" + day + "-" + "0" + month + "-" + year);
            } else {
                btn_date.setText(day + "-" + month + "-" + year);
            }
        }
    }

    public void timePicker() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);


        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String AM_PM;
                if (selectedHour < 12) {
                    hrs = selectedHour;
                    AM_PM = "AM";
                } else {
                    hrs = selectedHour - 12;
                    AM_PM = "PM";
                }
                Log.e(TAG, "Time IS : " + hrs + ":" + selectedMinute + " " + AM_PM);
                btn_time.setText(selectedHour + ":" + selectedMinute + " " + AM_PM);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isAllFilled() {

        place = edit_place.getText().toString().trim();
        duration = edit_duration.getText().toString().trim();
        comment = edit_comment.getText().toString().trim();

        str_date = btn_date.getText().toString().trim();
        str_time = btn_time.getText().toString().trim();

        user_location = txt_lat_lng.getText().toString().trim();


        if (activity_type.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Select activity type for events", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((str_date.equalsIgnoreCase("")) || (str_date.equalsIgnoreCase("Select Date"))) {
            Toast.makeText(getActivity(), "Select date for events", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((str_time.equalsIgnoreCase("")) || (str_time.equalsIgnoreCase("Select time"))) {
            Toast.makeText(getActivity(), "Select time for events", Toast.LENGTH_LONG).show();
            return false;
        }
        if (place.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Enter place for events", Toast.LENGTH_LONG).show();
            return false;
        }
        if (duration.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Enter duration for events", Toast.LENGTH_LONG).show();
            return false;
        }
        if (comment.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Enter comment for events", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }


}
