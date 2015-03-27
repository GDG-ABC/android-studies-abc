package gdgabc.recyclerviewdemo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ecarrara on 23/03/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public TextView mTextView;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTextView = (TextView) rootView.findViewById(R.id.myTextView);
            this.mCardView = (CardView) rootView.findViewById(R.id.myCardView);
        }
    }

    public MyRecyclerViewAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
