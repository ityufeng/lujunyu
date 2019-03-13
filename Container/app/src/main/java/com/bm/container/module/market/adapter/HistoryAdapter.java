package com.bm.container.module.market.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bm.container.R;
import com.bm.container.Tools.NumberUtils;
import com.bm.container.databinding.ItemHistoryBinding;
import com.bm.container.module.market.bean.OfferBean;

import java.util.ArrayList;

/**
 * @author nfmomo
 * <p>
 * //TODO 注解
 */
public class HistoryAdapter extends BaseAdapter { //TODO 改类名
    private Context context;
    private ArrayList<OfferBean> col = new ArrayList<>(); //TODO 改实体名
    private ItemHistoryBinding binding; //TODO 改binding类名
    private ArrayList<String> choosed = new ArrayList<>();

    public HistoryAdapter(Context context, ArrayList<OfferBean> col, ArrayList<String> choosed) { //TODO 改类名,改实体名
        this.context = context;
        this.col = col;
        this.choosed = choosed;
    }

    @Override
    public int getCount() {
        return col.size();
//		return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup vg) {

        if (contentView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_history, vg, false);//TODO 改布局文件
            contentView = binding.getRoot();
            contentView.setTag(binding);
        } else {
            binding = (ItemHistoryBinding) contentView.getTag();//TODO 改binding类名
        }

        OfferBean bean = col.get(position);

        binding.name.setText(bean.getCreateUserName());
//		binding.money.setText(bean.getPrice());
        binding.money.setText(NumberUtils.formatDouble(Double.valueOf(bean.getPrice())));
//		binding.num.setText(bean.getBidCount());
        binding.num.setText(bean.getCreateTime());
        if (bean.getImageUrl().equals("")) {
            binding.hasPic.setText("无");
        } else {
            binding.hasPic.setText("有");
        }
        if (bean.getIsSupportBill().equals("1")) {
            binding.hasInvoice.setText("有");
        } else {
            binding.hasInvoice.setText("无");
        }

        if (choosed.get(0).equals("" + position)) {
            binding.item.setBackgroundColor(context.getResources().getColor(R.color.grey_white));
        } else {
            binding.item.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        return contentView;
    }

}
