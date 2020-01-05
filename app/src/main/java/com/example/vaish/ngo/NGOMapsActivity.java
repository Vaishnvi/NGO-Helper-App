package com.example.vaish.ngo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class NGOMapsActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
Bookings b_obj;
    LatLng latLng;
String ngo_places;
DatabaseHelper mydb;
Button viewAll,viewNearby,finalize1;
LatLng[] loca;
String[] lat1,lon1;
double[] lat,lon;
String[] name;
double[] distance;
int[] near3;
double[] distance_copy;
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE=99;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngomaps);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        b_obj = new Bookings();
        ngo_places = b_obj.getPlaces();
        mydb = new DatabaseHelper(this);
        viewAll = (Button)findViewById(R.id.allngos);
        viewNearby=(Button)findViewById(R.id.nearby);
        finalize1=(Button)findViewById(R.id.finalise);
        lat1 = new String[100];
        lon1 = new String[100];
        lat = new double[100];
        lon = new double[100];
        name = new String[100];
        distance = new double[100];
        distance_copy = new double[100];
        near3 = new int[3];
        loca=new LatLng[100];
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("message");

        viewNearbyFunc();
        viewAllFunc();
    }
    public void viewAllFunc()
    {
       viewAll.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Cursor res=mydb.getData();
               if(res.getCount()==0)
               {
                   Toast.makeText(NGOMapsActivity.this,"Out of Outside",Toast.LENGTH_SHORT).show();
                   return;
               }
               int i=0;
               //     StringBuffer buffer=new StringBuffer();
               while(res.moveToNext()){
                   Toast.makeText(NGOMapsActivity.this,"Outside",Toast.LENGTH_SHORT).show();
                   if(res.getString(2).equals(message)){
                       Toast.makeText(NGOMapsActivity.this,"Inside",Toast.LENGTH_SHORT).show();
                       lon1[i] = res.getString(3);
                       lon[i] = Double.parseDouble(lon1[i]);
                       lat1[i] = res.getString(4);
                       lat[i] = Double.parseDouble(lat1[i]);
                       name[i] = res.getString(1);

                       LatLng latLngB = new LatLng(lon[i],lat[i]);
                       //Location locationB = new Location(String.valueOf(latLngB));
                       Location locationB = new Location(name[i]);
                       locationB.setLatitude(latLngB.latitude);
                       locationB.setLongitude(latLngB.longitude);
                       loca[i]=latLngB;
                       Location locationA = new Location("My current location");
                       locationA.setLatitude(latLng.latitude);
                       locationA.setLongitude(latLng.longitude);

                       Toast.makeText(NGOMapsActivity.this,"longitude"+latLngB.longitude,Toast.LENGTH_SHORT).show();
                       Toast.makeText(NGOMapsActivity.this,"latitde"+latLngB.latitude,Toast.LENGTH_SHORT).show();


                       distance[i] = locationA.distanceTo(locationB);
                       Toast.makeText(NGOMapsActivity.this,"distance"+distance[i],Toast.LENGTH_SHORT).show();

                       MarkerOptions markerOptions=new MarkerOptions();
                       markerOptions.position(latLngB);
                       markerOptions.title(name[i]);
                       markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                       markerOptions.snippet("Website:www"+name[i]+".com\nContact Admin:9764286196");

                       currentLocationMarker=mMap.addMarker(markerOptions);
                       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngB));
                       mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
                       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                           @Override
                           public boolean onMarkerClick(Marker marker) {
                               if(!marker.getTitle().equals(""))
                               {
                                   String ans=marker.getTitle();
                                   /*Intent i=new Intent(NGOMapsActivity.this,Bookings.class);
                                   i.putExtra("message",ans);
                                   startActivity(i);*/
                                   isfinal(ans);
                               }
                               return false;
                           }
                       });

                   }
             /*       buffer.append("id_key : "+ res.getString(0)+"\n");
                    buffer.append("name : "+ res.getString(1)+"\n");
                    buffer.append("pincode : "+ res.getString(2)+"\n");
                    buffer.append("lon : "+ res.getString(3)+"\n");
                    buffer.append("lat : "+ res.getString(4)+"\n\n");



             */   }

           }
       });
    }

    public void isfinal(final String ans)
    {
        finalize1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NGOMapsActivity.this,Bookings.class);
                                    i.putExtra("message", ans);
                                    startActivity(i);
            }
        });
    }
    public void viewNearbyFunc(){
        viewNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=mydb.getData();
                if(res.getCount()==0)
                {
                    Toast.makeText(NGOMapsActivity.this,"Out of Outside",Toast.LENGTH_SHORT).show();
                    return;
                }
                int i=0;
           //     StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()) {
                    Toast.makeText(NGOMapsActivity.this, "Outside", Toast.LENGTH_SHORT).show();
                    if (res.getString(2).equals(message)) {
                        Toast.makeText(NGOMapsActivity.this, "Inside", Toast.LENGTH_SHORT).show();
                        lon1[i] = res.getString(3);
                        lon[i] = Double.parseDouble(lon1[i]);
                        lat1[i] = res.getString(4);
                        lat[i] = Double.parseDouble(lat1[i]);
                        name[i] = res.getString(1);

                        LatLng latLngB = new LatLng(lon[i], lat[i]);
                        //Location locationB = new Location(String.valueOf(latLngB));
                        Location locationB = new Location(name[i]);
                        locationB.setLatitude(latLngB.latitude);
                        locationB.setLongitude(latLngB.longitude);
                        loca[i] = latLngB;
                        Toast.makeText(NGOMapsActivity.this,"lat in latlng "+loca[i].latitude,Toast.LENGTH_SHORT).show();

                        Location locationA = new Location("My current location");
                        locationA.setLatitude(latLng.latitude);
                        locationA.setLongitude(latLng.longitude);

                        //Toast.makeText(NGOMapsActivity.this, "longitude" + latLngB.longitude, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(NGOMapsActivity.this, "latitde" + latLngB.latitude, Toast.LENGTH_SHORT).show();


                        distance[i] = locationA.distanceTo(locationB);
                        //Toast.makeText(NGOMapsActivity.this, "distance" + distance[i], Toast.LENGTH_SHORT).show();

                        // MarkerOptions markerOptions=new MarkerOptions();
                        // markerOptions.position(latLngB);
                        // markerOptions.title(name[i]);
                        // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                        //   currentLocationMarker=mMap.addMarker(markerOptions);
                        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngB));
                        //   mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

                        i++;

                    }
                }
                Toast.makeText(NGOMapsActivity.this,"Outside of while "+i,Toast.LENGTH_SHORT).show();
             /*       buffer.append("id_key : "+ res.getString(0)+"\n");
                    buffer.append("name : "+ res.getString(1)+"\n");
                    buffer.append("pincode : "+ res.getString(2)+"\n");
                    buffer.append("lon : "+ res.getString(3)+"\n");
                    buffer.append("lat : "+ res.getString(4)+"\n\n"); */

                    //Nearby 3 ngos code :

                    for(int j=0;j<i;j++){
                        distance_copy[j] = distance[j];
                    }
