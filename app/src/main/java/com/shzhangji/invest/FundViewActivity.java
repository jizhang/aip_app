package com.shzhangji.invest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.fund_view_button_periods);
            toggleGroup.check(R.id.fund_view_button_period_3_months);

            LineChart chart = view.findViewById(R.id.fund_view_holder_net_value_chart);
            chart.setDescription(null);
            chart.setDrawBorders(false);
            chart.setHighlightPerDragEnabled(false); // not working 'cause conflict with ViewPager2, ScrollView.
            chart.setDoubleTapToZoomEnabled(false);

            Legend legend = chart.getLegend();
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);

            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format("%+.2f%%", value * 100);
                }
            });

            mockData(view, chart);
            chart.invalidate();
            return view;
        }

        private void mockData(View view, LineChart chart) {
            List<NetValueItem> items = new ArrayList<>();
            LocalDate currentDate = LocalDate.now().minusDays(1);
            int limit = 60;
            Random random = new Random();
            while (items.size() < limit) {
                if (currentDate.getDayOfWeek().getValue() <= 5) {
                    NetValueItem item = new NetValueItem();
                    item.index = limit - items.size() - 1;
                    item.percent = random.nextFloat() * 0.2f - 0.1f;
                    item.netValue = 1 + random.nextFloat() * 0.5f;
                    item.date = currentDate;
                    items.add(item);
                }
                currentDate = currentDate.minusDays(1);
            }
            Collections.reverse(items);

            List<Entry> entries = items.stream()
                    .map(i -> new Entry(i.index, i.percent))
                    .collect(Collectors.toList());

            LineDataSet dataSet = new LineDataSet(entries, "本产品 +12.34%");
            dataSet.setColor(getActivity().getColor(R.color.textUp));
            dataSet.setDrawValues(false);
            dataSet.setDrawCircles(false);
            dataSet.setLineWidth(2);
            dataSet.setDrawHorizontalHighlightIndicator(false);
            LineData data = new LineData(dataSet);

            chart.getXAxis().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    LocalDate date = items.get((int) value).date;
                    return date.format(DateTimeFormatter.ofPattern("M.d"));
                }
            });

            View highlight = view.findViewById(R.id.fund_view_layout_highlight);
            TextView highlightDate = view.findViewById(R.id.fund_view_text_highlight_date);
            TextView highlightNetValue = view.findViewById(R.id.fund_view_text_highlight_net_value);
            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    NetValueItem item = items.get((int) e.getX());
                    highlightDate.setText(item.date.format(DateTimeFormatter.ISO_DATE));
                    highlightNetValue.setText(getActivity().getString(
                            R.string.fund_view_text_highlight_net_value,
                            String.format("%.4f", item.netValue)));
                    highlight.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected() {
                    highlight.setVisibility(View.INVISIBLE);
                }
            });

            chart.setData(data);
        }
    }

    public static class NetValueItem {
        int index;
        float percent;
        float netValue;
        LocalDate date;
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
