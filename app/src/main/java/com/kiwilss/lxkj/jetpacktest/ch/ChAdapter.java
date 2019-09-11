package com.kiwilss.lxkj.jetpacktest.ch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiwilss.lxkj.jetpacktest.R;

import java.util.List;
import java.util.Map;

/**
 * @author : Lss kiwilss
 * @FileName: ChAdapter
 * @e-mail : kiwilss@163.com
 * @time : 2019-08-22
 * @desc : {DESCRIPTION}
 */
public class ChAdapter extends RecyclerView.Adapter<ChAdapter.MyHodler> {

    private final LayoutInflater from;
    private Context context;
    private List<Map<String,Integer>>mData;

    public ChAdapter(Context context, List<Map<String, Integer>> mData) {
        this.context = context;
        this.mData = mData;
        from = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.layout_item_view, parent, false);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler holder, int position) {

        Map<String, Integer> map = mData.get(position);
        holder.ivIcon.setImageResource(map.get("icon"));
        holder.tvname.setText("hello" + map.get("num"));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyHodler extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvname, tvDes;
        public MyHodler(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_avatar);
            tvname = itemView.findViewById(R.id.tv_name);
            tvDes = itemView.findViewById(R.id.tv_message);
        }
    }
}
