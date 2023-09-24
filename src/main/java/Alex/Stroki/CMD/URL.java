/*
Короткие ссылки пользователя:
•	создание новой короткой ссылки;
•	получение всех созданных коротких ссылок пользователя;
•	получение информации о конкретной короткой ссылке пользователя (также включить количество переходов);
•	удаление короткой ссылки пользователя.
*/

package Alex.Stroki.CMD;

import Alex.Stroki.SQL.Url;
import Alex.Stroki.SQL.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Alex.Stroki.StrokiApplication.*;

@RestController
public class URL {

    private static final String allowedString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static char[] allowedCharacters = allowedString.toCharArray();
    private static int base = allowedCharacters.length;

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/Создание короткой ссылки")
    public String createUrl(@RequestParam String url, Model model) {

        String shortUrl = notAuthorized;

        if (authorizeUser != null) {
            // Проверка, существует ли данная ссылка в базе данных
            boolean isExist = false;
            Iterable<Url> existUrl = urlRepository.findAll();
            model.addAttribute("shortUrls", existUrl);

            for(Url iterUrl:existUrl)
                if(url.equals(iterUrl.getUrl())) {
                    shortUrl = "Введённая ссылка уже существует и находится по короткому адресу: \"" + iterUrl.getShortUrl() + "\"";
                    isExist = true;
                    break;
                }

            if (!isExist) {
                long id = getLastId(model);
                shortUrl = getShortUrl(id, url, model);
                Url classUrl = new Url(id, url, shortUrl, 0, authorizeUser);
                urlRepository.save(classUrl);
            }

        }

        return shortUrl;
    }

    @GetMapping("/Получение ссылок пользователя")
    public String getUserUrls(Model model) {
        String userUrls = "";

        if (authorizeUser != null) {
            Iterable<Url> shortUrls = urlRepository.findAll();
            model.addAttribute("shortUrls", shortUrls);

            for(Url iterUrl:shortUrls)
                if(authorizeUser.equals(iterUrl.getUser()))
                    userUrls += iterUrl.getShortUrl() + "\n";
        }
        else
            userUrls = notAuthorized;

        if (userUrls == null || userUrls == "")
            userUrls = "У вас ещё (или уже) нет созданных ссылок.";

        return userUrls;
    }

    @GetMapping("/Получение информации о короткой ссылке пользователя")
    public String getUrlInfo(@RequestParam String shortUrl, Model model) {
        String urlInfo = notFoundOrNorAccess(shortUrl);

        if (authorizeUser != null) {
            Iterable<Url> shortUrls = urlRepository.findAll();
            model.addAttribute("shortUrls", shortUrls);

            for(Url iterUrl:shortUrls)
                if (shortUrl.equals(iterUrl.getShortUrl()))
                    if (authorizeUser.equals(iterUrl.getUser())) {
                        urlInfo = "Идентификатор ссылки: " + iterUrl.getId()
                                + "\nСсылка: " + iterUrl.getUrl()
                                + "\nКороткая ссылка: " + iterUrl.getShortUrl()
                                + "\nСоздана пользователем: " + iterUrl.getUser();
                        break;
                    }
        }
        else
            urlInfo = notAuthorized;

        return urlInfo;
    }

    @DeleteMapping("/Удаление короткой ссылки")
    public String deleteShortUrl(@RequestParam String shortUrl, Model model) {
        String delete = notFoundOrNorAccess(shortUrl);

        if (authorizeUser != null) {
            Iterable<Url> shortUrls = urlRepository.findAll();
            model.addAttribute("shortUrls", shortUrls);

            for(Url iterUrl:shortUrls)
                if(shortUrl.equals(iterUrl.getShortUrl()))
                    if(authorizeUser.equals(iterUrl.getUser())) {
                        urlRepository.deleteById(iterUrl.getId());
                        delete = "Удаление короткой ссылки \"" + shortUrl + "\" прошло успешно!";
                    }
        }
        else
            delete = notAuthorized;

        return delete;
    }

    // Это больше считывание предыдущего long числа из id-поля для конвертации его в хэш, чем костыль
    public long getLastId(Model model) {
        long i = 999999999L;

        List<Url> urls = urlRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("url", urls);

        for(Url url:urls)
            i = url.getId();

        return i + 1;
    }

    public String getShortUrl(long id, String url, Model model) {

        String shortUrl = "";
        boolean isExist = false;

        Iterable<Url> urls = urlRepository.findAll();
        model.addAttribute("url", urls);

        for(Url iterUrl:urls) {
            if(url.equals(iterUrl.getUrl())) {
                shortUrl = iterUrl.getShortUrl(); // Url уже существует
                isExist = true;
                break;
            }
        }

        // Если не существует, генерация ...
        if (!isExist) {
            shortUrl = shortener(id);
        }

        return shortUrl;
    }

    public static String shortener(long id) {
        String shortUrl = "http://alex.stroki/";

        while (id > 0) {
            shortUrl += String.valueOf(allowedCharacters[Math.toIntExact(id % base) - 1]);
            id /= base;
        }

        return shortUrl;
    }

}
