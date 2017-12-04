package com.example.sony.recycleview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sony on 12/4/2017.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem;
    private final int VIEW_TYPE_LOADING = 10;
    private final int VIEW_TYPE_1 = 0;
    private final int VIEW_TYPE_2 = 2;
    int totalItemCount;
    private List<Person> personList;

    public static final String TAG = Adapter.class.toString();


    private OnLoadMoreListener onLoadMoreListener;


    public Adapter() {

    }



    public Adapter(RecyclerView recyclerView,List<Person> personList){
        this.personList = personList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    Log.d(TAG, String.valueOf(recyclerView.getScrollY())+"hihi");
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout1, parent,false);
        View itemView2 = layoutInflater.inflate(R.layout.layout2, parent,false);
        View loadingView = layoutInflater.inflate(R.layout.itemloading,parent, false);
        switch (viewType) {
            case VIEW_TYPE_1: return new PViewHolder(itemView);
            case VIEW_TYPE_2: return new PViewHolder2(itemView2);
            case VIEW_TYPE_LOADING: return new LoadingViewHolder(loadingView);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(personList.get(position) == null){
            return VIEW_TYPE_LOADING;
        }
        else {
            if(position % 2 == 0)   return VIEW_TYPE_1;
            else return VIEW_TYPE_2;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadingViewHolder) {
            Log.d(TAG, "123");
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
        else if(holder instanceof PViewHolder || holder instanceof  PViewHolder2)
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_1:
                    PViewHolder pViewHolder = (PViewHolder) holder;
                    pViewHolder.setData(personList.get(position));
                    break;

                case VIEW_TYPE_2:
                    PViewHolder2 pViewHolder2 = (PViewHolder2)holder;
                    pViewHolder2.setData(personList.get(position));
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class PViewHolder extends RecyclerView.ViewHolder{

        TextView tvname;
        TextView tvage;

        public PViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.nametv);
            tvage = (TextView) itemView.findViewById(R.id.agetv);
        }

        public void setData(Person person){
            tvname.setText(person.getName());
            tvage.setText(String.valueOf(person.getAge()));
        }
    }

    public class PViewHolder2 extends RecyclerView.ViewHolder{

        TextView tvname2;
        TextView tvage2;

        public PViewHolder2(View itemView) {
            super(itemView);
            tvname2 = (TextView) itemView.findViewById(R.id.tvname);
            tvage2 = (TextView) itemView.findViewById(R.id.tvage);
        }

        public void setData(Person person){
            tvname2.setText(person.getName());
            tvage2.setText(String.valueOf(person.getAge()));
        }

    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.prgbar);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }
}
