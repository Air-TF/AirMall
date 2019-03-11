package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.airmall.R;
import com.example.airmall.adapter.BaseRecyclerAdapter;
import com.example.airmall.adapter.BaseViewHolder;
import com.example.airmall.adapter.SimpleAdapter;
import com.example.airmall.bean.Item;
import com.example.airmall.network.Impl.ItemServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SimpleAdapter searchAdapter;
    private SwipeRefreshLayout refreshLayout;
    private List<Item> items;
    Intent intent;

    //当前页
    private int page = 1;
    //每页数量
    private int size = 10;
    //总量
    private int total;

    private String keyword;

    private String subcategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Fresco.initialize(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = getIntent();
        int id = intent.getIntExtra("subcategoryId", -1);
        if (id > 0) {
            subcategoryId = String.valueOf(id);
            refreshData();
        } else {
            subcategoryId = "";
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        searchView = findViewById(R.id.sv_search);
        recyclerView = findViewById(R.id.rv_item);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
        searchAdapter = new SimpleAdapter<Item>(SearchActivity.this, R.layout.item_search) {
            @Override
            protected void convert(BaseViewHolder viewHolder, Item item) {
                SimpleDraweeView itemImage = (SimpleDraweeView) viewHolder.getView(R.id.iv_item_img);
                itemImage.setImageURI(item.getImage());
                viewHolder.getTextView(R.id.tv_item_title).setText(item.getName());
                viewHolder.getTextView(R.id.tv_item_describe).setText(item.getTitle());
                viewHolder.getTextView(R.id.tv_item_price).setText(item.getPrice());
                viewHolder.getImageView(R.id.iv_item_cart).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchActivity.this, "To cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        };
        recyclerView.setAdapter(searchAdapter);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    refreshData();
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                refreshData();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                page = 1;
                keyword = s;
                refreshData();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        searchAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("id", items.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void refreshData() {
        ItemServiceImpl.getItemService().getItemListByKeyword(page, size, keyword, subcategoryId, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    page = data.getInt("page");
                    total = data.getInt("total");
                    size = data.getInt("size");
                    JSONArray rows = data.getJSONArray("rows");
                    Item[] fromJson = JsonUtils.getGson().fromJson(rows.toString(), Item[].class);
                    items = Arrays.asList(fromJson);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (page > 1)
                                        searchAdapter.loadMoreData(items);
                                    else
                                        searchAdapter.refreshData(items);
                                    if (refreshLayout.isRefreshing())
                                        refreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "-1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
