package com.szw.autoscrollview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import com.szw.autoscrollview.anim.DrawYH;
import com.szw.autoscrollview.anim.HolderSurfaceView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private static String Tag = "MainActivity";
    private RecyclerView scroll1,scroll2,scroll3;
    private Button btnPlay;
    private AutoScrollAdapter[] autoScrollAdapter = new AutoScrollAdapter[3];
    private int playAdds = 50;
    private Integer[] imags = new Integer[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f};

    private DrawYH yh;
    private SurfaceView yanhuaSurface;
    private View viewOverContain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scroll1 = (RecyclerView) findViewById(R.id.rv_scroll1);
        scroll2 = (RecyclerView) findViewById(R.id.rv_scroll2);
        scroll3 = (RecyclerView) findViewById(R.id.rv_scroll3);
        btnPlay = (Button) findViewById(R.id.btn_play_game);
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger = new ScrollSpeedLinearLayoutManger(this);
        scrollSpeedLinearLayoutManger.setSpeedSlow();
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger2 = new ScrollSpeedLinearLayoutManger(this);
        scrollSpeedLinearLayoutManger2.setSpeedSlow();
        ScrollSpeedLinearLayoutManger scrollSpeedLinearLayoutManger3 = new ScrollSpeedLinearLayoutManger(this);
        scrollSpeedLinearLayoutManger3.setSpeedSlow();
        scroll1.setLayoutManager(scrollSpeedLinearLayoutManger);
        scroll2.setLayoutManager(scrollSpeedLinearLayoutManger2);
        scroll3.setLayoutManager(scrollSpeedLinearLayoutManger3);

        scroll1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        preparePlayDateSequence(imags,1,4,true);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });

        yanhuaLogic();
    }

    private void yanhuaLogic() {
        viewOverContain = findViewById(R.id.rlv_game_over_contain);
        yanhuaSurface = (SurfaceView) findViewById(R.id.sv_yh);

        HolderSurfaceView.getInstance().setSurfaceView(yanhuaSurface);
        yanhuaSurface.setBackgroundColor(Color.parseColor("#502B2B2B"));
        yh=new DrawYH();
        yanhuaSurface.setOnTouchListener(yh);
        yh.begin();
        viewOverContain.setVisibility(View.GONE);
    }

    /**
     * 开始玩游戏
     */
    private void playGame() {
        scroll1.smoothScrollToPosition(playAdds);
                scroll2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll2.smoothScrollToPosition(playAdds);
                        scroll3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                scroll3.smoothScrollToPosition(playAdds);
                                playAdds = playAdds+imags.length*4;
                            }
                        },300);
                    }
                },300);

    }


    /**
     *  选中 规则 数据初始化
     * @param arrs  要初始化的序列数据（此处为 奖品图片序列）
     * @param choosePos  成功后选中的 条目 在数据序列中的角标
     * @param endScrollPos 要旋转几次（）次数多了，
     * @param isSuccess  （游戏是否成功了）成功 数据序列会使3个都一样，失败序列会有其中1个不一样
     */
    public void preparePlayDateSequence(final Integer[] arrs, final int choosePos, final int endScrollPos, final boolean isSuccess){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //1. 选择 1个随机位置
                int max=arrs.length-1;
                int min=0;
                Random random = new Random();
                int succesPosation = random.nextInt(max)%(max-min+1) + min;

                Integer[] ramdomSequence = new Integer[arrs.length];
                ramdomSequence[succesPosation] =  arrs[choosePos];
               //-------------------for difflogic -start--------------------------------------;
                int nextRadmoDiff=0;
                if(!isSuccess){
                     nextRadmoDiff = random.nextInt(autoScrollAdapter.length)%(autoScrollAdapter.length-0+1) + min;
                }
                //-------------------for difflogic -end---------------------------------------------;

                for(int scrollView =0 ;scrollView< autoScrollAdapter.length;scrollView++){
                    ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(arrs));
                    ArrayList<Integer> playSequence = new ArrayList<Integer>(Arrays.asList(ramdomSequence));
                    temp.remove(choosePos);

                    for(int i=0;i<playSequence.size();i++){
                        if(i==succesPosation){
                            continue;
                        }
                        int radomSequeceForTemp = temp.size();
                        int nextRadmo = random.nextInt(radomSequeceForTemp)%(radomSequeceForTemp-0+1) + min;
                        playSequence.set(i,temp.get(nextRadmo));
                        temp.remove(nextRadmo);
                    }

                    //---------for diff
                    if(!isSuccess && scrollView == nextRadmoDiff){
                        Integer successPosForDiff = playSequence.get(succesPosation);
                        Integer successPosForChange;
                        if(succesPosation==0){
                            successPosForChange = playSequence.get(succesPosation+1);
                            playSequence.set(succesPosation,successPosForChange);
                            playSequence.set(succesPosation+1,successPosForDiff);
                        }else{
                            successPosForChange = playSequence.get(succesPosation-1);
                            playSequence.set(succesPosation,successPosForChange);
                            playSequence.set(succesPosation-1,successPosForDiff);
                        }
                    }
                    //---------for diff ---- end

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0;i<playSequence.size();i++)
                    {
                        stringBuilder.append(playSequence.get(i)+",");
                    }

                    //------------------------ 数组生成成功 -----------------------

                    Log.i(Tag,stringBuilder.toString());

                    //------------------------ 计算滚动中奖角标----------------------

                    List products = new ArrayList<Product>();
                    for(int prodecut =0;prodecut<playSequence.size();prodecut++){
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), playSequence.get(prodecut));
                        Product product = new Product(bitmap, prodecut);
                        products.add(product);
                    }

                    autoScrollAdapter[scrollView] = new AutoScrollAdapter(products,MainActivity.this);
                }


                // endScrollPos* arrs.length = "要旋转的总圈数，是数组的整数倍"
                playAdds  = endScrollPos*arrs.length+ arrs.length  + succesPosation+1;
                Log.i(Tag,"to Play pos"+playAdds +" and succespos =" +succesPosation);

                return null;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                scroll1.setAdapter(autoScrollAdapter[0]);
                scroll2.setAdapter(autoScrollAdapter[1]);
                scroll3.setAdapter(autoScrollAdapter[2]);

                scroll3.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        switch (newState){
                            case RecyclerView.SCROLL_STATE_IDLE :
                                isNeedDot = true;
                                  playSuccess();
                                GameOverDialog gameOverDialog = new GameOverDialog(MainActivity.this);
                                gameOverDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        isNeedDot = false;
                                        viewOverContain.setVisibility(View.GONE);
                                    }
                                });
                                gameOverDialog.show();

                                break;
                        }
                    }
                });

            }
        }.execute();
    }
    private boolean isNeedDot = false;

    /**
     * 游戏成功
     */
    private void playSuccess() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                viewOverContain.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {

                try {
                    for(int i=0;i <15;i++) {
                        Thread.sleep(50);
                        yh.dot();
                    }

//                    while (isNeedDot){
//                        Thread.sleep(300);
//                        yh.dot();
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
