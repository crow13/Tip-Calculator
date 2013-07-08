package com.jwhitaker.mytipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main extends Activity {
    private static final String BILL_TOTAL = "BILL_TOTAL";
    private static final String CURRENT_PERCENTAGE = "CURRENT_PERCENTAGE";

    private double currentBill;
    private int currentCustomPercent;
    private EditText tip10EditText;
    private EditText total10EditText;
    private EditText tip15EditText;
    private EditText total15EditText;
    private EditText tip20EditText;
    private EditText total20EditText;
    private EditText billEditText;
    private TextView customTipTextView;
    private EditText tipCustomEditView;
    private EditText totalCustomEditView;
    private SeekBar customSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if(savedInstanceState == null){
            currentBill = 0.0;
            currentCustomPercent = 18;
        } else {
            currentBill = savedInstanceState.getDouble(BILL_TOTAL);
            currentCustomPercent = savedInstanceState.getInt(CURRENT_PERCENTAGE);
        }

        tip10EditText = (EditText) findViewById(R.id.tip10text);
        tip15EditText = (EditText) findViewById(R.id.tip15text);
        tip20EditText = (EditText) findViewById(R.id.tip20text);
        total10EditText = (EditText) findViewById(R.id.tip10TotalText);
        total15EditText = (EditText) findViewById(R.id.tip15TotalText);
        total20EditText = (EditText) findViewById(R.id.tip20TotalText);
        billEditText = (EditText) findViewById(R.id.editText);
        customTipTextView = (TextView) findViewById(R.id.customTipText);
        tipCustomEditView = (EditText) findViewById(R.id.customTipAmountText);
        totalCustomEditView = (EditText) findViewById(R.id.customTipTotalText);
        customSeekBar = (SeekBar) findViewById(R.id.seekBar);
        billEditText.addTextChangedListener(billEditTextWatcher);

        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
    }

    private void updateStandard() {
        double tenPercentTip = currentBill * .1;
        double fifteenPercentTip = currentBill * .15;
        double twentyPercentTip = currentBill * .2;
        double tenPercentTotal = currentBill + tenPercentTip;
        double fifteenPercentTotal = currentBill + fifteenPercentTip;
        double twentyPercentTotal = currentBill + twentyPercentTip;

        tip10EditText.setText(String.format("%.02f",tenPercentTip));
        tip15EditText.setText(String.format("%.02f",fifteenPercentTip));
        tip20EditText.setText(String.format("%.02f",twentyPercentTip));
        total10EditText.setText(String.format("%.02f",tenPercentTotal));
        total15EditText.setText(String.format("%.02f",fifteenPercentTotal));
        total20EditText.setText(String.format("%.02f",twentyPercentTotal));
    }

    private void updateCustom() {
        customTipTextView.setText(currentCustomPercent + "%");
        double customTipAmount = currentBill * currentCustomPercent * .01;
        double customTotalAmount = currentBill + customTipAmount;
        tipCustomEditView.setText(String.format("%.02f",customTipAmount));
        totalCustomEditView.setText(String.format("%.02f",customTotalAmount));
    }

    private SeekBar.OnSeekBarChangeListener customSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            currentCustomPercent = seekBar.getProgress();
            updateCustom();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private TextWatcher billEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                currentBill = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e) {
                currentBill = 0.0;
            }
            updateStandard();
            updateCustom();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble(BILL_TOTAL,currentBill);
        outState.putInt(CURRENT_PERCENTAGE,currentCustomPercent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
