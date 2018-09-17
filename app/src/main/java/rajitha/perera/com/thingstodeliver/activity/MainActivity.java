package rajitha.perera.com.thingstodeliver.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import rajitha.perera.com.thingstodeliver.R;
import rajitha.perera.com.thingstodeliver.adapter.ThingsAdapter;
import rajitha.perera.com.thingstodeliver.listener.EndlessRecyclerViewScrollListener;
import rajitha.perera.com.thingstodeliver.model.Thing;
import rajitha.perera.com.thingstodeliver.network.GetThingsDataService;
import rajitha.perera.com.thingstodeliver.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ThingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onCallApi(1, 20);

    }

    /*Method to generate List of things using RecyclerView with custom adapter*/
    private void generateThingsList(final List<Thing> thingsDataList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_things_list);

        adapter = new ThingsAdapter(thingsDataList, MainActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                loadNextDataFromApi(totalItemsCount, thingsDataList);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadNextDataFromApi(int offset, final List<Thing> thingsDataList) {


        /*Create handle for the RetrofitInstance interface*/
        GetThingsDataService service = RetrofitInstance.getRetrofitInstance(MainActivity.this, isOnline()).create(GetThingsDataService.class);

        /*Call the method with parameter in the interface to get the things data*/
        Call<List<Thing>> call = service.getThingsData(offset, 20);


        call.enqueue(new Callback<List<Thing>>() {
            @Override
            public void onResponse(Call<List<Thing>> call, Response<List<Thing>> response) {

                if (response.body() != null) {
                    thingsDataList.addAll(response.body());

                    adapter.notifyItemRangeInserted(thingsDataList.size(), response.body().size());
                }
            }

            @Override
            public void onFailure(Call<List<Thing>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onCallApi(int offset, int i) {

        /*Create handle for the RetrofitInstance interface*/
        GetThingsDataService service = RetrofitInstance.getRetrofitInstance(MainActivity.this, isOnline()).create(GetThingsDataService.class);

        /*Call the method with parameter in the interface to get the things data*/
        Call<List<Thing>> call = service.getThingsData(offset, i);


        call.enqueue(new Callback<List<Thing>>() {
            @Override
            public void onResponse(Call<List<Thing>> call, Response<List<Thing>> response) {
                generateThingsList(response.body());
            }

            @Override
            public void onFailure(Call<List<Thing>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
