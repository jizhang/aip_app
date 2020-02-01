package com.shzhangji.invest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FundViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_view);

        getSupportActionBar().setTitle("博时标普500ETF联接");
        getSupportActionBar().setSubtitle("050025");

        ViewPager2 viewPager = findViewById(R.id.fund_view_pager);
        viewPager.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.fund_view_tabs);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
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
        }).attach();
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

    public static class NetValueChartFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fund_view_net_value_chart, container, false);
            TextView holder = view.findViewById(R.id.fund_view_holder_net_value_chart);
            MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.fund_view_button_periods);
            toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (!isChecked) {
                    return;
                }
                int period;
                switch (checkedId) {
                    case R.id.fund_view_button_period_3_months:
                        period = R.string.fund_view_button_period_3_months;
                        break;
                    case R.id.fund_view_button_period_6_months:
                        period = R.string.fund_view_button_period_6_months;
                        break;
                    case R.id.fund_view_button_period_1_year:
                        period = R.string.fund_view_button_period_1_year;
                        break;
                    case R.id.fund_view_button_period_3_years:
                        period = R.string.fund_view_button_period_3_years;
                        break;
                    default:
                        throw new IllegalArgumentException("invalid id " + checkedId);
                }
                holder.setText(getString(R.string.fund_view_text_holder,
                        getString(period),
                        getString(R.string.fund_view_text_net_value_chart)));
            });
            toggleGroup.check(R.id.fund_view_button_period_3_months);
            return view;
        }
    }

    public static class ProfitChartFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fund_view_profit_chart, container, false);
            TextView holder = view.findViewById(R.id.fund_view_holder_profit_chart);
            MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.fund_view_button_periods);
            toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (!isChecked) {
                    return;
                }
                int period;
                switch (checkedId) {
                    case R.id.fund_view_button_period_3_months:
                        period = R.string.fund_view_button_period_3_months;
                        break;
                    case R.id.fund_view_button_period_6_months:
                        period = R.string.fund_view_button_period_6_months;
                        break;
                    case R.id.fund_view_button_period_1_year:
                        period = R.string.fund_view_button_period_1_year;
                        break;
                    case R.id.fund_view_button_period_3_years:
                        period = R.string.fund_view_button_period_3_years;
                        break;
                    default:
                        throw new IllegalArgumentException("invalid id " + checkedId);
                }
                holder.setText(getString(R.string.fund_view_text_holder,
                        getString(period),
                        getString(R.string.fund_view_text_profit_chart)));
            });
            toggleGroup.check(R.id.fund_view_button_period_3_months);
            return view;
        }
    }

    public static class RecordListFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_fund_view_record_list, container, false);
        }
    }
}
