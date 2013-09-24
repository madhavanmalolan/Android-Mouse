package com.madhavan93.androidmouse;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouchEventView extends View {

	private boolean isClick = false;
	public static Intent intent = null;
//	private TouchPadActivity tpa = new TouchPadActivity();

	public MultiTouchEventView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		Log.i("DEB", ".....");

		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			isClick = true;
			Log.i("EVENT", "DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			isClick = false;
			Log.i("EVENT", "MOVE");
			
			break;
		case MotionEvent.ACTION_UP:
			TouchPadActivity tpa = new TouchPadActivity();
			Log.i("EVENT", Boolean.toString(isClick));

		}
		/*
		 * 
		 * if ((action & MotionEvent.ACTION_MASK) ==
		 * MotionEvent.ACTION_POINTER_){ int actionEvent = action &
		 * MotionEvent.ACTION_MASK; int actionPointerId= (action &
		 * MotionEvent.ACTION_POINTER_INDEX_MASK)>>
		 * MotionEvent.ACTION_POINTER_INDEX_SHIFT; Log.i("MOVE",
		 * Integer.toString(actionPointerId));
		 * 
		 * }
		 */

		return true;
	}
}