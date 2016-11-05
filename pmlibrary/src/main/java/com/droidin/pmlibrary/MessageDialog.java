package com.droidin.pmlibrary;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bowen on 2016-11-03 0003.
 */

public class MessageDialog extends BottomDialog {

    private OnActionListener onAction;
    private String reason;
    private String tip;

    /**
     * @param context
     */
    public MessageDialog(Context context, String reason, String tip) {
        super(context, R.layout.activity_permission);
        this.reason = reason;
        this.tip = tip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView negativeBtn = findView(R.id.pm_refuse);
        TextView positiveBtn = findView(R.id.pm_accept);
        TextView reasonView = findView(R.id.pm_reason);
        TextView tipView = findView(R.id.pm_tip);

        reasonView.setText(reason);
        tipView.setText(tip);

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAction != null) {
                    onAction.onAction(false);
                }
            }
        });
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAction != null) {
                    onAction.onAction(true);
                }
            }
        });

    }

    public void setOnActionListener(OnActionListener onAction) {
        this.onAction = onAction;
    }

    public interface OnActionListener {
        void onAction(boolean accept);
    }
}
