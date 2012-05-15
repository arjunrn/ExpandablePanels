package com.bean;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class ExpandAnimation extends Animation {
	private final View _view;
	private final int _startHeight;
	private final int _finishHeight;

	public ExpandAnimation(View view, int startHeight, int finishHeight) {
		_view = view;
		_startHeight = startHeight;
		_finishHeight = finishHeight;
		setDuration(220);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final int newHeight = (int) ((_finishHeight - _startHeight)
				* interpolatedTime + _startHeight);
		_view.getLayoutParams().height = newHeight;
		_view.requestLayout();
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
	}

	@Override
	public boolean willChangeBounds() {
		return true;
	}
}