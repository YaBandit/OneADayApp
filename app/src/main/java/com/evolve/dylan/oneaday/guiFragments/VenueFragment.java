package com.evolve.dylan.oneaday.guiFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evolve.dylan.oneaday.R;

public class VenueFragment extends Fragment implements View.OnClickListener {

    private Button voteVenueButton;
    private TextView venueTitleText, venueRatingCountText, venueRatingNumber;
    private ImageView venueImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    private void setGuiComponents(View v) {
        voteVenueButton = (Button) v.findViewById(R.id.VoteForVenusButton);
        venueTitleText = (TextView) v.findViewById(R.id.VenueTitleText);
        venueRatingNumber = (TextView) v.findViewById(R.id.VenueRatingNumberText);
        venueRatingCountText = (TextView) v.findViewById(R.id.VenueRatingCount);
        venueImage = (ImageView) v.findViewById(R.id.VenueImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VoteForVenusButton:
                break;
        }
    }
}
