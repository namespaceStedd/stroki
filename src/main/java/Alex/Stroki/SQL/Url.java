package Alex.Stroki.SQL;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Url {

    @Id
    private long id;
    private String url;
    private String shortUrl;
    private int tranzit;
    private String user;

    public Url() {
    }

    public Url(long id, String url, String shortUrl, int tranzit, String user) {
        this.id = id;
        this.url = url;
        this.shortUrl = shortUrl;
        this.tranzit = tranzit;
        this.user = user;
    }

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

    public int getTranzit() {
        return tranzit;
    }

    public void setTranzit(int tranzit) {
        this.tranzit = tranzit;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
