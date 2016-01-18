package com.fos.trackingapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fos.trackingapplication.adapter.CardViewAdapter;
import com.fos.trackingapplication.controller.PreferenceService;
import com.fos.trackingapplication.model.LocationModel;
import com.fos.trackingapplication.service.TrackingService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    private PreferenceService mPreferenceService;
    private CardViewAdapter mCardViewAdapter;
    private BroadcastReceiver mBroadcastReceiver;
    private Intent intentService;
    private List<LocationModel> allModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // init service
        intentService = new Intent(getBaseContext(), TrackingService.class);

        // init BroadcastReceiver to get change from service
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notifyUIFromService();
            }
        };

        // init preferences
        mPreferenceService = PreferenceService.getInstance(this);
        allModels = new ArrayList<>();

        // get count of data in prefs
        int dataCount = mPreferenceService.getCount();
        if(dataCount > 0) {

            // create array with models
            for(int i = dataCount - 1; i >= 0 ; i--) {
                LocationModel model = new LocationModel();
                model.setLatitude(mPreferenceService.getLtdString(i));
                model.setLongitude(mPreferenceService.getLngString(i));
                allModels.add(model);
            }
        }
        setRecyclerView();
    }


    @OnClick(R.id.btn_start_service)
    public void startService() {
        startService(intentService);
    }


    @OnClick(R.id.btn_stop_service)
    public void stopService() {
        stopService(intentService);
    }


    private void setRecyclerView() {
        // set RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCardViewAdapter = new CardViewAdapter(allModels);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCardViewAdapter);
    }


    private void notifyUIFromService() {
            int newPosition = mPreferenceService.getCount();

            if(newPosition > 0) {
                LocationModel model = new LocationModel();
                model.setLatitude(mPreferenceService.getLtdString(newPosition -1));
                model.setLongitude(mPreferenceService.getLngString(newPosition - 1));

                allModels.add(0, model);

                mCardViewAdapter.notifyItemInserted(model);
            }
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mBroadcastReceiver),
                new IntentFilter(TrackingService.RESULT)
        );
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }


}
