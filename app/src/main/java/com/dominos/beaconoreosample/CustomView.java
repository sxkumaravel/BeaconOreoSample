package com.dominos.beaconoreosample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created on 6/11/18.
 *
 * @author kumars
 */
public class CustomView extends LinearLayout {

    private String mText;
    private int mCount;

    public CustomView(Context context, String text, int count) {
        super(context);
        mText = text;
        mCount = count;
        onFinishInflate();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        inflate(getContext(), R.layout.view_custom, this);
        ((TextView) findViewById(R.id.cv_count)).setText(String.valueOf(mCount));
        ((TextView) findViewById(R.id.cv_details)).setText(mText);
        super.onFinishInflate();
    }
}
