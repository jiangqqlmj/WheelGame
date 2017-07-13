package com.szw.autoscrollview;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by shenmegui on 2017/7/11.
 */
public class GameOverPopUpWindow extends PopupWindow {
    private View conentView;

    public GameOverPopUpWindow(Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_over_game, null);
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(787);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.update();
    }
    /**
     * 显示popupWindow
     *
     */
    public void showPopupWindow(View parentView) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            showAtLocation(parentView, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
