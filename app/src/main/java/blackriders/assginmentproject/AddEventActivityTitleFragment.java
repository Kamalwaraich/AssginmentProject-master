package blackriders.assginmentproject;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 10/16/2017.
 */

public class AddEventActivityTitleFragment extends Fragment {
    private String TAG = "AddEventTitleFragment";
    private String id;
    private static final int RESULT_LOAD_IMAGE = 1;

    SQLitDB sqLitDB;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(getActivity());
        return sqLitDB;
    }


    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri mCapturedImageURI;

    EditText edit_title;
    ImageView img_preview;
    ImageButton imgBnt_action;

    String picturePath = "", title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view;
        view = inflater.inflate(R.layout.add_event_activity_title, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edit_title = (EditText) view.findViewById(R.id.edit_title);
         /*Imageview*/
        img_preview = (ImageView) view.findViewById(R.id.img_preview);
        //img_back = (ImageView)view. findViewById(R.id.img_back);

        /*ImageButton*/
        imgBnt_action = (ImageButton) view.findViewById(R.id.imgBnt_action);


        imgBnt_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showActionAlert(view);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }

        if (!id.equalsIgnoreCase("0")) {

            edit_title.setText(getSqLitDB().getEventByID(Integer.parseInt(id)).getTitle());

            String imgPath = getSqLitDB().getEventByID(Integer.parseInt(id)).getPhoto();

            showPhoto(imgPath);

            Log.d(TAG, "Photo Path-->    " + imgPath);
        }

    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == getActivity().RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Log.e(TAG, "Photo Path Gallery : " + picturePath);
                    showPhoto(picturePath);
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    picturePath = cursor.getString(column_index_data);

                    Log.e(TAG, "Photo Path Camera : " + picturePath);
                    showPhoto(picturePath);
                }
        }
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    public void showActionAlert(View view) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeGallery();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeTakePhoto();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showPhoto(String picPath) {

        if (!picPath.equalsIgnoreCase("")) {
            BitmapDrawable bitmapDrawable = null;
            bitmapDrawable = PictureUtils.getScaledDrawable(getActivity(), picPath);
            img_preview.setImageDrawable(bitmapDrawable);
        }

    }

    public boolean isAllFilled() {
        title = edit_title.getText().toString().trim();


        if (title.equalsIgnoreCase(""))
        {
            Toast.makeText(getActivity(), "Enter title of events", Toast.LENGTH_LONG).show();
            return false;
        }
            return true;
    }

}
