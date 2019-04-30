package com.jlbennett.soulsbornebossranker;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RankingRecyclerAdapter.RowViewHolder>{

    List<Boss> bosses;

    RankingRecyclerAdapter(List<Boss> bosses){
        this.bosses = bosses;
    }

    @Override
    public int getItemCount() {
        return bosses.size();
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ranking_card, viewGroup, false);
        return new RowViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int i) {
        Integer iI = i + 1;
        rowViewHolder.rankTV.setText(iI.toString());
        rowViewHolder.gameIV.setImageResource(R.drawable.ds1);
        //rowViewHolder.nameTV.setText(bosses.get(i).name);
        rowViewHolder.nameTV.setText("D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n D n");
        rowViewHolder.eloTV.setText("1000");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class RowViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView rankTV;
        ImageView gameIV;
        TextView nameTV;
        TextView eloTV;

        RowViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.rowCard);
            rankTV = (TextView) itemView.findViewById(R.id.rankTV);
            gameIV = (ImageView) itemView.findViewById(R.id.gameIV);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            eloTV = (TextView) itemView.findViewById(R.id.eloTV);
        }
    }
}
