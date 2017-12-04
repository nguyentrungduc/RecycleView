package com.example.sony.recycleview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn;
    RecyclerView recyclerView;
    int TYPE = 0;
    Adapter adapter;
    public static String TAG = MainActivity.class.toString();
    ArrayList<Person> personArrayList = Person.createArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        btn = (Button) findViewById(R.id.btn);

        recyclerView = (RecyclerView) findViewById(R.id.rcv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new Adapter(recyclerView, personArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG,"On load more??? ");
                personArrayList.add(null);
                adapter.notifyItemInserted(personArrayList.size() - 1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        personArrayList.remove(personArrayList.size() - 1);
                        adapter.notifyItemRemoved(personArrayList.size());

                        int start = personArrayList.size();
                        int end = start + 10;

                        for (int i = start + 1; i < end; i++) {
                            personArrayList.add(new Person("Hoang",21));
                            adapter.notifyItemInserted(personArrayList.size());
                        }
                        adapter.notifyDataSetChanged();
                        adapter.setLoaded();

                    }
                }, 2000);
            }
        });
        listener();




    }
    private void listener(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TYPE == 0) {
                    Log.d(TAG, "btn click");
                    btn.setText("1");
                    final GridLayoutManager manager = new
                            GridLayoutManager(getBaseContext(), 2, GridLayoutManager.VERTICAL, false);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (position % 3 == 0 ? 2 : 1);
                        }

                    });
                    recyclerView.setLayoutManager(manager);
                    TYPE = 1;
                    adapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(adapter.getItemCount(), personArrayList.size());
                    recyclerView.setAdapter(adapter);


                }
                else{
                    Log.d(TAG,"btn click 2");
                    btn.setText("0");
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    TYPE = 0;
                    adapter.notifyDataSetChanged();

                    adapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(adapter.getItemCount(), personArrayList.size());
                    recyclerView.setAdapter(adapter);

                }
            }
        });



    }
}
