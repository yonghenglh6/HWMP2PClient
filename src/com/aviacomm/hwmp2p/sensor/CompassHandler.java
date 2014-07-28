package com.aviacomm.hwmp2p.sensor;

import com.aviacomm.hwmp2p.HWMP2PClient;
import com.aviacomm.hwmp2p.MLog;
import com.aviacomm.hwmp2p.MessageEnum;
import com.aviacomm.hwmp2p.sensor.BatteryHandler.BatteryBroadcastReceiver;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class CompassHandler implements SensorEventListener {
	static int state;
	private final int RUN = 1;
	private final int STOP = 2;

	Handler handler;
	Context context;
	// 管理传感器对象
	private android.hardware.SensorManager mSensorManager;
	// 传感器对象
	private Sensor mAccelerometer;
	private Sensor mMagnetometer;
	private float[] mLastAccelerometer = new float[3];
	private float[] mLastMagnetometer = new float[3];
	private boolean mLastAccelerometerSet = false;
	private boolean mLastMagnetometerSet = false;

	private float[] mR = new float[9];
	private float[] mOrientation = new float[3];

	// 自定义的view
//	private SampleView mView;

	public CompassHandler(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		mSensorManager = (android.hardware.SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mMagnetometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

	}

	public void start() {
		mLastAccelerometerSet = false;
		mLastMagnetometerSet = false;
		mSensorManager.registerListener(this, mAccelerometer,
				android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mMagnetometer,
				android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void stop() {
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
	private float conculateDegree(float[] orientation){
		float degree=0;
		if(mOrientation[1]==0){
			if(mOrientation[0]>0)
				degree=(float) (Math.PI/2);
			else if(mOrientation[0]<0)
				degree=-(float) (Math.PI/2);
		}else{
			degree=(float) Math.atan(mOrientation[0]/mOrientation[1]);
		}
		degree=(float) (degree*180/Math.PI);
		return degree;
	}
	public static int tolog_count=0;
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == mAccelerometer) {
			System.arraycopy(event.values, 0, mLastAccelerometer, 0,
					event.values.length);
			mLastAccelerometerSet = true;
		} else if (event.sensor == mMagnetometer) {
			System.arraycopy(event.values, 0, mLastMagnetometer, 0,
					event.values.length);
			mLastMagnetometerSet = true;
		}
		if (mLastAccelerometerSet && mLastMagnetometerSet) {
			android.hardware.SensorManager.getRotationMatrix(mR, null,
					mLastAccelerometer, mLastMagnetometer);
			android.hardware.SensorManager.getOrientation(mR, mOrientation);
			float degree=conculateDegree(mOrientation);
			if(tolog_count++%20==0){
				HWMP2PClient.log.i(String.format("Orientation: %f, %f, %f %f\n",
					mOrientation[0], mOrientation[1], mOrientation[2],degree));
			}
			handler.obtainMessage(MessageEnum.ORIENTATIONCHANGE,Float.valueOf(degree))
			.sendToTarget();
		}
	}
	/*
	 * private class SampleView extends View { private Paint mPaint = new
	 * Paint(); private Path mPath = new Path();
	 * 
	 * // private boolean mAnimate; // private long mNextTime;
	 * 
	 * public SampleView(Context context) { super(context); // Construct a
	 * wedge-shaped path mPath.moveTo(0, -50); mPath.lineTo(-20, 60);
	 * mPath.lineTo(0, 50); mPath.lineTo(20, 60); mPath.close(); }
	 * 
	 * @Override protected void onDraw(Canvas canvas) { Paint paint = mPaint;
	 * canvas.drawColor(Color.WHITE); paint.setAntiAlias(true);
	 * paint.setColor(Color.BLACK); paint.setStyle(Paint.Style.FILL); int w =
	 * canvas.getWidth(); int h = canvas.getHeight(); int cx = w / 2; int cy = h
	 * / 2; canvas.translate(cx, cy); if (mValues != null) {
	 * canvas.rotate(-mValues[0]); } canvas.drawPath(mPath, mPaint); }
	 * 
	 * @Override protected void onAttachedToWindow() { // mAnimate = true;
	 * super.onAttachedToWindow(); }
	 * 
	 * @Override protected void onDetachedFromWindow() { // mAnimate = false;
	 * super.onDetachedFromWindow(); } }
	 */
}