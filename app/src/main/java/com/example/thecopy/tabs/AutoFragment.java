package com.example.thecopy.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.thecopy.R;
import com.example.thecopy.activities.MatchActivity;
import com.example.thecopy.data.model.Stats;
import com.example.thecopy.data.repo.StatsRepo;

public class AutoFragment extends Fragment {

    private MatchActivity mActivity;
    private boolean viewAssigned = false;

    private ConstraintLayout autoField;

    private CheckBox autoFragCb_hadAuto;

    private String mEvent;
    private int mMatchNum;
    private int mTeamNum;
    private int mMatchPos;
    private String mAlliance;


    private final String mPhase = "Auto";

    private StatsRepo statsRepo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_auto, container, false);
        mActivity = (MatchActivity)getActivity();
        mEvent = mActivity.mEvent;
        mMatchNum = mActivity.mMatchNumber;
        mTeamNum = mActivity.mTeamNumber;
        mMatchPos = mActivity.mDeviceNumber;
        mAlliance = mActivity.mAllianceColor;

        statsRepo = new StatsRepo();

        if(!viewAssigned) assignViews(rootView);
        if (viewAssigned)loadData();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        assignViews(view);
        if (viewAssigned)loadData();

    }

    @Override
    public void onPause(){
        super.onPause();
        Stats stats = saveData();
        statsRepo.setAutoStats(stats);
    }

    public Stats saveData(){
        Stats stat = new Stats();
        stat.setCompId(mEvent);
        stat.setMatchNum(mMatchNum);
        stat.setTeamNum(mTeamNum);
        int hA = (autoFragCb_hadAuto.isChecked() ? 1 : 0);
        stat.setHadAuto(hA);
        return stat;
    }

    @Override
    public void onResume(){
        super.onResume();
        assignViews(getView());
        if(viewAssigned) loadData();
    }

    private void loadData() {
        Stats stats = statsRepo.getAutoStats(mEvent, mMatchNum, mMatchPos);
        autoFragCb_hadAuto.setChecked(stats.getHadAuto() == 1);
    }

    public void assignViews(View view){
        try {
            autoField = (ConstraintLayout) view.findViewById(R.id.field_view);
            if (mAlliance.equals("RED")){
                autoField.setBackground(getResources().getDrawable(R.drawable.field2018red));
            }else{
                autoField.setBackground(getResources().getDrawable(R.drawable.field2018blue));
            }

            autoFragCb_hadAuto = (CheckBox) view.findViewById(R.id.autoCb_hadAuto);

            autoFragCb_hadAuto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (autoFragCb_hadAuto.isChecked()){
                        enableViews();
                    }else
                        disableViews();
                }
            });

            viewAssigned = true;
        }catch(Exception e){
            e.printStackTrace();
            viewAssigned = false;
        }
    }


    public void enableViews(){

    }

    public void disableViews(){

    }


}
