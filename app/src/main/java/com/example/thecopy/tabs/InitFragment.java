package com.example.thecopy.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.thecopy.R;
import com.example.thecopy.activities.MatchActivity;
import com.example.thecopy.data.repo.StatsRepo;
import com.example.thecopy.dialogs.NoShowDialogFragment;

public class InitFragment extends Fragment {

    private MatchActivity mActivity;

    private CheckBox noShow;
    private ImageView robotPic;

    private StatsRepo statsRepo;

    private String mEvent;
    private int mMatchNum;
    private int mTeamNum;

    private int noShowValue;

    public NoShowDialogFragment noShowDF;

    private boolean viewsAssigned = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_init, container, false);
        mActivity = (MatchActivity) getActivity();
        mEvent = mActivity.mEvent;
        mMatchNum = mActivity.mMatchNumber;
        mTeamNum = mActivity.mTeamNumber;
        statsRepo = new StatsRepo();

        noShowDF = new NoShowDialogFragment();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        assignViews(view);
        //if(viewsAssigned) loadData(mInitData);
    }

    @Override
    public void onPause(){
        super.onPause();
        noShowValue = (noShow.isChecked() == true ? 1 : 0);
        statsRepo.setInitStats(mEvent, mMatchNum, mTeamNum, noShowValue);
        viewsAssigned=false;
    }

    @Override
    public void onResume(){
        super.onResume();
        assignViews(getView());
        //if(viewsAssigned) loadData(mInitData);
    }

    private void assignViews(View view){
        try{
            noShow = (CheckBox) view.findViewById(R.id.cb_noShow);
            robotPic = (ImageView) view.findViewById(R.id.img_teamPic);
            ImageTools.placeImage(getActivity(), mActivity.mTeamNumber, robotPic);

            viewsAssigned = true;
        } catch (Exception e){
            e.printStackTrace();
            viewsAssigned = false;
        }
    }

    public void command_noShow(View view) {
        if (noShow.isChecked()) {
            noShowDF.show(getChildFragmentManager(), "NoShowDialogFragment");
        }
    }

    public void setNoShow(boolean b){
        noShow.setChecked(b);
    }
}
