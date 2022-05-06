package skripsi.android.andrian;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectResponse {
    @SerializedName("RandomNumber")
    @Expose
    private String RandomNumber;

    @SerializedName("status")
    @Expose
    private String status;

    public ObjectResponse(String status, String RandomNumber){
        this.status = status;
        this.RandomNumber = RandomNumber;
    }
    public String getStatus(){
        return status;
    }

    public String getRandomNumber() {
        return RandomNumber;
    }
}
