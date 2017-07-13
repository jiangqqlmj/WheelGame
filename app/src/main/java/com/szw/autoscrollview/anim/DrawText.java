package com.szw.autoscrollview.anim;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class DrawText extends DrawTopBase {

	protected void doWork(Canvas canvas) {
		super.doWork(canvas);
		mPaint.setColor(Color.RED);
		mPaint.setTextSize(20);
		canvas.drawText("新年快乐", 100, 50, mPaint);
		if(textb!=null)
		{
			canvas.drawBitmap(textb, 100, 100, mPaint);
		}
	}

	Bitmap textb;
	Canvas textc;
	int[] textints;
	int[] bints;
	int textW;
	int textH;
	Rect textR;

	public void set() {
		super.set();

		Log.e("aaa","bbbbb");

		textR = new Rect();
		Paint paint=new Paint();
		paint.setTextSize(20);
		String s = "万事如意";
		paint.getTextBounds(s, 0, s.length(), textR);
		textW=textR.width();
		textH=textR.height();
		textb= Bitmap.createBitmap(textW, textH, Bitmap.Config.ARGB_8888);
		textc = new Canvas();
		textc.setBitmap(textb);
		paint.setColor(0xffffffff);
		textc.drawRGB(255, 0, 0);
		mPaint.setColor(0xff00ff00);
		textc.drawText(s, 100,textH-2+50, paint);
	}
}
