package cn.vacuumflask.commonlib.adapter;

import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Author: WeiCheng
 * Date: 2021/1/21 2:37 PM
 * Description: RecyclerView Adapter 封装基类
 */
public abstract class BaseRecyclerViewAdapter<T extends ViewDataBinding, D> extends RecyclerView.Adapter<BaseViewHolder<T, D>> {

    private final Activity act;
    private final List<D> datas;
    private OnItemClickListener<D> onItemClickListener;
    private OnItemLongClickListener<D> onItemLongClickListener;

    public BaseRecyclerViewAdapter(Activity act, List<D> datas) {
        this.act = act;
        this.datas = datas;
    }

    public interface OnItemClickListener<D> {
        void onItemClickListener(D entity, View view, int position);
    }

    public interface OnItemLongClickListener<D> {
        boolean onItemLongClickListener(D entity, View view, int position);
    }

    @NonNull
    @Override
    public BaseViewHolder<T, D> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        T mBinding = DataBindingUtil.inflate(LayoutInflater.from(act), getLayoutRes(), parent, false);
        BaseViewHolder<T, D> baseViewHolder = new BaseViewHolder<>(mBinding, datas);
        baseViewHolder.setOnItemClickListener(onItemClickListener);
        baseViewHolder.setOnItemLongClickListener(onItemLongClickListener);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<T, D> holder, int position) {
        D data = datas.get(position);
        T binding = holder.getmBinding();
        creatrView(binding, data, position);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    /**
     * 布局Id
     * @return 返回Item 布局Id
     */
    protected abstract int getLayoutRes();

    /**
     * Item 布局
     * @param binding Databinding 布局类
     * @param entity 实体类
     * @param position item 位置
     */
    protected abstract void creatrView(T binding, D entity, int position);


}
