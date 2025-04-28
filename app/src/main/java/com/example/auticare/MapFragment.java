package com.example.auticare;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap gMap;
    private Polygon boundaryPolygon;
    private List<LatLng> polygonPoints = new ArrayList<>();
    private FloatingActionButton resetButton;

    public MapFragment() {
        // Required empty constructor
    }

    @Override
    public android.view.View onCreateView(@NonNull android.view.LayoutInflater inflater,
                                          android.view.ViewGroup container,
                                          Bundle savedInstanceState) {

        android.view.View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        resetButton = view.findViewById(R.id.btnResetBoundary);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        resetButton.setOnClickListener(v -> {
            if (boundaryPolygon != null) {
                boundaryPolygon.remove();
                boundaryPolygon = null;
            }
            polygonPoints.clear();
            gMap.clear();
            Toast.makeText(getContext(), "Boundary reset. Long press to draw again!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        gMap.setMyLocationEnabled(true);

        // ✨ Show friendly Toast
        Toast.makeText(getContext(), "Long press on the map to create a boundary!", Toast.LENGTH_LONG).show();

        // Center to device location if available
        gMap.setOnMyLocationChangeListener(location -> {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16));

            if (boundaryPolygon != null) {
                if (!isInsidePolygon(userLocation)) {
                    Toast.makeText(getContext(), "⚠️ You are outside the boundary!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle Long Press to create boundary
        gMap.setOnMapLongClickListener(latLng -> {
            polygonPoints.add(latLng);
            gMap.addMarker(new MarkerOptions().position(latLng));
            redrawPolygon();
        });
    }

    private void redrawPolygon() {
        if (boundaryPolygon != null) {
            boundaryPolygon.remove();
        }
        if (polygonPoints.size() >= 3) {
            PolygonOptions polygonOptions = new PolygonOptions()
                    .addAll(polygonPoints)
                    .strokeColor(0xFF00BCD4)    // Cyan border
                    .fillColor(0x3300BCD4);     // Light transparent fill
            boundaryPolygon = gMap.addPolygon(polygonOptions);
        }
    }

    private boolean isInsidePolygon(LatLng point) {
        if (polygonPoints == null || polygonPoints.size() < 3) {
            return true;
        }
        int intersectCount = 0;
        for (int j = 0; j < polygonPoints.size() - 1; j++) {
            if (rayCastIntersect(point, polygonPoints.get(j), polygonPoints.get(j + 1))) {
                intersectCount++;
            }
        }
        if (rayCastIntersect(point, polygonPoints.get(polygonPoints.size() - 1), polygonPoints.get(0))) {
            intersectCount++;
        }
        return (intersectCount % 2) == 1;
    }

    private boolean rayCastIntersect(LatLng point, LatLng vertA, LatLng vertB) {
        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = point.latitude;
        double pX = point.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY) || (aX < pX && bX < pX)) {
            return false;
        }

        double m = (aY - bY) / (aX - bX);
        double bee = -aX * m + aY;
        double x = (pY - bee) / m;

        return x > pX;
    }

    // Lifecycle Methods
    @Override public void onResume() { super.onResume(); mapView.onResume(); }
    @Override public void onStart() { super.onStart(); mapView.onStart(); }
    @Override public void onStop() { super.onStop(); mapView.onStop(); }
    @Override public void onPause() { mapView.onPause(); super.onPause(); }
    @Override public void onDestroy() { mapView.onDestroy(); super.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
}
