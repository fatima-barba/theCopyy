package com.example.thecopy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;
import androidx.viewpager.widget.PagerTabStrip;

import com.example.thecopy.R;
import com.example.thecopy.data.model.MatchInfo;
import com.example.thecopy.data.repo.MatchInfoRepo;
import com.example.thecopy.data.repo.StatsRepo;
import com.example.thecopy.data.repo.TeamsRepo;
import com.example.thecopy.dialogs.DialogListener;
import com.example.thecopy.tabs.AutoFragment;
import com.example.thecopy.tabs.InitFragment;
import com.example.thecopy.tabs.TeleFragment;
import com.example.thecopy.tools.ExternalStorageTools;

public class MatchActivity extends FragmentActivity implements DialogListener {

    private FragmentTabHost mTabHost;
    private InitFragment mInitFrag;
    private AutoFragment mAutoFrag;
    private TeleFragment mTeleFrag;

    public String mEvent;
    public String mType;
    public int mMatchNumber;
    public int mTeamNumber;
    public int mDeviceNumber;
    public String mAllianceColor;

    private TeamsRepo teamsRepo;
    private MatchInfoRepo matchInfoRepo;
    private StatsRepo statsRepo;

    private int allianceColor;
    private int textColor;

    private TextView initTeamNumber;
    private TextView initMatchNumber;
    private TextView initDeviceNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mEvent = getIntent().getStringExtra("event");
        mType = getIntent().getStringExtra("type");
        mMatchNumber = getIntent().getIntExtra("matchNumber", 1);
        mTeamNumber = getIntent().getIntExtra("teamNumber", 0);
        mDeviceNumber = getIntent().getIntExtra("deviceNumber", 1);

        matchInfoRepo = new MatchInfoRepo();

        mAllianceColor = (mDeviceNumber>0 && mDeviceNumber<4)? "RED" : "BLUE";
        allianceColor = (mDeviceNumber>0 && mDeviceNumber<4)? R.color.red : R.color.blue;
        textColor = (allianceColor== R.color.red)? R.color.black : R.color.white;

        setupInfoBar();
        //TODO set up sliding pages
//        pagerAdapter = new MatchPagerAdapter(getSupportFragmentManager());
//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setAdapter(pagerAdapter);
//
//        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);
//        slidingTabLayout.setDistributeEvenly(true);                                     //Distribute tabs evenly
//        slidingTabLayout.setViewPager(viewPager);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("tab_init")
                .setIndicator(getResources().getString(R.string.title_initTab), null), InitFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab_auto")
                .setIndicator(getResources().getString(R.string.title_autoTab), null), AutoFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab_tele")
                .setIndicator(getResources().getString(R.string.title_teleTab), null), TeleFragment.class, null);
    }

    private void setupInfoBar() {
        teamsRepo = new TeamsRepo();
        String teamName =  teamsRepo.getTeamName(mTeamNumber);

        initTeamNumber = (TextView) findViewById(R.id.initTeamNumber);
        initTeamNumber.setText("Team: " + mTeamNumber + " - "+ teamName);
        initTeamNumber.setTextColor(ContextCompat.getColor(initTeamNumber.getContext(),textColor));

        initMatchNumber = (TextView) findViewById(R.id.initMatchNumber);
        initMatchNumber.setText("Match: " + mMatchNumber);
        initMatchNumber.setTextColor(ContextCompat.getColor(initMatchNumber.getContext(), textColor));

        initDeviceNumber = (TextView) findViewById(R.id.initDeviceNumber);
        String deviceText = (mDeviceNumber > 0 && mDeviceNumber < 4) ? "Red " + mDeviceNumber : "Blue " + (mDeviceNumber - 3);
        initDeviceNumber.setText("Device: " + deviceText);
        initDeviceNumber.setTextColor(ContextCompat.getColor(initDeviceNumber.getContext(), textColor));

        PagerTabStrip allianceColorBar = (PagerTabStrip) findViewById(R.id.pager_title_strip);
        allianceColorBar.setDrawFullUnderline(true);
        allianceColorBar.setTabIndicatorColor(ContextCompat.getColor(allianceColorBar.getContext(), allianceColor));
        allianceColorBar.setBackgroundColor(ContextCompat.getColor(allianceColorBar.getContext(), allianceColor));

    }

    public void dialog_noShow(View view) {
        if (mInitFrag == null)
            mInitFrag = (InitFragment) getSupportFragmentManager().findFragmentByTag("tab_init");
        mInitFrag.command_noShow(view);
    }

    @Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog) {
        if (!dialog.equals(null)) {
            if (mInitFrag != null) {
                if (dialog.equals(mInitFrag.noShowDF)) {
                    mInitFrag.setNoShow(true);
                    // save all data and close the match
                    this.finishViaNoShow();
                }
            }


        }
    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {
        if (!dialog.equals(null)) {
            if (mInitFrag != null) {
                if (dialog.equals(mInitFrag.noShowDF)) {
                    mInitFrag.setNoShow(false);
                }
            }
        }
    }

    public void finishMatch(View view) {
        getSupportFragmentManager().findFragmentByTag("tab_tele").onPause();

        MatchInfo matchInfo =  new MatchInfo();
        matchInfo.setCompId(mEvent);
        matchInfo.setScoutType(mType);
        matchInfo.setCurrentMatch(mMatchNumber+1);
        matchInfo.setDeviceNum(mDeviceNumber);
        matchInfoRepo.update(matchInfo);

        ExternalStorageTools.writeDatabaseToES();
        Intent intent = new Intent(this, ScoutActivity.class);
        intent.putExtra("event", mEvent);
        intent.putExtra("type", mType);
        startActivity(intent);
        this.finish();
    }

    public void finishViaNoShow() {
        getSupportFragmentManager().findFragmentByTag("tab_init").onPause();

        MatchInfo matchInfo =  new MatchInfo();
        matchInfo.setCompId(mEvent);
        matchInfo.setScoutType(mType);
        matchInfo.setCurrentMatch(mMatchNumber+1);
        matchInfo.setDeviceNum(mDeviceNumber);
        matchInfoRepo.update(matchInfo);

        ExternalStorageTools.writeDatabaseToES();
        Intent intent = new Intent(this, ScoutActivity.class);
        intent.putExtra("event", mEvent);
        intent.putExtra("type", mType);
        startActivity(intent);
        this.finish();
    }

}