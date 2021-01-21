package cn.vacuumflask.commonlib.dialog;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import cn.vacuumflask.commonlib.R;

/**
 * Author: WeiCheng
 * Date: 2021/1/21 4:00 PM
 * Description:DialogFragment 基类
 */
public abstract class BaseDialogFragment<T extends ViewDataBinding> extends DialogFragment {

    protected T mBinding;
    protected FragmentActivity act;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        act = getActivity();
        if (bundle!=null){
            initBundleData(bundle);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,getLayoutResId(),container,false);
        initView(mBinding);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewCreated(mBinding);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setDialogAnim(win);
        DisplayMetrics dm = new DisplayMetrics();

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = location();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度 1 个屏幕
        params.width = dialogWidth();
        params.height = dialogHeight();

        win.setAttributes(params);
    }

    /**
     * 初始化 传值参数
     * @param bundle 数据
     */
    protected void initBundleData(Bundle bundle){}

    protected void initView(T mBinding){};

    protected abstract void viewCreated(T mBinding);

    protected abstract int getLayoutResId();

    protected abstract int dialogWidth();

    protected abstract int dialogHeight();

    /**
     * dialog 定位
     * @return Gravity.CENTER  Gravity.BOTTOM
     */
    protected abstract int location();

    protected void setDialogAnim(Window win){
        win.setWindowAnimations(R.style.dialogstyle); // 添加动画
    }
}
