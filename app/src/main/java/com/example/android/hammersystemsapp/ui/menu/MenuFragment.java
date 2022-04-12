package com.example.android.hammersystemsapp.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.hammersystemsapp.R;
import com.example.android.hammersystemsapp.data.BannerAdapter;
import com.example.android.hammersystemsapp.data.BannerItem;
import com.example.android.hammersystemsapp.data.CategoryAdapter;
import com.example.android.hammersystemsapp.data.CategoryItem;
import com.example.android.hammersystemsapp.data.FoodAdapter;
import com.example.android.hammersystemsapp.data.FoodItem;
import com.example.android.hammersystemsapp.databinding.FragmentMenuBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private RequestQueue requestQueue;
    private ArrayList<FoodItem> foodItems;
    private RecyclerView foodRecyclerView;
    private RecyclerView.Adapter<FoodAdapter.FoodViewHolder> foodAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Spinner spinner = binding.locationSpinner;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.location, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        ArrayList<BannerItem> bannerItems = new ArrayList<>();
        bannerItems.add(new BannerItem(R.drawable.banner_1));
        bannerItems.add(new BannerItem(R.drawable.banner_2));
        bannerItems.add(new BannerItem(R.drawable.banner_3));

        RecyclerView bannerRecyclerView = binding.bannerRecyclerView;
        bannerRecyclerView.setHasFixedSize(true);
        RecyclerView.Adapter<BannerAdapter.BannerViewHolder> bannerAdapter = new BannerAdapter(requireActivity(), bannerItems);
        LinearLayoutManager linearLayoutManagerBanner = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        bannerRecyclerView.setAdapter(bannerAdapter);
        bannerRecyclerView.setLayoutManager(linearLayoutManagerBanner);

        ArrayList<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(getResources().getString(R.string.category_pizza)));
        categoryItems.add(new CategoryItem(getResources().getString(R.string.category_combo)));
        categoryItems.add(new CategoryItem(getResources().getString(R.string.category_dessert)));
        categoryItems.add(new CategoryItem(getResources().getString(R.string.category_drink)));

        RecyclerView categoryRecyclerView = binding.categoryRecyclerView;
        categoryRecyclerView.setHasFixedSize(true);
        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> categoryAdapter = new CategoryAdapter(requireActivity(), categoryItems);
        LinearLayoutManager linearLayoutManagerCategory = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(linearLayoutManagerCategory);

        foodRecyclerView = binding.foodRecyclerView;
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        requestQueue = Volley.newRequestQueue(requireActivity());
        foodItems = new ArrayList<>();
        getFood();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getFood() {
        String url = "http://www.omdbapi.com/?apikey=3b8a83ef&s=superman";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Search");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String ImageUrl = jsonObject.getString("Poster");

                        FoodItem foodItem = new FoodItem();
                        foodItem.setTitle(title);
                        foodItem.setDescription(year);
                        foodItem.setImageUrl(ImageUrl);

                        foodItems.add(foodItem);
                    }
                    foodAdapter = new FoodAdapter(requireActivity(), foodItems);
                    foodRecyclerView.setAdapter(foodAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

}