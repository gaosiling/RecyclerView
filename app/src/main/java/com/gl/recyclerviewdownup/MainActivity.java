package com.gl.recyclerviewdownup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public boolean isRefresh  = false;

    ArrayList<String> listMore = null;

    private ArrayList<String> list = null;

    private HotAdapter mAdapter;

    @BindView(R.id.message)
    RecyclerView message;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    LinearLayout container;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(isRefresh) {
                        isRefresh  = false;
                    }
                    initView();
                    return true;
                case R.id.navigation_dashboard:
                    if(isRefresh) {
                        isRefresh  = false;
                    }
                    initView();
                    return true;
                case R.id.navigation_notifications:
                    if(isRefresh) {
                        isRefresh  = false;
                    }
                    initView();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        list = new ArrayList<>();
        listMore = new ArrayList();
        initView();
    }

    private void initView() {
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                isRefresh = false;
                getList();
            }

        });
        //初始化adapter
        mAdapter = new HotAdapter(this, null, true);
        //设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                isRefresh = true;
                getList();
            }
        });
        mAdapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<String>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, String data, int position, int viewType) {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        message.setLayoutManager(layoutManager);
        message.setAdapter(mAdapter);
        getList();
    }

    private void getList() {

        onShowProgressDlg();
        new Thread(){
            public void run(){
                try {
                    sleep(600);
                    cancelProgressDlg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        if ( !isRefresh ) {
            initData();
            mAdapter.setNewData(list);
            swipe.setRefreshing(false);
            mAdapter.setLoadingView(R.layout.load_more_layout);
        } else {
            addMoreData();
            if (listMore != null &&listMore.size() > 0) {
                mAdapter.setLoadMoreData(listMore);
            } else {
                mAdapter.setLoadEndView(R.layout.load_end_layout);
            }
        }
    }

    private void addMoreData() {
        if(listMore != null) {
            listMore.clear();
            for (int i = 0; i < 5; i++) {
                listMore.add(i, "addMoreData" + i);
            }
        }
    }

    private void initData() {
        if (list != null) {
            list.clear();
            for (int i = 0; i < 20; i++) {
                list.add(i, "initData" + i);
            }
        }
    }

    private LoadingDialog dialog;

    public void onShowProgressDlg() {
        if (dialog == null) {
            dialog = new LoadingDialog(this);
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

    }

    public void cancelProgressDlg() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

    }

}
