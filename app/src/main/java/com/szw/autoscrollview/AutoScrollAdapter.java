package com.szw.autoscrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by shenmegui on 2017/7/6.
 */
public class AutoScrollAdapter   extends RecyclerView.Adapter<AutoScrollAdapter.AskRequestHolder> {
    private List<Product> products;
    private Context context;
    public AutoScrollAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public AskRequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_scroll, null);
        AskRequestHolder askRequestHolder = new AskRequestHolder(inflate);
        return askRequestHolder;
    }

    @Override
    public void onBindViewHolder(AskRequestHolder holder, int position) {
        //无限循环
        position = position%products.size();

        Product product = products.get(position);
        holder.ico.setImageBitmap(product.getBitmap());

    }



    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class AskRequestHolder extends RecyclerView.ViewHolder{
        private ImageView ico;
        public AskRequestHolder(View itemView) {
            super(itemView);
            ico = (ImageView) itemView.findViewById(R.id.iv_ico);
        }
    }

}
