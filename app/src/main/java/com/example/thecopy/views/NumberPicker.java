package com.example.thecopy.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NumberPicker extends LinearLayout {

    private final long REPEAT_DELAY = 50;

    private final int ELEMENT_HEIGHT = 50;
    private final int ELEMENT_WIDTH = ELEMENT_HEIGHT; // you're all squares, yo

    private int minValue = 0;
    private int maxValue = 999;

    public Integer value;

    Button decrement;
    Button increment;
    public TextView valueText;

    private Handler repeatUpdateHandler = new Handler();

    private boolean autoIncrement = false;
    private boolean autoDecrement = false;

    private boolean listentingToScoutActivity = false;
    private boolean enabled = true;

    /**
     * This little guy handles the auto part of the auto incrementing feature.
     * In doing so it instantiates itself. There has to be a pattern name for
     * that...
     *
     * @author Jeffrey F. Cole
     */
    class RepetetiveUpdater implements Runnable {
        public void run() {
            if (autoIncrement) {
                increment();
                repeatUpdateHandler.postDelayed(new RepetetiveUpdater(), REPEAT_DELAY);
            } else if (autoDecrement) {
                decrement();
                repeatUpdateHandler.postDelayed(new RepetetiveUpdater(), REPEAT_DELAY);
            }
        }
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        LayoutParams elementParams = new LayoutParams(ELEMENT_HEIGHT, ELEMENT_WIDTH);

        // init the individual elements
        initDecrementButton(context);
        initValueTextView(context);
        initIncrementButton(context);

        // Can be configured to be vertical or horizontal
        // Thanks for the help, LinearLayout!
        if (getOrientation() == VERTICAL) {
            addView(increment, elementParams);
            addView(valueText, elementParams);
            addView(decrement, elementParams);
        } else {
            addView(decrement, elementParams);
            addView(valueText, elementParams);
            addView(increment, elementParams);
        }
    }

    private void initIncrementButton(Context context) {
        increment = new Button(context);
        increment.setTextSize(25);
        increment.setText("+");

        // Increment once for a click
        increment.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                increment();
            }
        });

        // Auto increment for a long click
//        increment.setOnLongClickListener(
//                new OnLongClickListener(){
//                    public boolean onLongClick(View arg0) {
//                        autoIncrement = true;
//                        repeatUpdateHandler.post( new RepetetiveUpdater() );
//                        return false;
//                    }
//                }
//        );

        // When the button is released, if we're auto incrementing, stop
//        increment.setOnTouchListener( new OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if( event.getAction() == MotionEvent.ACTION_UP && autoIncrement ){
//                    autoIncrement = false;
//                }
//                return false;
//            }
//        });
    }

    private void initValueTextView(Context context) {

        value = new Integer(minValue);

        valueText = new TextView(context);
        valueText.setTextSize(25);
        valueText.setTextColor(getResources().getColor(R.color.Goldish));

        valueText.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        valueText.setText(value.toString());
    }

    private void initDecrementButton(Context context) {
        decrement = new Button(context);
        decrement.setTextSize(25);
        decrement.setText("-");


        // Decrement once for a click
        decrement.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                decrement();
            }
        });


        // Auto Decrement for a long click
//        decrement.setOnLongClickListener(
//                new OnLongClickListener(){
//                    public boolean onLongClick(View arg0) {
//                        autoDecrement = true;
//                        repeatUpdateHandler.post( new RepetetiveUpdater() );
//                        return false;
//                    }
//                }
//        );

//        // When the button is released, if we're auto decrementing, stop
//        decrement.setOnTouchListener( new OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if( event.getAction() == MotionEvent.ACTION_UP && autoDecrement ){
//                    autoDecrement = false;
//                }
//                return false;
//            }
//        });
    }

    public void increment() {
        if (value < maxValue) {
            value = value + 1;
            valueText.setText(value.toString());
//            if(listentingToScoutActivity)
//                ScoutActivity.upDateTeam(value);
        }
    }

    public void decrement() {
        if (value > minValue) {
            value = value - 1;
            valueText.setText(value.toString());
//            if(listentingToScoutActivity)
//                ScoutActivity.upDateTeam(value);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value > maxValue) value = maxValue;
        if (value >= minValue) {
            this.value = value;
            valueText.setText(this.value.toString());
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        valueText.setEnabled(enabled);
        decrement.setEnabled(enabled);
        increment.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void something(boolean state) {
        listentingToScoutActivity = state;
    }

}
