package com.szw.autoscrollview.anim;

import android.graphics.PixelFormat;
import android.view.SurfaceView;

/**
 * ��surfaceView����һЩ���� ����һ������
 * 
 * @author gary
 * 
 */
public class HolderSurfaceView {
	private HolderSurfaceView() {

	}

	private static HolderSurfaceView mHolderSurfaceView = null;

	public static HolderSurfaceView getInstance() {
		if (mHolderSurfaceView == null)
			mHolderSurfaceView = new HolderSurfaceView();
		return mHolderSurfaceView;
	}

	private SurfaceView mSurfaceView;

	/**
	 * ����SurfaceView
	 * 
	 * @param view
	 */
	public void setSurfaceView(SurfaceView view) {
		mSurfaceView = view;
		mSurfaceView.setZOrderOnTop(true);
		mSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}

	public SurfaceView getSurfaceView() {
		return mSurfaceView;
	}
}
