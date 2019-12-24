package com.example.travelapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.api.model.request.CoordStopPoint;
import com.example.travelapp.api.model.request.ReqSetStopPoints;
import com.example.travelapp.api.model.request.ReqSuggestStopPoint;
import com.example.travelapp.api.model.request.StopPoint;
import com.example.travelapp.api.model.response.ResSetStopPoints;
import com.example.travelapp.api.model.response.ResStopPoint;
import com.example.travelapp.api.model.response.ResSuggestStopPoint;
import com.example.travelapp.api.service.RetrofitClient;
import com.example.travelapp.api.service.UserService;
import com.example.travelapp.store.UserStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener {

    private GoogleMap mMap;
    private LatLng myLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private List<ResStopPoint> stopPointList;
    private List<StopPoint> stopPoints;
    private String startDate, endDate;
    private int clickDatePicker;
    private TextView startDateTextView, endDateTextView;
    private int locationRequestCode = 1000;
    private String id;
    private double wayLatitude = 0.0, wayLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stopPoints = new ArrayList<>();
        id = getIntent().getStringExtra("id");
        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {
            // already permission granted
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.my_location) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
        }
        if (item.getItemId() == R.id.list) {
            // TODO: here
            UserStore userStore = new UserStore(getApplicationContext());
            UserService userService = RetrofitClient.getUserService();
            Call<ResSetStopPoints> call = userService.setStopPoints(
                    userStore.getUser().getAccessToken(),
                    new ReqSetStopPoints(id, stopPoints)
            );
            call.enqueue(new Callback<ResSetStopPoints>() {
                @Override
                public void onResponse(Call<ResSetStopPoints> call, Response<ResSetStopPoints> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Add stop points successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String msg = jsonObject.getJSONArray("message").getJSONObject(0).get("msg").toString();
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResSetStopPoints> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Add stop points failed", Toast.LENGTH_LONG).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getPosition().latitude == myLocation.latitude && marker.getPosition().longitude == myLocation.longitude) {
                    // nothing
                } else
                for (int i = 0; i < stopPointList.size(); i++) {
                    if (stopPointList.get(i).getId().equals(Integer.valueOf(marker.getSnippet()))) {
                        String minCost = stopPointList.get(i).getMinCost();
                        String maxCost = stopPointList.get(i).getMaxCost();
                        String service;
                        switch (stopPointList.get(i).getServiceTypeId()) {
                            case 1:
                                service = "Restaurant";
                                break;
                            case 2:
                                service = "Hotel";
                                break;
                            case 3:
                                service = "Station";
                                break;
                                default:
                                    service = "Other";
                                    break;
                        }
                        final int finalI = i;
                        new AlertDialog.Builder(MapsActivity.this)
                                .setTitle("Add stop point")
                                .setMessage("$: " + minCost + " - " + maxCost + "\nService: " + service)
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        stopPoints.add(0, new StopPoint(
                                                stopPointList.get(finalI).getName(),
                                                stopPointList.get(finalI).getLat(),
                                                stopPointList.get(finalI).getLong(),
                                                "15532956000",
                                                "15532956000",
                                                String.valueOf(stopPointList.get(finalI).getServiceTypeId()),
                                                String.valueOf(stopPointList.get(finalI).getMinCost()),
                                                String.valueOf(stopPointList.get(finalI).getMaxCost())
                                        ));
                                    }
                                })
                                .show();
                    }
                }
                return false;
            }
        });
        fusedLocationClient
                .getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            UserStore userStore = new UserStore(getApplicationContext());
                            UserService userService = RetrofitClient.getUserService();
                            Call<ResSuggestStopPoint> call = userService.getSuggestedDestinations(
                                    userStore.getUser().getAccessToken(),
                                    new ReqSuggestStopPoint(true, new CoordStopPoint(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())))
                            );
                            call.enqueue(new Callback<ResSuggestStopPoint>() {
                                @Override
                                public void onResponse(Call<ResSuggestStopPoint> call, Response<ResSuggestStopPoint> response) {
                                    if (response.isSuccessful()) {
                                        stopPointList = new ArrayList<>(response.body().getStopPoints());
                                        for(int i = 0; i < response.body().getStopPoints().size(); i++) {
                                            String lat = response.body().getStopPoints().get(i).getLat();
                                            String _long = response.body().getStopPoints().get(i).getLong();
                                            String name = response.body().getStopPoints().get(i).getName();
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(Double.valueOf(lat), Double.valueOf(_long))).title(
                                                            name
                                                    )).setSnippet(String.valueOf(response.body().getStopPoints().get(i).getId()));
                                        }
                                    } else {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                            Toast.makeText(getApplicationContext(), jsonObject.get("message").toString(), Toast.LENGTH_LONG).show();
                                        } catch (JSONException | IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResSuggestStopPoint> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "get suggested stop points failed", Toast.LENGTH_LONG).show();
                                }
                            });
                            myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                        }
                    }
                });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                final View view = getLayoutInflater().inflate(R.layout.popup_stop_points, null);

                final EditText nameStopPoint = view.findViewById(R.id.stop_point_name);
                final EditText minCost = view.findViewById(R.id.min_cost);
                final EditText maxCost = view.findViewById(R.id.max_cost);

                startDateTextView = view.findViewById(R.id.start_date);
                endDateTextView = view.findViewById(R.id.end_date);
                startDateTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDatePicker = 1;
                        showDatePicker();
                    }
                });

                endDateTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickDatePicker = 2;
                        showDatePicker();
                    }
                });
                /*
                Spinner spinner = view.findViewById(R.id.service_type);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter
                        .createFromResource(getApplication(), R.array.type, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner

                 */
                final RadioGroup radioGroup = view.findViewById(R.id.service_type);

                new AlertDialog.Builder(MapsActivity.this)
                        .setView(view)
                        .setTitle("Add stop point")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int service = 4;
                                final RadioButton radioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                                switch (radioButton.getId()) {
                                    case R.id.restaurant:
                                        service = 1;
                                        break;
                                    case R.id.hotel:
                                        service = 2;
                                        break;
                                    case R.id.station:
                                        service = 3;
                                        break;
                                        default:
                                            break;
                                }
                                stopPoints.add(0, new StopPoint(
                                        nameStopPoint.getText().toString(),
                                        String.valueOf(latLng.latitude),
                                        String.valueOf(latLng.longitude),
                                        startDate,
                                        endDate,
                                        String.valueOf(service),
                                        minCost.getText().toString(),
                                        maxCost.getText().toString()
                                ));
                            }
                        })
                        .show();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Objects.requireNonNull(MapsActivity.this),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        if (clickDatePicker == 1) {
            startDate = String.valueOf(cal.getTimeInMillis() / 1000);
            startDateTextView.setText(dayOfMonth + "/" + month + "/" + year);
        } else {
            endDate = String.valueOf(cal.getTimeInMillis() / 1000);
            endDateTextView.setText(dayOfMonth + "/" + month + "/" + year);
        }
    }
}
