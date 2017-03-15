package com.gn.cb.mdtest;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView nav;
    //悬浮球
    private FloatingActionButton fab;

    //下拉刷新控件
    private SwipeRefreshLayout swipeRefreshLayout;

    //资源
    private Fruit[] fruits = {
            new Fruit("Apple",R.mipmap.apple),
            new Fruit("Banana",R.mipmap.banana),
            new Fruit("Orange",R.mipmap.orange),
            new Fruit("Watermelon",R.mipmap.watermelon),
            new Fruit("Pear",R.mipmap.pear),
            new Fruit("Grape",R.mipmap.grape),
            new Fruit("Pineapple",R.mipmap.pineapple),
            new Fruit("Strawberry",R.mipmap.strawberry),
            new Fruit("Cherry",R.mipmap.cherry),
            new Fruit("Mango",R.mipmap.mango)
    };
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private FruitAdapter fruitAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        //得到ActionBar实例
        ActionBar actionbar = getSupportActionBar();
        if (null != actionbar){
            //该方法作用是将导航栏显示出来
            actionbar.setDisplayHomeAsUpEnabled(true);
            //该方法用来设置导航栏按钮的图标
            actionbar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        nav = (NavigationView)findViewById(R.id.nav_view);

        //设置默认选中Call
        nav.setCheckedItem(R.id.nav_call);
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.nav_call:
                                Toast.makeText(MainActivity.this,"NavigationView Call",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_friends:
                                Toast.makeText(MainActivity.this,"NavigationView Friens",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_location:
                                Toast.makeText(MainActivity.this,"NavigationView Location",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_mail:
                                Toast.makeText(MainActivity.this,"NavigationView Mail",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_task:
                                Toast.makeText(MainActivity.this,"NavigationView Task",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data deleteed",Snackbar.LENGTH_SHORT)
                        .setAction("undo",new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,
                                        "Data Restored",Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });


        initFruits();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        /**创建一个GridLayoutManager这种布局。
         * 第一个参数是一个Context，第二个参数是表示是列数
         */
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        //设置RecyclerView的布局
        recyclerView.setLayoutManager(layoutManager);
        //创建一个适配器并设置给RecyclerView
        fruitAdapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(fruitAdapter);

        //下拉刷新控件
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        //设置刷新进度条的颜色
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        //下拉刷新的监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout
                .OnRefreshListener(){
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }

    private void initFruits(){
        fruitList.clear();
        for(int i = 0; i < 40; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"click backup",Toast.LENGTH_LONG).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"click delete",Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"click settings",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        fruitAdapter.notifyDataSetChanged();
                        //设置false表示刚才的刷新事件结束，并隐藏刷新进度条
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
