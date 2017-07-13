package com.szw.autoscrollview.anim;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import com.szw.autoscrollview.R;


public class YuanHuaActivity extends Activity {
    private DrawYH yh;
    private SurfaceView yanhuaSurface;
    private View viewOverContain;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_over_game);
        yanhuaLogic();
    }

    private void yanhuaLogic() {
        viewOverContain = findViewById(R.id.rlv_game_over_contain);
        yanhuaSurface = (SurfaceView) findViewById(R.id.sv_yh);

        HolderSurfaceView.getInstance().setSurfaceView(yanhuaSurface);
//        yanhuaSurface.setBackgroundColor(Color.parseColor("#9000"));
        yh=new DrawYH();
        yanhuaSurface.setOnTouchListener(yh);
        yh.begin();
//        viewOverContain.setVisibility(View.GONE);

        playSuccess();
    }


    /**
     * 游戏成功
     */
    private void playSuccess() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                viewOverContain.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    for(int i=0;i <15;i++) {
                        Thread.sleep(50);
                        yh.dot();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
