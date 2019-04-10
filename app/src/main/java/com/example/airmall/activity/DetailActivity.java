package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.adapter.BaseViewHolder;
import com.example.airmall.adapter.ExpandableListAdapter;
import com.example.airmall.adapter.SimpleAdapter;
import com.example.airmall.bean.Item;
import com.example.airmall.network.Impl.ItemServiceImpl;
import com.example.airmall.network.Impl.RecommendServiceImpl;
import com.example.airmall.network.Impl.UserServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.example.airmall.utils.SPUtils;
import com.example.airmall.weight.CustomExpandableListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {
    private SimpleDraweeView iv_image;
    private TextView tv_name;
    private TextView tv_price;
    private TextView tv_title;
    private TextView tv_favorite;
    private TextView tv_star;
    private TextView tv_add_star;

    //产品id
    private String id;
    private Item item;
    private CustomExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private RecyclerView recyclerView;
    private SimpleAdapter<Item> simpleAdapter;

    private boolean isStar = false;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Fresco.initialize(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initView();
    }

    private void initView() {
        iv_image = findViewById(R.id.iv_img);
        tv_name = findViewById(R.id.tv_name);
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        tv_favorite = findViewById(R.id.tv_favorite);
        tv_star = findViewById(R.id.tv_star);
        tv_add_star = findViewById(R.id.tv_add_star);

        listView = findViewById(R.id.ex_list);
        listAdapter = new ExpandableListAdapter(DetailActivity.this, item);
        listView.setAdapter(listAdapter);
        recyclerView = findViewById(R.id.rv_recommend);
        recyclerView.setLayoutManager(new GridLayoutManager(DetailActivity.this, 2));

        simpleAdapter = new SimpleAdapter<Item>(DetailActivity.this, R.layout.item_recommend) {
            @Override
            protected void convert(BaseViewHolder viewHolder, Item item) {
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) viewHolder.getView(R.id.sv_image);
                simpleDraweeView.setImageURI(item.getImage());
                viewHolder.getTextView(R.id.tv_name).setText(item.getName());
                viewHolder.getTextView(R.id.tv_price).setText(item.getPrice());
            }
        };
        simpleAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
            intent.putExtra("id", simpleAdapter.getItem(position).getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(simpleAdapter);

        ItemServiceImpl.getItemService().getItem(id, (String) SPUtils.get(this, "userId", ""), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject itemObj = data.getJSONObject("item");
                    JSONObject history = data.getJSONObject("history");
                    isStar = !history.getBoolean("star");
                    isFavorite = !history.getBoolean("favorite");
                    item = JsonUtils.getGson().fromJson(itemObj.toString(), Item.class);

                    runOnUiThread(() -> {
                        tv_add_star.callOnClick();
                        tv_favorite.callOnClick();
                        listAdapter.setItem(DetailActivity.this.item);
                        iv_image.setImageURI(DetailActivity.this.item.getImage());
                        tv_name.setText(DetailActivity.this.item.getName());
                        tv_title.setText(DetailActivity.this.item.getTitle());
                        tv_price.setText(DetailActivity.this.item.getPrice());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        RecommendServiceImpl.getRecommendService().listRecommendByItem(id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray data = jsonObject.getJSONArray("data");
                    Item[] items = JsonUtils.getGson().fromJson(data.toString(), Item[].class);
                    runOnUiThread(() -> simpleAdapter.refreshData(Arrays.asList(items)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        tv_favorite.setOnClickListener(v -> {
            Drawable top;
            if (!isFavorite) {
                top = getDrawable(R.drawable.favorite_fill);
            } else {
                top = getDrawable(R.drawable.favorite);
            }
            tv_favorite.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
            isFavorite = !isFavorite;

            UserServiceImpl.getUserService().setFavorite(id, (String) SPUtils.get(this, "userId", ""), isStar, isFavorite, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });

        tv_star.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.putExtra("mainId", 2);
            startActivity(intent);
        });

        tv_add_star.setOnClickListener(v -> {
            if (!isStar) {
                tv_add_star.setBackgroundColor(getResources().getColor(R.color.div_white));
                tv_add_star.setText("取消收藏");
            } else {
                tv_add_star.setBackgroundColor(getResources().getColor(R.color.color_002));
                tv_add_star.setText("加入收藏");
            }
            isStar = !isStar;

            UserServiceImpl.getUserService().setFavorite(id, (String) SPUtils.get(this, "userId", ""), isStar, isFavorite, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });
    }

}
