package com.example.airmall.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.airmall.R;
import com.example.airmall.activity.DetailActivity;
import com.example.airmall.activity.SearchActivity;
import com.example.airmall.adapter.BaseViewHolder;
import com.example.airmall.adapter.SimpleAdapter;
import com.example.airmall.bean.Item;
import com.example.airmall.network.Impl.RecommendServiceImpl;
import com.example.airmall.utils.JsonUtils;
import com.example.airmall.utils.SPUtils;
import com.example.airmall.weight.GlideImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private EditText editText;
    private Banner banner;
    private volatile Item[] items;
    private RecyclerView rv_recommend;
    private SimpleAdapter<Item> simpleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        editText = view.findViewById(R.id.et_toolbar);
        banner = view.findViewById(R.id.banner);
        rv_recommend = view.findViewById(R.id.rv_recommend);
        rv_recommend.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        simpleAdapter = new SimpleAdapter<Item>(getActivity(), R.layout.item_recommend) {
            @Override
            protected void convert(BaseViewHolder viewHolder, Item item) {
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) viewHolder.getView(R.id.sv_image);
                simpleDraweeView.setImageURI(item.getImage());
                viewHolder.getTextView(R.id.tv_name).setText(item.getName());
                viewHolder.getTextView(R.id.tv_price).setText(item.getPrice());
            }
        };

        simpleAdapter.setOnItemClickListener((view1, position) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("id", simpleAdapter.getItem(position).getId());
            startActivity(intent);
        });
        rv_recommend.setAdapter(simpleAdapter);

        RecommendServiceImpl.getRecommendService().listRecommendByUser((String) SPUtils.get(getActivity(), "userId", ""), new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray data = jsonObject.getJSONArray("data");
                    Item[] items = JsonUtils.getGson().fromJson(data.toString(), Item[].class);
                    getActivity().runOnUiThread(() -> simpleAdapter.refreshData(Arrays.asList(items)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        RecommendServiceImpl.getRecommendService().listRecommendByHot(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray data = jsonObject.getJSONArray("data");
                    items = JsonUtils.getGson().fromJson(data.toString(), Item[].class);
                    List<String> images = new ArrayList<>();
                    for (int i = 0; i < items.length; i++) {
                        images.add(items[i].getImage());
                    }
                    banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        banner.setOnBannerListener(position -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("id", items[position].getId());
            startActivity(intent);
        });

        editText.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }

}
