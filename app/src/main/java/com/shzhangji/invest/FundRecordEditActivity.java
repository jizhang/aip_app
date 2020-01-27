package com.shzhangji.invest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FundRecordEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_record_edit);
    }

    public void showConfirmDatePicker(View view) {
        EditText editText = (EditText) view;
        String dateString = editText.getText().toString();
        Calendar current = Calendar.getInstance();
        if (!TextUtils.isEmpty(dateString)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                current.setTime(date);
            } catch (Exception e) {
                // noop
            }
        }

        DialogFragment newFragment = new DatePickerFragment(current, calendar -> {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            editText.setText(date);
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private Calendar calendar;
        private Callback callback;

        public DatePickerFragment(Calendar calendar, Callback callback) {
            this.calendar = calendar;
            this.callback = callback;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, dayOfMonth);
            callback.call(c);
        }

        public interface Callback {
            void call(Calendar calendar);
        }
    }
}
