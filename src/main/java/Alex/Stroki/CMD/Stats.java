/*
Статистика по ссылкам:
•	получение временного графика количества переходов с группировкой по дням, часам, минутам;
•	получение топа из 20 сайтов источников переходов.
*/

package Alex.Stroki.CMD;

import Alex.Stroki.SQL.Log;
import Alex.Stroki.SQL.LogRepository;
import Alex.Stroki.SQL.Url;
import Alex.Stroki.SQL.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static Alex.Stroki.StrokiApplication.authorizeUser;
import static Alex.Stroki.StrokiApplication.notAuthorized;

@RestController
public class Stats {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private LogRepository logRepository;

    @GetMapping("/Получение временного графика")
    public String getTimeGraph(@RequestParam String url, @RequestParam String графикСтроитьПо, Model model) {

        String getGraph = notAuthorized;

        if (authorizeUser != null && (графикСтроитьПо.equals("дням") || графикСтроитьПо.equals("часам") || графикСтроитьПо.equals("минутам"))) {
            // Проверка, существует ли данная ссылка в базе данных
            boolean isExist = false;
            String shortUrl = "";
            Iterable<Url> existUrl = urlRepository.findAll();
            model.addAttribute("Urls", existUrl);

            // Получение короткой ссылки, если введена исходная
            for(Url iterUrl:existUrl)
                if(url.equals(iterUrl.getUrl()) || url.equals(iterUrl.getShortUrl())) {
                    shortUrl = iterUrl.getShortUrl();
                    isExist = true;
                    break;
                }

            if (isExist) {
                List<Date> date = date(shortUrl, model);

                List<String[]> dateString = new ArrayList<>();
                for (int i = 0; i < date.size(); i++)
                    dateString.add(String.valueOf(date.get(i)).split(" "));

                // Определение типа модели и занесение списка данными в связи с этим
                String[] stringGraph = new String[dateString.size()];
                switch (графикСтроитьПо) {
                    case "дням":
                        for (int i = 0; i < dateString.size(); i++) {
                            String[] line = dateString.get(i);
                            stringGraph[i] = line[0];
                        }
                        break;

                    case "часам":
                        for (int i = 0; i < dateString.size(); i++) {
                            String[] time = dateString.get(i);
                            time = time[1].split(":");
                            stringGraph[i] = time[0];
                        }
                        break;

                    case "минутам":
                        for (int i = 0; i < dateString.size(); i++) {
                            String[] time = dateString.get(i);
                            time = time[1].split(":");
                            stringGraph[i] = time[1];
                        }
                        break;
                }

                // Получение графика
                Map<String, Integer> graph = graph(stringGraph, model);
                int[] columnLength = {0, 0};
                for (Map.Entry<String, Integer> getGraphValues : graph.entrySet()) {
                    columnLength[0] = 19 - getGraphValues.getKey().length();
                    if (columnLength[1] < getGraphValues.getValue())
                        columnLength[1] = getGraphValues.getValue();

                }
                String column = "";
                for (int i = 1; i <= columnLength[1] + 1; i++)
                    column += " " + i;
                getGraph = "График для ссылки \"" + shortUrl + "\" построен по " + графикСтроитьПо
                        + "\n\nПоказатель времени | " + column
                        + "\n------------------------------------------";

                for (Map.Entry<String, Integer> getGraphValues : graph.entrySet()) {
                    getGraph += "\n" + getGraphValues.getKey();
                    for (int i = 0; i < columnLength[0] - 1; i++)
                        getGraph += " ";

                    getGraph += "| ";

                    for (int i = 0; i < getGraphValues.getValue(); i++)
                        getGraph += "**";
                }
            }
            else
                getGraph = "Ссылка \"" + url + "\" не найдена в базе данных.";

        }
        else if (authorizeUser != null)
            getGraph = "ОШИБКА!!! \nНераспознанный тип модели.";

        return getGraph;
    }

    @GetMapping("/Получение топ-20 переходов")
    public String getTop(Model model) {
        String top = notAuthorized;

        if (authorizeUser != null) {
            List<Url> urls = urlRepository.findAll(Sort.by(Sort.Direction.DESC, "tranzit"));
            model.addAttribute("urls", urls);

            int i = 0;
            top = "";
            for(Url iterUrl:urls) {
                top += iterUrl.getShortUrl() + ": " + iterUrl.getTranzit() + " переходов\n";
                i++;

                if (i >= 20)
                    break;
            }
        }

        return top;
    }

    // Получение дат и занесение в список
    public List<Date> date(String shortUrl, Model model) {
        List<Date> date = new ArrayList<>();
        Iterable<Log> redirectData = logRepository.findAll();
        model.addAttribute("redirectData", redirectData);

        for(Log iterLog:redirectData)
            if (shortUrl.equals(iterLog.getShortUrl()))
                date.add(iterLog.getDate());

        return date;
    }

    // Получение переходов по дате и занесение в список
    public Map<String, Integer> graph(String[] dateString, Model model) {
        Map<String, Integer> graph = new TreeMap<>();

        for (int i = 0; i < dateString.length; i++) {
            boolean isExistKey = false;
            int tranzit = 0;
            for (Map.Entry<String, Integer> getKey : graph.entrySet())
                if (dateString[i].equals(getKey.getKey())) {
                    isExistKey = true;
                    tranzit = getKey.getValue();
                    break;
                }

            tranzit += 1;
            graph.put(dateString[i], tranzit);

        }

        return graph;
    }
}
