package org.upv.myfinances.views.categories;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.upv.myfinances.R;
import org.upv.myfinances.model.MyCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private List<MyCategory> categories;
    private Listeners listeners;

    public CategoryListAdapter(Listeners listeners) {
        this.listeners = listeners;
        this.categories = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyCategory category = categories.get(position);
        holder.icon.setImageResource(category.getIcon());
        holder.iconWrapper.setCardBackgroundColor(Color.parseColor(category.getColor()));
        holder.title.setText(category.getTitle());

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeners.onClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void clear(){
        categories.clear();
        notifyDataSetChanged();
    }

    public void add(Collection<MyCategory> toAdd){
        categories.addAll(toAdd);
        notifyDataSetChanged();
    }

    public void add(MyCategory category){
        categories.add(category);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_icon)
        ImageView icon;
        @BindView(R.id.category_icon_wrapper)
        CardView iconWrapper;
        @BindView(R.id.category_title)
        TextView title;
        View mainView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mainView = view;
        }
    }

    public interface Listeners{
        public void onClick(MyCategory category);
    }
}
