package org.jugendhackt.fahrradkette.view.ui;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapzen.tangram.MarkerPickListener;
import com.mapzen.tangram.MarkerPickResult;

import org.jugendhackt.fahrradkette.R;
import org.jugendhackt.fahrradkette.model.Bike;

public class MarkerBottomSheet extends BottomSheetBehavior.BottomSheetCallback implements MarkerPickListener  {
    private BottomSheetBehavior bottomSheet;
    private TextView bikeNameTextView;
    private TextView coordinatesTextView;
    private TextView distanceTextView;
    private TextView notesTextView;
    private FloatingActionButton fab;
    private boolean addState = false;

    public MarkerBottomSheet(View bottomSheetView, FloatingActionButton fab) {
        this.fab = fab;

        bottomSheet = BottomSheetBehavior.from(bottomSheetView);
        bottomSheet.setBottomSheetCallback(this);
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

    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {

    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        if(slideOffset <= 0) {
            if(slideOffset < -0.5 && !addState) {
                addState = true;
                fab.setImageDrawable(fab.getContext().getResources().getDrawable(R.drawable.ic_add_black_24dp));
                fab.setBackgroundTintList(ColorStateList.valueOf(fab.getContext().getResources().getColor(R.color.colorAccent)));
            }
            if(slideOffset >= -0.5 && addState) {
                addState = false;
                fab.setImageDrawable(fab.getContext().getResources().getDrawable(R.drawable.ic_done_black_24dp));
                fab.setBackgroundTintList(ColorStateList.valueOf(fab.getContext().getResources().getColor(R.color.colorRent)));
            }
            fab.setScaleY((float)Math.pow(slideOffset*2+1, 2));
        }
        else {
            fab.setScaleY(1);
        }
    }
}
