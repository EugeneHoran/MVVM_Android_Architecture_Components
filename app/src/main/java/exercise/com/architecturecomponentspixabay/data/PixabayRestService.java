package exercise.com.architecturecomponentspixabay.data;


import exercise.com.architecturecomponentspixabay.model.Pixabay;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayRestService {
    @GET("api/")
    Single<Pixabay> getImages(@Query("page") int page);
}
