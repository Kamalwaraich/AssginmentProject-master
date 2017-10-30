package blackriders.assginmentproject;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity implements ProfileActivityShowFragment.OnButtonClickListener,
        ProfileActivityEditFragment.OnButtonClickListener{

    String userId;
    ProfileActivityEditFragment profileActivityEditFragment;
    ProfileActivityShowFragment profileActivityShowFragment;
    SQLitDB sqLitDB;

    public SQLitDB getSqLitDB() {
        if (sqLitDB == null)
            sqLitDB = new SQLitDB(ProfileActivity.this);
        return sqLitDB;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId = getIntent().getStringExtra("userId");
        if(userId.equals("0"))
            openEditFragment();
         else
            openShowFragment();

        setContentView(R.layout.profile_activity);
    }
    public void openShowFragment()
    {
        profileActivityShowFragment = new ProfileActivityShowFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_show, profileActivityShowFragment).commit();

    }
    public void openEditFragment()
    {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);

        profileActivityEditFragment = new ProfileActivityEditFragment();
        profileActivityEditFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.fragment_edit, profileActivityEditFragment).commit();
    }
    @Override
    public void onEditClick()
    {
     //   profileActivityShowFragment.onDestroy();
        getFragmentManager().beginTransaction().remove(profileActivityShowFragment).commitAllowingStateLoss();
        openEditFragment();
    }

    @Override
    public void onBackClick()
    {
        finish();
    }

    @Override
    public void onSaveClick(String name, String email, String gender, String comment)
    {
        UserDetails details = new UserDetails(name, email, gender, comment,
                "true");
        if (!userId.equalsIgnoreCase("0")) {
            userId = "1";
            getSqLitDB().updateProfile(userId, details);
        } else {
            getSqLitDB().addUser(details);
        }
        getFragmentManager().beginTransaction().remove(profileActivityEditFragment).commitAllowingStateLoss();
        openShowFragment();
    }
    @Override
    public void onEditBackClick()
    {
        openShowFragment();
    }

}
