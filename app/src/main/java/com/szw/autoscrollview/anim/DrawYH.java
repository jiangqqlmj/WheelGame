package com.szw.autoscrollview.anim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;


import com.szw.autoscrollview.R;

import java.util.Random;

public class DrawYH extends DrawTopBase {

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceCreated(holder);
		this.begin();
		stopBGMedia();
		bgMedia = new GameSoundMedia(mContext);
//		bgMedia.playInMediaPlayer(R.raw.bg, null);
	}

	private void stopBGMedia() {
		if (bgMedia != null) {
			try {
				bgMedia.stopAll();				
				bgMedia = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopBGMedia();
		super.surfaceDestroyed(holder);
	}

	@Override
	public void set() {
		super.set();
		if (this.getRect().width() > 10 && this.getRect().height() > 10)
			init();
	}

	public DrawYH() {
		super();
		m_mouseX = 0;
		m_mouseY = 0;
		m_sleepTime = 5;
		isError = false;
		isInitialized = false;
		rand = new Random();
		bits = 10000;
		bit_px = new double[bits];
		bit_py = new double[bits];
		bit_vx = new double[bits];
		bit_vy = new double[bits];
		bit_sx = new int[bits];
		bit_sy = new int[bits];
		bit_l = new int[bits];
		bit_f = new int[bits];
		bit_p = new int[bits];
		bit_c = new int[bits];
		ru = 50;
		rv = 50;
		// init();
	}

	Canvas mCanvas;
	private Rect drawRect = new Rect(0, 0, 0, 0);
	GameSoundMedia sm;

	private GameSoundPool soundPool;
	private int id_sound1 = R.raw.firework;
	private int id_sound2 = R.raw.syu;
	GameSoundMedia bgMedia;

	public void init() {
		soundPool = new GameSoundPool(mContext);
		// Log.i("gary", mContext.toString());
		soundPool.loadSfx(R.raw.firework, id_sound1);
		soundPool.loadSfx(R.raw.syu, id_sound2);
		// soundPool.loadSfx(R.raw.bg, id_bg);

		m_nAppX = 200;
		m_nAppY = 300;
		Log.i("...........>>>",m_nAppX+"?"+m_nAppY);
		drawRect = new Rect(0, 0, m_nAppX, m_nAppY);
		m_centerX = m_nAppX / 2;
		m_centerY = m_nAppY / 2;
		m_mouseX = m_centerX;
		m_mouseY = m_centerY;
		// resize(m_nAppX, m_nAppY);
		pixls = m_nAppX * m_nAppY;
		pixls2 = pixls - m_nAppX * 2;
		pix0 = new int[pixls];
		offImage = Bitmap.createBitmap(m_nAppX, m_nAppY,
				Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas();
		mCanvas.setBitmap(offImage);
		for (int i = 0; i < pixls; i++)
			pix0[i] = 0xff000000;

		// sound1 = getAudioClip(getDocumentBase(), "firework.au");
		// sound2 = getAudioClip(getDocumentBase(), "syu.au");
		for (int j = 0; j < bits; j++)
			bit_f[j] = 0;

		isInitialized = true;
	}

	@Override
	protected void doWork(Canvas canvas) {
		// TODO Auto-generated method stub
		super.doWork(canvas);
		if (offImage != null)
			canvas.drawBitmap(offImage, drawRect, this.getRect(), mPaint);
		 
	}

	@Override
	protected void change() {
		super.change();
		if (isInitialized) {
			for (int j = 0; j < pixls2; j++) {
				int k = pix0[j];
				int l = pix0[j + 1];
				int i1 = pix0[j + m_nAppX];
				int j1 = pix0[j + m_nAppX + 1];
				int i = (k & 0xff0000) >> 16;
				int k1 = ((((l & 0xff0000) >> 16) - i) * ru >> 8) + i;
				i = (k & 0xff00) >> 8;
				int l1 = ((((l & 0xff00) >> 8) - i) * ru >> 8) + i;
				i = k & 0xff;
				int i2 = (((l & 0xff) - i) * ru >> 8) + i;
				i = (i1 & 0xff0000) >> 16;
				int j2 = ((((j1 & 0xff0000) >> 16) - i) * ru >> 8) + i;
				i = (i1 & 0xff00) >> 8;
				int k2 = ((((j1 & 0xff00) >> 8) - i) * ru >> 8) + i;
				i = i1 & 0xff;
				int l2 = (((j1 & 0xff) - i) * ru >> 8) + i;
				int i3 = ((j2 - k1) * rv >> 8) + k1;
				int j3 = ((k2 - l1) * rv >> 8) + l1;
				int k3 = ((l2 - i2) * rv >> 8) + i2;				
				pix0[j] = i3 << 16 | j3 << 8 | k3 | 0x77000000;
			}

			rend();

			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setStrokeWidth(0.001f);
			mCanvas.drawBitmap(pix0, 0, m_nAppX, 0.01f, 0.01f, m_nAppX, m_nAppY, true,
					mPaint);
		}
	}

	public void dot() {
		int maxX  =150;
		int minX = 00;

		int max  =200;
		int min = 20;

		dot(rand.nextInt(maxX) % (maxX - minX + 1) + min, rand.nextInt(max) % (max - min + 1) + min);
	}

	public void dot(int x, int y) {
		m_mouseX = x;
		m_mouseY = y;
		int k = (int) (rand.nextDouble() * 256D);
		int l = (int) (rand.nextDouble() * 256D);
		int i1 = (int) (rand.nextDouble() * 256D);
		int j1 = k << 16 | l << 8 | i1 | 0xff000000;
		int k1 = 0;
		for (int l1 = 0; l1 < bits; l1++) {
			if (bit_f[l1] != 0)
				continue;
			bit_px[l1] = m_mouseX;
			bit_py[l1] = m_mouseY;
			double d = rand.nextDouble() * 6.2800000000000002D;
			double d1 = rand.nextDouble();
			bit_vx[l1] = Math.sin(d) * d1;
			bit_vy[l1] = Math.cos(d) * d1;
			bit_l[l1] = (int) (rand.nextDouble() * 100D) + 100;
			bit_p[l1] = (int) (rand.nextDouble() * 3D);
			bit_c[l1] = j1;
			bit_sx[l1] = m_mouseX;
			bit_sy[l1] = m_nAppY - 5;
			bit_f[l1] = 2;
			if (++k1 == bit_max)
				break;
		}
	}

	void rend() {
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		for (int k = 0; k < bits; k++)
			switch (bit_f[k]) {
			default:
				break;

			case 1: // '\001'
				bit_vy[k] += rand.nextDouble() / 50D;
				bit_px[k] += bit_vx[k];
				bit_py[k] += bit_vy[k];
				bit_l[k]--;
				if (bit_l[k] == 0 || bit_px[k] < 0.0D || bit_py[k] < 0.0D
						|| bit_px[k] > (double) m_nAppX
						|| bit_py[k] > (double) (m_nAppY - 3)) {
					bit_c[k] = 0xff000000;
					bit_f[k] = 0;
				} else if (bit_p[k] == 0) {
					if ((int) (rand.nextDouble() * 2D) == 0)
						bit_set((int) bit_px[k], (int) bit_py[k], -1);
				} else {
					bit_set((int) bit_px[k], (int) bit_py[k], bit_c[k]);
				}
				break;

			case 2: // '\002'
				bit_sy[k] -= 5;
				if ((double) bit_sy[k] <= bit_py[k]) {
					bit_f[k] = 1;
					flag2 = true;
				}
				if ((int) (rand.nextDouble() * 20D) == 0) {
					int i = (int) (rand.nextDouble() * 2D);
					int j = (int) (rand.nextDouble() * 5D);
					bit_set(bit_sx[k] + i, bit_sy[k] + j, -1);
				}
				break;
			}

		if (flag2 && bit_sound > 0)
			// sm.playInMediaPlayer(R.raw.firework, null);
			soundPool.play(id_sound1, 0);
	}

	void bit_set(int i, int j, int k) {
		int l = i + j * m_nAppX;
		 pix0[l] = k;
	}

	protected void calculatePerframe() {
		  try {
			Thread.sleep(m_sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private int m_nAppX;
	private int m_nAppY;
	private int m_centerX;
	private int m_centerY;
	private int m_mouseX;
	private int m_mouseY;
	private int m_sleepTime;
	private boolean isError;
	private boolean m_isPaintFinished;
	boolean isRunning;
	boolean isInitialized;
	Thread runner;
	int pix0[];
	Bitmap offImage;
	// Image dbImg;
	int pixls;
	int pixls2;
	Random rand;
	int bits;
	double bit_px[];
	double bit_py[];
	double bit_vx[];
	double bit_vy[];
	int bit_sx[];
	int bit_sy[];
	int bit_l[];
	int bit_f[];
	int bit_p[];
	int bit_c[];
	int bit_max = 100;
	int bit_sound = 2;
	int ru;
	int rv;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// return super.onTouch(v, event);
		int x = (int) event.getX();
		int y = (int) event.getY();
		x = x / 2;
		y = y / 2;
		if (x > m_nAppX)
			x = m_nAppX;
		if (y > m_nAppY)
			y = m_nAppY;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_UP:
			this.dot(x, y);

			Log.i("...........>>>",x+"?"+y);

			if (this.bit_sound > 1)
				// sm.playInMediaPlayer(R.raw.syu, null);
				soundPool.play(id_sound2, 0);
			break;
		}
		m_mouseX = x;
		m_mouseY = y;
		return true;
	}
}
