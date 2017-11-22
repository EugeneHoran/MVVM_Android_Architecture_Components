package exercise.com.architecturecomponentspixabay.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pixabay {
    public Pixabay() {
    }

    @SerializedName("hits")
    private List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
