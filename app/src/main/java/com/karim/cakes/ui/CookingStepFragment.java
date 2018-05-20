package com.karim.cakes.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.karim.cakes.R;
import com.karim.cakes.model.Recipe;
import com.karim.cakes.model.Step;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CookingStepFragment extends Fragment {

    private List<Step> mSteps;
    private int mIndex;
    private Step mStep;
    private String mInstructionText;
    private String mUrl;
    private String mThumbnail;
    private Recipe mRecipe;
    Button nextButton;
    Button previousButton;
    TextView cookingStepText;
    ImageView mThumbnailImage;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mExoPlayerView;
    long position = C.TIME_UNSET;

    public CookingStepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(getString(R.string.recipe_details));
            mIndex = savedInstanceState.getInt("Index");
            position = savedInstanceState.getLong("SELECTED_POSITION", C.TIME_UNSET);
        }

        View rootView = inflater.inflate(R.layout.fragment_cooking_step, container, false);
        cookingStepText = rootView.findViewById(R.id.fragment_text);
        mThumbnailImage = rootView.findViewById(R.id.fragment_thumbnail);
        nextButton = rootView.findViewById(R.id.next_button);
        previousButton = rootView.findViewById(R.id.previous_button);
        mExoPlayerView = rootView.findViewById(R.id.fragment_video);
        mSteps = mRecipe.getSteps();
        mStep = mSteps.get(mIndex);
        mInstructionText = mStep.getDescription();
        cookingStepText.setText(mInstructionText);

        if (mIndex == mSteps.size() - 1) {
            nextButton.setVisibility(View.INVISIBLE);
        } else if (mIndex == 0) {
            previousButton.setVisibility(View.INVISIBLE);
        }

        mUrl = mStep.getVideoURL();
        mThumbnail = mStep.getThumbnailURL();
        initializePlayer(mUrl, mThumbnail);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex < mSteps.size() - 1) {
                    mIndex++;
                    mStep = mSteps.get(mIndex);
                    mInstructionText = mStep.getDescription();
                    cookingStepText.setText(mInstructionText);

                    mUrl = mStep.getVideoURL();
                    mThumbnail = mStep.getThumbnailURL();
                    setVideo(mUrl, mThumbnail);
                    previousButton.setVisibility(View.VISIBLE);
                    if (mIndex == mSteps.size() - 1) {
                        nextButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIndex >= 1) {
                    mIndex--;
                    mStep = mSteps.get(mIndex);
                    mInstructionText = mStep.getDescription();
                    cookingStepText.setText(mInstructionText);

                    mUrl = mStep.getVideoURL();
                    mThumbnail = mStep.getThumbnailURL();
                    setVideo(mUrl, mThumbnail);
                    nextButton.setVisibility(View.VISIBLE);
                    if (mIndex == 0) {
                        previousButton.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }


    public void setIndex(int index) {
        mIndex = index;
    }

    public void initializePlayer(String url, String thumbnail) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            setVideo(url, thumbnail);
        }
    }

    public void setVideo(String url, String thumbnail) {
        mExoPlayer.stop();
        if (url.isEmpty()) {
            mExoPlayerView.setVisibility(View.GONE);
            if (thumbnail.isEmpty()) {
                mThumbnailImage.setVisibility(View.GONE);
            } else {
                Picasso.with(getContext()).load(thumbnail).into(mThumbnailImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mThumbnailImage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        Log.e("Picasso", "cannot load image");
                        mThumbnailImage.setVisibility(View.GONE);
                    }
                });
            }
        } else {
            mExoPlayerView.setVisibility(View.VISIBLE);
            String userAgent = Util.getUserAgent(getContext(), "Cakes");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url), new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position != C.TIME_UNSET) mExoPlayer.seekTo(position);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.recipe_details), mRecipe);
        outState.putInt("Index", mIndex);
        outState.putLong("SELECTED_POSITION", position);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(mUrl, mThumbnail);
    }
}
