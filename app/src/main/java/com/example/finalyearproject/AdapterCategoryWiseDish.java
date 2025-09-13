package com.example.finalyearproject;

import android.app.Activity;
import android.content.Intent; // 🔄 ADDED
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton; // 🔄 ADDED

import com.bumptech.glide.Glide;
import com.example.finalyearproject.common.Urls;

import java.util.List;

public class AdapterCategoryWiseDish extends BaseAdapter {

    List<POJOCategoryWiseDish> list;
    Activity activity;

    public AdapterCategoryWiseDish(List<POJOCategoryWiseDish> pojoCategoryWiseDishList, Activity activity) {
        this.list = pojoCategoryWiseDishList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lv_categorywisedish, null);

            holder.ivCategoryWiseDishImage = view.findViewById(R.id.ivcategorywisedishimage);
            holder.tvRestaurantName = view.findViewById(R.id.tvCategoryWiseDishRestaurantName);
            holder.tvRestaurantRating = view.findViewById(R.id.tvCategoryWiseDishRestaurantRating);
            holder.tvDishName = view.findViewById(R.id.tvCategoryWiseDishDishName);
            holder.tvDishCategory = view.findViewById(R.id.tvCategoryWiseDishDishCategory);
            holder.tvDishPrice = view.findViewById(R.id.tvCategoryWiseDishDishPrice);
            holder.tvDishOffer = view.findViewById(R.id.tvCategoryWiseDishDishoffer);
            holder.tvDishDescription = view.findViewById(R.id.tvCategoryWiseDishDishDescription);

            holder.btnOrderNow = view.findViewById(R.id.btnOrderNow); // 🔄 ADDED

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final POJOCategoryWiseDish obj = list.get(position);
        holder.tvRestaurantName.setText(obj.getRestaurantname());
        holder.tvRestaurantRating.setText(obj.getDishrating());
        holder.tvDishName.setText(obj.getDishname());
        holder.tvDishCategory.setText(obj.getDishcategory());
        holder.tvDishPrice.setText(obj.getDishprice());
        holder.tvDishOffer.setText(obj.getDishoffer());
        holder.tvDishDescription.setText(obj.getDishdiscription());

        Glide.with(activity)
                .load(Urls.getImages + obj.getDishimage())
                .skipMemoryCache(true)
                .error(R.drawable.image_not_found)
                .into(holder.ivCategoryWiseDishImage);

        // 🔄 ADD LISTENER TO ORDER BUTTON
        holder.btnOrderNow.setOnClickListener(v -> {
            Intent intent = new Intent(activity, OrderFoodNowActivity.class);
            intent.putExtra("dishname", obj.getDishname());
            intent.putExtra("dishprice", obj.getDishprice());
            intent.putExtra("dishimage", obj.getDishimage()); // ✅ pass image
            activity.startActivity(intent);
        });


        return view;
    }

    class ViewHolder {
        ImageView ivCategoryWiseDishImage;
        TextView tvRestaurantName, tvRestaurantRating, tvDishName, tvDishCategory, tvDishPrice, tvDishOffer, tvDishDescription;
        AppCompatButton btnOrderNow; // 🔄 ADDED
    }
}
