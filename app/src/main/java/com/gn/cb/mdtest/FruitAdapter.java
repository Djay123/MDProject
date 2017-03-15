package com.gn.cb.mdtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * RecyclerView的适配器
 * Created by CBJ on 2017/3/5.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context mContext;
    private List<Fruit> mFruitList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView fruitName;
        ImageView fruitImage;
        CardView cardView;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
            fruitName = (TextView)view.findViewById(R.id.fruit_name);

        }
    }

    public FruitAdapter(List<Fruit> fruitList){
        mFruitList = fruitList;

    }

    /**
     * 该方法是用来创建ViewHolder实例的。
     * 我们会将item的布局加载进来，然后创建一个ViewHolder实例。
     * 只有通过这个方法将ViewHolder实例创建出来之后，onBindViewHolder方法才能对相应
     * 的ViewHoler实例赋值
     * @param parent 子项布局的父布局
     * @param viewType
     * @return 为当前子项创建的一个ViewHolder对象
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(null == mContext){
            mContext = parent.getContext();
        }

        //通过父布局来将item项子布局加载进来，返回加载的子布局
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fruit_item,parent,false);


        //给每一个子项绑定监听事件
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Intent intent = new Intent(mContext,FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.name);
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.imageId);
                mContext.startActivity(intent);
            }
        });

        //用得到的子布局创建一个ViewHolder对象，用来保存view中的参数
        return holder;
    }

    /**
     * 该方法是是用于对RecyclerView子项的数据进行赋值的，
     * 会在每个子项被滚动到屏幕内的时候执行
     * @param holder 对应该项实例的ViewHolder对象
     * @param position 表示第几项数据
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.name);
        /**
         * Glide.with()方法需要传入一个Context、Activit、Fragment参数，
         * 然后调用load()方法去加载图片，可以是一个URL，也可以是一个本地路径
         * ，或者是一个资源id，最后调用into方法将图片设置到具体的某一个ImageView中。
         *
         * Glide内部做了很多非常复杂的逻辑操作，可以有效防止因加载高清图片而引起的内存溢出。
         */

        Glide.with(mContext).load(fruit.imageId).into(holder.fruitImage);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 返回当前数据的长度
     * @return
     */
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
