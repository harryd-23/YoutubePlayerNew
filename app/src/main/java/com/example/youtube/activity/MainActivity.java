package com.example.youtube.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.example.youtube.R;
import com.example.youtube.adapter.ViewPagerAdapter;
import com.example.youtube.base.BaseActivity;
import com.example.youtube.fragments.AllListVideoFragment;
import com.example.youtube.fragments.FavouriteFragment;
import com.example.youtube.fragments.HistoryFragment;
import com.example.youtube.fragments.SocialFragment;
import com.example.youtube.model.channel.ChannelInfo;
import com.example.youtube.presenter.MainViewPresenter;
import com.example.youtube.presenteriplm.MainViewPresenterIplm;
import com.example.youtube.view.MainView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.youtube.utils.Utils.API_KEY;
import static com.example.youtube.utils.Utils.CHANNEL_ID;

public class MainActivity extends BaseActivity implements MainView{
    private MainViewPresenter mainViewPresenter;
    private AllListVideoFragment allVideoFragment;
    private HistoryFragment historyFragment;
    private FavouriteFragment favouriteFragment;
    private SocialFragment socialFragment;
    private ViewPagerAdapter viewPagerAdapter;
    private static final int REQUEST_PERMISSION_CODE = 123;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appBar)
    AppBarLayout appBar;

    @BindView(R.id.tvChannelTitle)
    AppCompatTextView tvChannelTitle;

    @BindView(R.id.imgChannelIcon)
    SimpleDraweeView imgChannelIcon;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.llError)
    LinearLayoutCompat llError;

    @OnClick(R.id.imgSearch)
    void search(){
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

    @Override
    public void getChannelInfoSuccess(ChannelInfo channelInfo) {
        if (!compositeDisposable.isDisposed()) {
            tvChannelTitle.setText(channelInfo.getSnippet().getTitle().toString());
            Uri uri = Uri.parse(channelInfo.getSnippet().getThumbnails().getDefault().getUrl().toString());
            imgChannelIcon.setImageURI(uri);
        }
    }

    @Override
    public void getChannelInfoError() {
        if (!compositeDisposable.isDisposed()){
            llError.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void getExtraData() {
        if (!checkPermissionOnDevice()){
            requestPermision();
        }
    }

    @Override
    public void createPresenter() {
        mainViewPresenter = new MainViewPresenterIplm(this, compositeDisposable);
    }

    @Override
    public void createAdapter() {
        setSupportActionBar(toolbar);
        setUpViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment fragment = fm.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + tab.getPosition());
                if (fragment instanceof FavouriteFragment){
                    ((FavouriteFragment) fragment).refreshData();
                } else if (fragment instanceof HistoryFragment){
                    ((HistoryFragment) fragment).refreshData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void loadData() {
        mainViewPresenter.getChannelInfo(CHANNEL_ID, API_KEY);
    }

    private void setUpViewPager(ViewPager viewPager){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        allVideoFragment = new AllListVideoFragment();
        favouriteFragment = new FavouriteFragment();
        historyFragment = new HistoryFragment();
        socialFragment = new SocialFragment();

        viewPagerAdapter.addFragment(allVideoFragment, "All videos");
        viewPagerAdapter.addFragment(favouriteFragment, "Favourites");
        viewPagerAdapter.addFragment(historyFragment, "History");
        viewPagerAdapter.addFragment(socialFragment, "Social");

        viewPager.setOffscreenPageLimit(10);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void requestPermision() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.INTERNET
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private boolean checkPermissionOnDevice() {
        int internet_result = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        return internet_result == PackageManager.PERMISSION_GRANTED;
    }
}
