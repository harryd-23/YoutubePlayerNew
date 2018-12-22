package com.example.youtube.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.youtube.R;
import com.example.youtube.base.BaseFragment;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import butterknife.BindView;

import static com.example.youtube.utils.Utils.TWITTER_NAME;

public class SocialFragment extends BaseFragment {
    @BindView(R.id.rvListTweet)
    RecyclerView rvListTweet;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    UserTimeline userTimeline;

    @Override
    public int initLayout() {
        return R.layout.fragment_social;
    }

    @Override
    public void getExtraData() {
        userTimeline = new UserTimeline.Builder()
                .screenName(TWITTER_NAME)
                .maxItemsPerRequest(50)
                .build();
        rvListTweet.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void createAdapter() {
        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this.getContext())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();
        rvListTweet.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "Tweets refreshed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
            }
        });
    }

    @Override
    public void loadData() {

    }
}
