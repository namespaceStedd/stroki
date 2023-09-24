package Alex.Stroki.SQL;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Log {

    @Id
    private int id;
    private String shortUrl;
    private Date date;

    public Log() {
    }

    public Log(String shortUrl, Date date) {
        this.shortUrl = shortUrl;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
