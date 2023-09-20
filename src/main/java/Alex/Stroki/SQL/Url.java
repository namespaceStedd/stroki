package Alex.Stroki.SQL;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Url {

    @Id
    private long id;
    private String url;
    private String shortUrl;
    private String user;
    private int transit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTransit() {
        return transit;
    }

    public void setTransit(int transit) {
        this.transit = transit;
    }

    public Url() {
    }

    public Url(long id, String url, String shortUrl, String user, int transit) {
        this.id = id;
        this.url = url;
        this.shortUrl = shortUrl;
        this.user = user;
        this.transit = transit;
    }
}
