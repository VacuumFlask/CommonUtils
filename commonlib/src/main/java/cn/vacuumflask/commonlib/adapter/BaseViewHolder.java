package cn.vacuumflask.commonlib.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Author: WeiCheng
 * Date: 2021/1/21 2:53 PM
 * Description: ViewHolder 基类
 */
public class BaseViewHolder<T extends ViewDataBinding, D> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected T mBinding;
    protected List<D> datas;
    private BaseRecyclerViewAdapter.OnItemClickListener<D> onItemClickListener;
    private BaseRecyclerViewAdapter.OnItemLongClickListener<D> onItemLongClickListener;
    private boolean isClickItemContent;

    public BaseViewHolder(T mBinding, List<D> datas) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.datas = datas;
    }

    public BaseViewHolder(T mBinding, List<D> data, BaseRecyclerViewAdapter.OnItemClickListener<D> onItemClickListener) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
        this.datas = data;
        this.onItemClickListener = onItemClickListener;
        View root = mBinding.getRoot();
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position > -1 && position < datas.size()) {
            if (onItemClickListener != null && !isClickItemContent) {
                onItemClickListener.onItemClickListener(datas.get(position), v, position);
            }
        }
    }

    public void setOnItemClickListener(BaseRecyclerViewAdapter.OnItemClickListener<D> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(BaseRecyclerViewAdapter.OnItemLongClickListener<D> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setClickItemContent(boolean clickItemContent) {
        isClickItemContent = clickItemContent;
    }

    public T getmBinding() {
        return mBinding;
    }

    public List<D> getData() {
        return datas;
    }

    public D getItem() {
        int position = getAdapterPosition();
        if (position > -1 && position < datas.size()) {
            return datas.get(position);
        }
        return null;
    }

    @Override
    public boolean onLongClick(View v) {
        int position = getAdapterPosition();
        if (position > -1 && position < datas.size()) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClickListener(datas.get(position), v, position);
            }
        }
        return false;
    }

    /**
     * 点击 Item 中 具体某一个View
     * @param eventView 需要点击事件的View
     */
    public void setItemContentClick(View eventView) {
        eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position > -1 && position < datas.size()) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(datas.get(position), v, position);
                    }
                }
            }
        });
    }

}
