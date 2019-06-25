package org.jugendhackt.fahrradkette.view.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapzen.tangram.MarkerPickListener;
import com.mapzen.tangram.MarkerPickResult;

import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.model.Bike;

public class MarkerBottomSheet implements MarkerPickListener {
    private BottomSheetBehavior bottomSheet;
    private TextView bikeNameTextView;
    private TextView coordinatesTextView;
    private TextView distanceTextView;
    private TextView notesTextView;

    public MarkerBottomSheet(View bottomSheetView) {
        bottomSheet = BottomSheetBehavior.from(bottomSheetView);
        bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        bikeNameTextView = bottomSheetView.findViewById(R.id.textViewBikename);
        coordinatesTextView = bottomSheetView.findViewById(R.id.textViewCoordinates);
        distanceTextView = bottomSheetView.findViewById(R.id.textViewDistance);
        notesTextView = bottomSheetView.findViewById(R.id.textViewNotes);
    }

    private void updateView(Bike bike) {
        bikeNameTextView.setText("Bike " + bike.id);
        coordinatesTextView.setText(String.format("%f %f", bike.latitude, bike.longitude));
        distanceTextView.setText("24 m");
        notesTextView.setText(bike.notes);
    }

    @Override
    public void onMarkerPick(MarkerPickResult markerPickResult, float positionX, float positionY) {
        if(markerPickResult != null) {
            bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
            Bike bike = (Bike) markerPickResult.getMarker().getUserData();
            updateView(bike);
        }
        else {
            bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
