package rajitha.perera.com.thingstodeliver.network;

import java.util.List;

import rajitha.perera.com.thingstodeliver.model.Thing;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rajitha on 9/15/2018.
 */

public interface GetThingsDataService {
    @GET("deliveries")
    Call<List<Thing>> getThingsData(@Query("offset") int offset, @Query("limit") int limit);
}
