package skripsi.android.andrian;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://backend-skripsi-alpha.vercel.app/";
    @GET("api/otp")
    Call<List<ObjectResponse>> getObjectResponse();
}
