package rajitha.perera.com.thingstodeliver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import rajitha.perera.com.thingstodeliver.R;
import rajitha.perera.com.thingstodeliver.model.Thing;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Double longitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        CircleImageView imageView = findViewById(R.id.image_view);
        TextView description = findViewById(R.id.description);
        TextView address = findViewById(R.id.address);

        try {

            Thing thing = getIntent().getExtras().getParcelable("Thing");
            longitude = thing.getLocation().getLat();
            latitude = thing.getLocation().getLng();

            Picasso.get().load(thing.getImageUrl()).into(imageView);
            description.setText(thing.getDescription());
            address.setText(thing.getLocation().getAddress());
        } catch (NullPointerException e) {

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Drop Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));
    }
}
