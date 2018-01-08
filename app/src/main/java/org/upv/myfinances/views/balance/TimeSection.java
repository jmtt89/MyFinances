package org.upv.myfinances.views.balance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.upv.myfinances.R;
import org.upv.myfinances.model.MyCategory;
import org.upv.myfinances.model.MyTransaction;
import org.upv.myfinances.utils.Constants;
import org.upv.myfinances.views.transaction.EditTransactionActivity;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;


public class TimeSection extends StatelessSection {
    private Context context;
    private final List<MyTransaction> mValues;
    private final String title;

    public TimeSection(Context context, List<MyTransaction> myTransactions, String title) {
        // call constructor with layout resources for this Section header and items
        super(new SectionParameters.Builder(R.layout.fragment_transaction)
                .headerResourceId(R.layout.fragment_transaction_header)
                .build());
        this.context = context;
        this.mValues = myTransactions;
        this.title = title;
    }

    @Override
    public int getContentItemsTotal() {
        return mValues.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        MyTransaction transaction = mValues.get(position);
        MyCategory category = transaction.getMyCategory();
        String amount = String.format("%s%s%s", transaction.isIncome() ? "+" : "-" , Currency.getInstance(Locale.getDefault()).getSymbol(), transaction.getValue());
        itemHolder.transactionValue.setText(amount);
        itemHolder.transactionValue.setTextColor(transaction.isIncome() ? Color.GREEN : Color.RED);
        itemHolder.transactionDescription.setText(transaction.getDescription());
        itemHolder.transactionCategory.setText(category.getTitle());
        itemHolder.transactionCategoryIconWrapper.setCardBackgroundColor(Color.parseColor(category.getColor()));
        itemHolder.transactionCategoryIcon.setImageResource(category.getIcon());

        itemHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTransactionActivity.class);
                intent.putExtra(Constants.PARAM_TRANSACTION_IS_INCOME, mValues.get(position).isIncome());
                intent.putExtra(Constants.PARAM_TRANSACTION_ID, mValues.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        headerHolder.tvTitle.setText(title);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.date);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        @BindView(R.id.transaction_category_icon)
        ImageView transactionCategoryIcon;
        @BindView(R.id.transaction_category_icon_wrapper)
        CardView transactionCategoryIconWrapper;
        @BindView(R.id.transaction_description)
        TextView transactionDescription;
        @BindView(R.id.transaction_category)
        TextView transactionCategory;
        @BindView(R.id.transaction_value)
        TextView transactionValue;

        ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
