package com.szw.autoscrollview.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawTopBase implements Runnable, Callback,OnTouchListener {
	protected SurfaceView mSurfaceView;
	protected SurfaceHolder mSurfaceHolder;
	protected Context mContext;
	protected Rect mSurfaceRect = new Rect(0, 0, 0, 0);

	public DrawTopBase() {
		setSurfaceView(HolderSurfaceView.getInstance().getSurfaceView());
	}

	public void setSurfaceView(SurfaceView view) {
		mSurfaceView = view;
		mContext = mSurfaceView.getContext();
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceRect.set(new Rect(0, 0, mSurfaceView.getWidth(), mSurfaceView.getHeight()));
		set();
	}

	public void set() {
		setRect(mSurfaceRect);
	}

	protected Thread mThread = null;


	public void begin() {
		if (mThread == null) {
			mThread = new Thread(this);
			mThread.start();
		}
	}

	public void end() {
		mStatus = DrawStatus.Ending;
	}

	/**
	 * ������
	 * 
	 * @param canvas
	 */
	protected void doWork(Canvas canvas) {

	}

	/**
	 * ��������
	 */
	protected void endWork() {

	}

	protected Paint mPaint = new Paint();

	/**
	 * ���
	 * 
	 * @param canvas
	 */
	protected void clear(Canvas canvas) {
		mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		canvas.drawPaint(mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC));
	}

	protected void clear() {
		synchronized (mSurfaceHolder) {
			Canvas canvas = this.mSurfaceHolder.lockCanvas();
			try {
				clear(canvas);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null)
					mSurfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}

	protected void doChange() {
		change();
	}

	/**
	 * �仯
	 */
	protected void change() {

	}

	protected Rect mRect = new Rect(0, 0, 0, 0);

	public void setRect(Rect r) {
		mRect.set(r);
	}

	public Rect getRect() {
		return mRect;
	}

	protected DrawStatus mStatus = DrawStatus.NoWork;

	/**
	 * ����״̬ noWork û�й��� draw ����ѭ���� end �������� destroy ����������
	 * 
	 * @author gary
	 * 
	 */
	protected enum DrawStatus {
		NoWork, Drawing, Ending, Destroyed
	};

	protected long mBegintime;

	@Override
	public void run() {
		mStatus = DrawStatus.Drawing;
		starttime = System.currentTimeMillis();
		mBegintime = System.currentTimeMillis();
		// �������λ���
		clear();
		clear();
		while (mStatus == DrawStatus.Drawing) {
			synchronized (mSurfaceHolder) {
				Canvas canvas = this.mSurfaceHolder.lockCanvas(getRect());
				try {
					if (canvas != null) {
						clear(canvas);
						doWork(canvas);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (canvas != null)
						mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
				doChange();				
			}
			calculatePerframe();

		}
		if (mStatus != DrawStatus.Destroyed)
			clear();
		mThread = null;
		endWork();
	}

	protected long starttime;
	// ÿ֡ʱ�� 60֡���� ��һ�μ���ǰʹ�� ����
	private float perframe = 16;

	private int count = 0;

	// ÿ���೤ʱ�����һ��֡ʱ��
	private int mRefreshSpeed = 20;

	// �趨Ҫ��೤ʱ��ÿ֡ 24֡ÿ��
	private float mFPS = 1000 / 12;
	private float sleep = mFPS;

	// ����ˢ��Ƶ��
	public void setFPS(int fps) {
		mFPS = 1000 / fps;
	}

	/**
	 * �ȴ�ʱ��
	 */
	protected void calculatePerframe() {
		count++;
		if (count == mRefreshSpeed) // ����ÿ֡����ή����ϷЧ�ʡ�20��50���
		{
			long timepass = System.currentTimeMillis();
			long time = timepass - starttime;
			perframe = time / mRefreshSpeed;// ÿ֡ʱ��
			sleep = perframe > mFPS ? mFPS - (perframe - mFPS) / mRefreshSpeed: mFPS;
			starttime = timepass;
			count = 0;
		}
		try {
			Thread.sleep((long) (sleep));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		mSurfaceRect.set(new Rect(0, 0, width, height));
		set();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mStatus = DrawStatus.Destroyed;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
