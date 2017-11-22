package exercise.com.architecturecomponentspixabay.model;

import com.google.gson.annotations.SerializedName;

public class Hit {

    public Hit() {
    }

    @SerializedName("id")
    private Integer id;
    @SerializedName("webformatURL")
    private String webformatURL;
    @SerializedName("user")
    private String user;

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getUser() {
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
