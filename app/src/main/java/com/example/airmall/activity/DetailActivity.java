package com.example.airmall.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.airmall.R;
import com.example.airmall.adapter.ExpandableListAdapter;
import com.example.airmall.bean.Item;
import com.example.airmall.network.Impl.ItemServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private SimpleDraweeView iv_image;
    private TextView tv_name;
    private TextView tv_price;
    private TextView tv_title;
    private String id;
    private Item item;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;

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
        iv_image=findViewById(R.id.iv_img);
        tv_name = findViewById(R.id.tv_name);
        tv_title = findViewById(R.id.tv_title);
        tv_price = findViewById(R.id.tv_price);
        listView = findViewById(R.id.ex_list);
        listAdapter = new ExpandableListAdapter(DetailActivity.this, item);
        listView.setAdapter(listAdapter);

        ItemServiceImpl.getItemService().getItem(id, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    item = JsonUtils.getGson().fromJson(data.toString(), Item.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.setItem(item);
                            iv_image.setImageURI(item.getImage());
                            tv_name.setText(item.getName());
                            tv_title.setText(item.getTitle());
                            tv_price.setText(item.getPrice());
                        }

                    });
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