/*                    double low = 0,var=0;
                    //LatLng temp;

                    for (int j = 0; j < 3; j++) {
                        low = distance_copy[j];
                        near3[j] = j;
                        for(int s = j+1; s<i; s++){
                             if(distance_copy[s]<low){
                                 low = distance_copy[s];
                                 near3[j]=s;
                             }
                        }
                        if(near3[j]!=j){
                            var = distance_copy[near3[j]];
                            distance_copy[near3[j]] = distance_copy[j];
                            distance_copy[j] = var;
                        }
                    }
*/
                    for(int j=0;j<3;j++){
                        for(int d=j+1;d<i;d++){
                            if(distance_copy[d]<distance_copy[j]){
                                double temp;
                                temp = distance_copy[d];
                                distance_copy[d]=distance_copy[j];
                                distance_copy[j]=temp;
                            }
                        }
                    }

                    for(int l=0;l<3;l++) {
                        for (int j = 0; j < i; j++) {
                            if (distance_copy[l]==distance[j]){
                                near3[l]=j;
                            }
                        }
                    }

                    for(int b=0;b<3;b++){
                        MarkerOptions markerOptions=new MarkerOptions();
                        markerOptions.position(loca[near3[b]]);
                        markerOptions.title(name[near3[b]]);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerOptions.snippet("Website:www"+name[near3[b]]+".com\nContact Admin:9764286196");

                        currentLocationMarker=mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(loca[near3[b]]));
                        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                if(!marker.getTitle().equals(""))
                                {
                                    String ans=marker.getTitle();
                                    isfinal(ans);
                                    /*Intent i=new Intent(NGOMapsActivity.this,Bookings.class);
                                    i.putExtra("message",ans);
                                    startActivity(i);*/
                                }
                                return false;
                            }
                        });



                    }



            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    //Permission granted
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if(client==null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                }
                return;
        }
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
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }


    }
    protected synchronized void buildGoogleApiClient()
    {
        client=new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
        client.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation=location;

        if(currentLocationMarker!=null)
        {
            currentLocationMarker.remove();
        }

        latLng=new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Loaction");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker=mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        if(client!=null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest=new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {LocationServices.FusedLocationApi.requestLocationUpdates(client,locationRequest,this);}

    }
    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

