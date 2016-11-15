package com.droidin.pmlibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;


public class BottomDialog extends BottomSheetDialog {

    private View mView;
    private int layoutId;

    private OnDialogInvisible onDialogInvisible;

    /**
     * @param context
     * @param layoutId
     */
    public BottomDialog(Context context, @LayoutRes int layoutId) {
        super(context);
        setContentView(layoutId);
        this.layoutId = layoutId;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mView = getLayoutInflater().inflate(layoutResID, null);
        setContentView(mView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mView == null) {
            setContentView(layoutId);
        }

        getWindow().setWindowAnimations(R.style.BottomUpAnim);

        View view = getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        if (view != null) {
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(view);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        hide();
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        if (onDialogInvisible != null) {
                            onDialogInvisible.onDialogDismiss();
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }
    }

    public <E extends View> E findView(@IdRes int resId) {
        if (mView == null) {
            setContentView(layoutId);
            return (E) mView.findViewById(resId);
        } else {
            return (E) mView.findViewById(resId);
        }
    }

    public View getDialogView() {
        if (mView == null) {
            setContentView(layoutId);
        }
        return mView;
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (onDialogInvisible != null) {
            onDialogInvisible.onDialogDismiss();
        }
    }

    public void setOnDialogInvisibleListener(OnDialogInvisible onDialogInvisible) {
        this.onDialogInvisible = onDialogInvisible;
    }

    public interface OnDialogInvisible {
        void onDialogDismiss();
    }
}
