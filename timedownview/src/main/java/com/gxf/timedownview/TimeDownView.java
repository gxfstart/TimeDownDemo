package com.gxf.timedownview;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * @author gxf
 * @date 2020/5/14
 */
public class TimeDownView extends FrameLayout {

    private TextView tv_hour_1;
    private TextView tv_hour_2;
    private TextView tv_minute_1;
    private TextView tv_minute_2;
    private TextView tv_second_1;
    private TextView tv_second_2;

    /**
     * CountDownTimer倒计时
     */
    private CountDownTimer countDownTimer;
    /**
     * 倒计时刷新间隔
     */
    private final long COUNT_DOWN_INTERVAL = 1000;

    public TimeDownView(Context context) {
        super(context);
    }

    public TimeDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.time_down_layout, this);

        tv_hour_1 = findViewById(R.id.tv_hour_1);
        tv_hour_2 = findViewById(R.id.tv_hour_2);
        tv_minute_1 = findViewById(R.id.tv_minute_1);
        tv_minute_2 = findViewById(R.id.tv_minute_2);
        tv_second_1 = findViewById(R.id.tv_second_1);
        tv_second_2 = findViewById(R.id.tv_second_2);
    }

    private void setTime(String time) {
        char[] chars = time.toCharArray();
        tv_hour_1.setText(String.valueOf(chars[0]));
        tv_hour_2.setText(String.valueOf(chars[1]));
        tv_minute_1.setText(String.valueOf(chars[2]));
        tv_minute_2.setText(String.valueOf(chars[3]));
        tv_second_1.setText(String.valueOf(chars[4]));
        tv_second_2.setText(String.valueOf(chars[5]));
    }

    /**
     * 开始倒计时
     *
     * @param millisInFuture 倒计时长
     */
    public void startCountDownTimer(long millisInFuture) {
        //如果有正在进行的倒计时，先停止
        stopCountDownTimer();
        countDownTimer = new CountDownTimer(millisInFuture, COUNT_DOWN_INTERVAL) {
            /**
             * @param millisUntilFinished 剩余时间
             */
            @Override
            public void onTick(long millisUntilFinished) {
                setTime(getTimeStr(millisUntilFinished));

                if (timeDownCallBack != null) {
                    timeDownCallBack.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (timeDownCallBack != null) {
                    timeDownCallBack.onFinish();
                }
            }
        };
        countDownTimer.start();
    }

    /**
     * 停止倒计时
     */
    public void stopCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        setTime("000000");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //View销毁时停止倒计时，防止内存泄漏
        stopCountDownTimer();
    }

    public void setTimeDownCallBack(TimeDownCallBack timeDownCallBack) {
        this.timeDownCallBack = timeDownCallBack;
    }

    private TimeDownCallBack timeDownCallBack;

    public interface TimeDownCallBack {
        void onTick(long millisUntilFinished);

        void onFinish();
    }

    /**
     * 毫秒转换时间字符串
     *
     * @param millis 毫秒
     * @return 000000
     */
    private String getTimeStr(long millis) {
        long totalSeconds = millis / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = (totalSeconds / 60 / 60) % 24;
        return String.format(Locale.getDefault(), "%02d%02d%02d", hours, minutes, seconds);
    }
}
