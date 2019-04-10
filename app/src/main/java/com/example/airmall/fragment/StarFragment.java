package com.example.airmall.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.airmall.R;
import com.example.airmall.activity.DetailActivity;
import com.example.airmall.activity.SearchActivity;
import com.example.airmall.adapter.BaseRecyclerAdapter;
import com.example.airmall.adapter.BaseViewHolder;
import com.example.airmall.adapter.SimpleAdapter;
import com.example.airmall.bean.Item;
import com.example.airmall.network.Impl.ItemServiceImpl;
import com.example.airmall.network.Impl.UserServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.example.airmall.utils.SPUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;


public class StarFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv_star;
    private SimpleAdapter<Item> simpleAdapter;

    //当前页
    private int page = 1;
    //每页数量
    private int size = 10;
    //数据总数
    private int total;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false);

        initView(view);
//        refreshData();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView(View view) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        rv_star = view.findViewById(R.id.rv_star);

        rv_star.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        simpleAdapter = new SimpleAdapter<Item>(getActivity(), R.layout.item_search) {
            @Override
            protected void convert(BaseViewHolder viewHolder, Item item) {
                SimpleDraweeView itemImage = (SimpleDraweeView) viewHolder.getView(R.id.iv_item_img);
                itemImage.setImageURI(item.getImage());
                viewHolder.getTextView(R.id.tv_item_title).setText(item.getName());
                viewHolder.getTextView(R.id.tv_item_describe).setText(item.getTitle());
                viewHolder.getTextView(R.id.tv_item_price).setText(item.getPrice());
            }
        };

        rv_star.setAdapter(simpleAdapter);

        refreshData();

        rv_star.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (!rv_star.canScrollVertically(1)) {
                if (total > page * size)
                    page += 1;
                refreshData();
            }
        });

        simpleAdapter.setOnItemClickListener((view1, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("id", simpleAdapter.getItem(position).getId());
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            refreshData();
        });
    }

    private void refreshData() {
        UserServiceImpl.getUserService().listStar(page, size, (String) SPUtils.get(getActivity(), "userId", ""), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    page = data.getInt("page");
                    size = data.getInt("size");
                    total = data.getInt("total");
                    JSONArray rows = data.getJSONArray("rows");
                    Item[] fromJson = JsonUtils.getGson().fromJson(rows.toString(), Item[].class);
                    new Thread(() -> getActivity().runOnUiThread(() -> {
                        if (page > 1)
                            simpleAdapter.loadMoreData(Arrays.asList(fromJson));
                        else
                            simpleAdapter.refreshData(Arrays.asList(fromJson));
                        if (refreshLayout.isRefreshing())
                            refreshLayout.setRefreshing(false);
                    })).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
