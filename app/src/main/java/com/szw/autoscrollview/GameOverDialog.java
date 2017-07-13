package com.szw.autoscrollview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by shenmegui on 2017/7/11.
 */
public class GameOverDialog extends Dialog {
    private Button btnEnter;
    public GameOverDialog(Context context) {
        super(context,R.style.transdialog);
        setCancelable(false);
    }

    public GameOverDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GameOverDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_over_game);
        btnEnter = (Button) findViewById(R.id.btn_enter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cancel();
            }
        });
    }
}
