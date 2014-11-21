package com.fmsirvent.transformabledrawablebuttonsample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmsirvent.transformabledrawablebutton.TransformableButton;

public class MainActivity extends Activity {

    private TextView textView;
    private TransformableButton transformableButton;
    private LinearLayout linearLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        textView = ((TextView) findViewById(R.id.thanks_testing));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(getString(R.string.thanks_testing)));

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 38, r.getDisplayMetrics());

        linearLayout = ((LinearLayout) findViewById(R.id.programaly_added));

        transformableButton = new TransformableButton(this, TransformableButton.Draw.MINUS, TransformableButton.Draw.PLUS, Color.BLACK, Color.GRAY, TransformableButton.PositionDraw.BACKGROUND);
        transformableButton.setOnCheckedChangeListener(new TransformableButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(TransformableButton buttonView, boolean isChecked) {
                Toast.makeText(context, (isChecked) ? "Checked" : "Unchecked", Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.addView(transformableButton, px, px);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transformableButton.setChecked(true, true);
            }
        }, 5000);

    }
}
