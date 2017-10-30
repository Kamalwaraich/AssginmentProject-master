package blackriders.assginmentproject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double dLat, dLng;
    String lat, lng;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        img_back = (ImageView) findViewById(R.id.img_back);

        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");

        dLat = Double.parseDouble(lat);
        dLng = Double.parseDouble(lng);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(dLat, dLng);

        Log.e("MapActivity", "Get Lat : " + lat);
        Log.e("MapActivity", "Get Lng : " + lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 19.0f));
    }
}
