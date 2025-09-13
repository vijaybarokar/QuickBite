package com.example.finalyearproject.admin.AdapterClass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.POJOGetAllCategoryDetails;
import com.example.finalyearproject.R;
import com.example.finalyearproject.common.Urls;

import java.util.List;

public class AdapterGetAllCategoryDetailsRV extends
        RecyclerView.Adapter<AdapterGetAllCategoryDetailsRV.ViewHolder>{

    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    Activity activity;

    public AdapterGetAllCategoryDetailsRV(List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails, Activity activity) {
        this.pojoGetAllCategoryDetails = pojoGetAllCategoryDetails;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rv_get_all_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        POJOGetAllCategoryDetails obj = pojoGetAllCategoryDetails.get(position);
        viewHolder.tvCategoryName.setText(obj.getCategoryName());

        Glide.with(activity).load(Urls.getImages+obj.getCategoryImage())
                .skipMemoryCache(false)
                .error(R.drawable.image_not_found)
                .into(viewHolder.ivCategoryImage);


    }

    @Override
    public int getItemCount() {
        return pojoGetAllCategoryDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCategoryImage;
        TextView tvCategoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategoryImage = itemView.findViewById(R.id.ivcategoryimage);
            tvCategoryName = itemView.findViewById(R.id.tvcategoryname);
        }
    }
}
