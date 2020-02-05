package com.shzhangji.invest.fundview;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shzhangji.invest.R;

public class FundViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("博时标普500ETF联接");
        actionBar.setSubtitle("050025");

        ViewPager2 viewPager = findViewById(R.id.fund_view_pager);
        viewPager.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.fund_view_tabs);
        new TabLayoutMediator(tabLayout, viewPager, this::configTab).attach();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.fund_view, menu);
        return true;
    }

    public static class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override @NonNull
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = new NetValueChartFragment();
                    break;
                case 1:
                    fragment = new ProfitChartFragment();
                    break;
                case 2:
                    fragment = new RecordListFragment();
                    break;
                default:
                    throw new IllegalArgumentException("invalid position " + position);
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

    public void configTab(TabLayout.Tab tab, int position) {
        switch (position) {
            case 0:
                tab.setText(R.string.fund_view_text_net_value_chart);
                break;
            case 1:
                tab.setText(R.string.fund_view_text_profit_chart);
                break;
            case 2:
                tab.setText(R.string.fund_view_text_record_list);
                break;
            default:
                throw new IllegalArgumentException("invalid position " + position);
        }
    }
}
