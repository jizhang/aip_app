package com.shzhangji.invest.fundview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.shzhangji.invest.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NetValueChartFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fund_view_net_value_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.fund_view_button_periods);
        toggleGroup.check(R.id.fund_view_button_period_3_months);

        List<NetValueItem> items = mockItems();
        LineChart chart = view.findViewById(R.id.fund_view_holder_net_value_chart);
        chart.setData(createLineData(items));

        configChart(chart);
        configXAxis(chart, items);
        configYAxis(chart);
        configLegend(chart);
        configHighlight(view, chart, items);

        chart.invalidate();
    }

    private void configChart(LineChart chart) {
        chart.setDescription(null);
        chart.setDrawBorders(false);
        chart.setHighlightPerDragEnabled(false); // not working 'cause conflict with ViewPager2, ScrollView.
        chart.setDoubleTapToZoomEnabled(false);
    }

    private void configXAxis(LineChart chart, List<NetValueItem> items) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                LocalDate date = items.get((int) value).date;
                return date.format(DateTimeFormatter.ofPattern("M.d"));
            }
        });
    }

    private void configYAxis(LineChart chart) {
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%+.2f%%", value * 100);
            }
        });
    }

    private void configLegend(LineChart chart) {
        Legend legend = chart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    }

    private void configHighlight(View view, LineChart chart, List<NetValueItem> items) {
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
    }

    private LineData createLineData(List<NetValueItem> items) {
        List<Entry> entries = items.stream()
                .map(i -> new Entry(i.index, i.percent))
                .collect(Collectors.toList());

        LineDataSet dataSet = new LineDataSet(entries, "本产品 +12.34%");
        dataSet.setColor(getActivity().getColor(R.color.textUp));
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(2);
        dataSet.setDrawHorizontalHighlightIndicator(false);
        return new LineData(dataSet);
    }

    static class NetValueItem {
        int index;
        float percent;
        float netValue;
        LocalDate date;
    }

    private List<NetValueItem> mockItems() {
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
        return items;
    }
}
