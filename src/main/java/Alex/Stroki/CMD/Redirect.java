/*
Ссылки:
•	Получение редиректа на полный url по короткой ссылке.
*/

package Alex.Stroki.CMD;

import Alex.Stroki.SQL.Log;
import Alex.Stroki.SQL.LogRepository;
import Alex.Stroki.SQL.Url;
import Alex.Stroki.SQL.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;

@RestController
public class Redirect {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private LogRepository logRepository;
    Date date;

    @PostMapping("/Редирект на полный URL")
    @CrossOrigin
    public String redirect11(@RequestParam String shortUrl, Model model, HttpServletResponse httpServletResponse) throws IOException {

        String redirect = "ОШИБКА!!! \nКороткая ссылка \"" + shortUrl +"\" не найдена.";
        String url = "";
        int tranzit = 0;
        Url classUrl = new Url();

        Iterable<Url> urls = urlRepository.findAll();
        model.addAttribute("urls", urls);

        for(Url iterUrl:urls)
            if(shortUrl.equals(iterUrl.getShortUrl())) {
                url = iterUrl.getUrl();
                tranzit = iterUrl.getTranzit();
                classUrl = iterUrl;
                break;
            }

        if (url != null && url != "") {

            // Здесь (и во вкладке ExampleNotWorkedRedirect) есть нерабочие попытки редиректа по URL.
            ResponseEntity.status(HttpStatus.OK).location(URI.create(url)).build();
            //httpServletResponse.sendRedirect(url);


            // Сохранение даты перехода
            date = new Date();
            Log log = new Log(shortUrl, date);
            logRepository.save(log);

            // Добавление перехода
            classUrl.setTranzit(tranzit + 1);
            urlRepository.save(classUrl);

            redirect = "Перенаправление по короткой ссылке \"" + shortUrl + "\" на сайт \"" + url + "\"...";
        }

        return redirect;
    }
}
