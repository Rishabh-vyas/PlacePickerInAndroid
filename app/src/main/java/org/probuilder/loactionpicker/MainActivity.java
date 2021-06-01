package org.probuilder.loactionpicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button pickBtn;
    private TextView showLoaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pickBtn=findViewById(R.id.pBtn);
        showLoaction=findViewById(R.id.showLocation);

        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MainActivity.this),101);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stringBuilder = new StringBuilder();
                double lat = Double.valueOf(place.getLatLng().latitude);
                double longi = Double.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(lat);
                stringBuilder.append("LOGI :");
                stringBuilder.append(longi);
                showLoaction.setText(stringBuilder.toString());

                getCompleteAddressString(lat,longi);
            }
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        String fullA = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                StringBuilder fullAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getSubAdminArea()).append("\n");
                    fullAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                fullA = fullAddress.toString();
                Toast.makeText(this, strReturnedAddress.toString() ,Toast.LENGTH_SHORT).show();
                Toast.makeText(this, fullAddress.toString() ,Toast.LENGTH_SHORT).show();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "not get address", Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }
}