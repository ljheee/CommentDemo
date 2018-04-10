package com.ljheee.commentdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends Activity {

    private ListView mListView;
    private View mCommentView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listview);
        myAdapter = new MyAdapter(this, getData());
        mListView.setAdapter(myAdapter);

        
        // 评论的 编辑框和发送button（点击评论时显示，不评论时隐藏）
        mCommentView = findViewById(R.id.comment_view);
    }

    // build data  从后台数据库获取Java bean列表（Item）
    private ArrayList<Item> getData() {
        int ITEM_COUNT = 20;
        ArrayList<Item> data = new ArrayList<Item>();

        for (int i = 0; i < 3; i++) {
            data.add(new Item(R.drawable.xiaona, "薄荷栗", "我学过跆拳道，都给我跪下唱征服", "昨天"));
            data.add(new Item(R.drawable.xueyan, "欣然", "走遍天涯海角，唯有我家风景最好，啊哈哈", "昨天"));
            data.add(new Item(R.drawable.leishao, "陈磊_CL", "老子以后要当行长的，都来找我借钱吧，now", "昨天"));
            data.add(new Item(R.drawable.yuhong, "永恒依然", "房子车子都到碗里来", "昨天"));
            data.add(new Item(R.drawable.lanshan, "蓝珊", "你们这群傻×，我笑而不语", "昨天"));

        }

        return data;
    }

    // custom adapter
    private class MyAdapter extends BaseAdapter implements ItemView.OnCommentListener {

        private Context context;
        private ArrayList<Item> mData;
        private Map<Integer, ItemView> mCachedViews = new HashMap<Integer, ItemView>();

        public MyAdapter(Context context, ArrayList<Item> mData) {
            this.context = context;
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;

            if (convertView != null) {
                view = convertView;
            } else {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_item, null, false);
            }

            if (view instanceof ItemView) {
                Item data = (Item) getItem(position);
                ((ItemView) view).setData(data);
                ((ItemView) view).setPosition(position);
                ((ItemView) view).setCommentListener(this);

                cacheView(position, (ItemView) view);
            }

            return view;
        }

        /**
         * 点击评论，显示隐藏的  评论 编辑框和发送button
         */
        @Override
        public void onComment(int position) {
            showCommentView(position);
        }

        private void cacheView(int position, ItemView view) {
            Iterator<Map.Entry<Integer, ItemView>> entries = mCachedViews.entrySet().iterator();

            while (entries.hasNext()) {

                Map.Entry<Integer, ItemView> entry = entries.next();
                if (entry.getValue() == view && entry.getKey() != position) {
                    mCachedViews.remove(entry.getKey());
                    break;
                }
            }

            mCachedViews.put(position, view);

            Log.d("MainActivity", position + ", " + mCachedViews.size());
        }

        // 显示隐藏的  评论 编辑框和发送button
        private void showCommentView(final int position) {
            mCommentView.setVisibility(View.VISIBLE);
//            EditText et = (EditText) mCommentView.findViewById(R.id.edit);
//            et.setFocusable(true);
//            et.setFocusableInTouchMode(true);
//            et.requestFocus();

            mCommentView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText et = (EditText) mCommentView.findViewById(R.id.edit);
                    String s = et.getText().toString();

                    if (!TextUtils.isEmpty(s)) {

                        // update model
                        Comment comment = new Comment(s);
                        mData.get(position).getComments().add(comment);

                        // update view maybe
                        ItemView itemView = mCachedViews.get(position);

                        if (itemView != null && position == itemView.getPosition()) {
                            itemView.addComment();
                        }
                        //将评论实例化
                        

                        et.setText("");
                        mCommentView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
