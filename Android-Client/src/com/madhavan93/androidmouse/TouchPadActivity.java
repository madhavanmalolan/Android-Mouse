package com.madhavan93.androidmouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchPadActivity extends Activity implements
		GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

	private static final String DEBUG_TAG = "Gestures";
	private GestureDetectorCompat mDetector;

	private float scroll_prev_x;
	private float scroll_prev_y;
	
	private float scroll_prev_down_x = 0 ;
	private float scroll_prev_down_y = 0;
	
	
	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_pad);
		// Instantiate the gesture detector with the
		// application context and an implementation of
		// GestureDetector.OnGestureListener
		mDetector = new GestureDetectorCompat(this, this);
		// Set the gesture detector as the double tap
		// listener.
		mDetector.setOnDoubleTapListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);
		// Be sure to call the superclass implementation
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDown: " + event.toString());
		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
		return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
		Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
		String JSONGesture = "{\"action\":\"CLICK_RIGHT\"}";
		sendGesture(JSONGesture);
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
		if(scroll_prev_down_x != e1.getX()){
			scroll_prev_x = e1.getX();
			scroll_prev_down_x = e1.getX();
		}
		
		if(scroll_prev_down_y != e1.getY()){
			scroll_prev_y = e1.getY();
			scroll_prev_down_y = e1.getY();
		}
		
		String JSONGesture = "{" +
				"\"action\": \"SCROLL\"," +
				"\"movement\":["+Float.toString(e2.getX()-scroll_prev_x)+","+Float.toString(e2.getY()-scroll_prev_y)+"],"+
				"\"pointer_count\":"+Integer.toString(e2.getPointerCount())+
				"}";
		
		scroll_prev_x = e2.getX();
		scroll_prev_y = e2.getY();
		
		sendGesture(JSONGesture);
				
		
		

		return true;
	}

	@Override
	public void onShowPress(MotionEvent event) {
		Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
		String JSONGesture = "{\"action\":\"CLICK_DOUBLE\"}";
		sendGesture(JSONGesture);
		
		

		
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
		
		String JSONGesture = "{\"action\":\"CLICK_DOUBLE\"}";
		sendGesture(JSONGesture);
		
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
		String JSONGesture = "{\"action\":\"CLICK_SINGLE\"}";
		sendGesture(JSONGesture);
		return true;
	}
	
	void sendGesture(String JSONGesture){
		Intent intent = new Intent(this, PushService.class);
		
		intent.putExtra("GESTURE",JSONGesture);
		startService(intent);

	}
}