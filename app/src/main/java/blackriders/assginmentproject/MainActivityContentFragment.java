package blackriders.assginmentproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */

public class MainActivityContentFragment extends Fragment {

    OnSelectListener onSelectListener;
    TextView txt_no_data;
    ListView list_events;
    SQLitDB sqLitDB;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(getActivity());
        return sqLitDB;
    }


    public class EventAdapter extends BaseAdapter {

        Activity context;
        List<Events> eventsList;
        private LayoutInflater inflater;

        public EventAdapter(Activity context, List<Events> eventsList) {
            this.context = context;
            this.eventsList = eventsList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return eventsList.size();
        }

        @Override
        public Object getItem(int position) {
            return eventsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            final ViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.custom_event_row, viewGroup, false);

                viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
                viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
                viewHolder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
                viewHolder.txt_place = (TextView) convertView.findViewById(R.id.txt_place);
                viewHolder.txt_duration = (TextView) convertView.findViewById(R.id.txt_duration);
                viewHolder.txt_comment = (TextView) convertView.findViewById(R.id.txt_comment);
                viewHolder.btn_go = (Button) convertView.findViewById(R.id.btn_go_map);
                viewHolder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);
                viewHolder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
                viewHolder.img_preview = (ImageView) convertView.findViewById(R.id.img_preview);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txt_title.setText("Title :" + eventsList.get(position).getTitle());
            viewHolder.txt_name.setText("Activity :" + eventsList.get(position).getActivity_type());
            viewHolder.txt_date.setText("Date & Time :" + eventsList.get(position).getDate());
            viewHolder.txt_place.setText("Place :" + eventsList.get(position).getPlace());
            viewHolder.txt_duration.setText("Duration :" + eventsList.get(position).getDuration());
            viewHolder.txt_comment.setText("Comment :" + eventsList.get(position).getComment());

            String picturePath = eventsList.get(position).getPhoto();

            if (!picturePath.equalsIgnoreCase("")) {
                BitmapDrawable bitmapDrawable = null;
                bitmapDrawable = PictureUtils.getScaledDrawable(context, picturePath);
                viewHolder.img_preview.setImageDrawable(bitmapDrawable);
            }


            viewHolder.btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String lat = eventsList.get(position).getLat();
                        String lng = eventsList.get(position).getLng();

                        Log.e("Adatper", "Get Lat : " + lat);
                        Log.e("Adatper", "Get Lng : " + lng);
                        if ((lat == null) || (lat.equalsIgnoreCase("")) || (lat.equalsIgnoreCase("0.0"))) {
                            showMapAlert();
                        } else {
                            Intent intent = new Intent(context, MapsActivity.class);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            startActivity(intent);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deleteId = eventsList.get(position).getId();
                    onSelectListener.onSelect(deleteId);
                }
            });

            viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deleteId = eventsList.get(position).getId();
                    getSqLitDB().deleteRow(deleteId);
                    setList();
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView txt_title, txt_name, txt_date, txt_place, txt_duration, txt_comment;
            ImageView img_preview;
            Button btn_go, btn_edit, btn_delete;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.main_activity_content, container, false);
        txt_no_data = (TextView)view.findViewById(R.id.txt_no_data);
        list_events = (ListView)view.findViewById(R.id.list_events);
        onSelectListener = (OnSelectListener)getActivity();

        return view;
    }
    public void showMapAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("For current Lat-Lng not found");
        builder1.setCancelable(true);

        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void setList() {
        if (getSqLitDB().getEventCount() == 0) {
            list_events.setVisibility(View.GONE);
            txt_no_data.setVisibility(View.VISIBLE);
        } else {
            list_events.setAdapter(new EventAdapter(getActivity(), getSqLitDB().getEventsList()));
        }
    }

    interface OnSelectListener{
        void onSelect(String id);
    }

}
