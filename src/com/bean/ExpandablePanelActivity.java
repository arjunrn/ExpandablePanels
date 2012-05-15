package com.bean;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandablePanelActivity extends Activity {

	private FrameLayout frameLayout;
	private TextView textView;
	private LinearLayout parentLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String text = new String(
				"Of course. In my case, view2Measure - is TextView. view2Expand - FrameLayout which contains TextView. We measure full height of text view and apply it to FrameLayout. FrameLayout is presented for fade effect when text is collapsed");

		parentLayout = (LinearLayout)findViewById(R.id.parent_layout);
		frameLayout = (FrameLayout) findViewById(R.id.frame);
		textView = (TextView) findViewById(R.id.text);
		textView.setText(text);
		final int collapsedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
		

		parentLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				expandOrCollapse(frameLayout, textView, collapsedHeight);				
			}
		});

	}

	private static int measureViewHeight(View view2Expand, View view2Measure) {
		try {
			Method m = view2Measure.getClass().getDeclaredMethod("onMeasure",
					int.class, int.class);
			m.setAccessible(true);
			m.invoke(view2Measure, MeasureSpec.makeMeasureSpec(
					view2Expand.getWidth(), MeasureSpec.AT_MOST), MeasureSpec
					.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		} catch (Exception e) {
			return -1;
		}

		int measuredHeight = view2Measure.getMeasuredHeight();
		return measuredHeight;
	}

	static public void expandOrCollapse(View view2Expand, View view2Measure,
			int collapsedHeight) {
		if (view2Expand.getHeight() < collapsedHeight)
			return;

		int measuredHeight = measureViewHeight(view2Expand, view2Measure);

		if (measuredHeight < collapsedHeight)
			measuredHeight = collapsedHeight;

		final int startHeight = view2Expand.getHeight();
		final int finishHeight = startHeight <= collapsedHeight ? measuredHeight
				: collapsedHeight;

		view2Expand.startAnimation(new ExpandAnimation(view2Expand,
				startHeight, finishHeight));
	}

}