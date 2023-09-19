/*
Работа с пользователем:
•	Регистрация пользователя (авторизация не требуется).
•	Получение информации о текущем авторизованном пользователе.
*/

package Alex.Stroki.CMD;

import Alex.Stroki.SQL.User;
import Alex.Stroki.SQL.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static Alex.Stroki.StrokiApplication.authorizeUser;

@RestController
public class Authorization {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/Авторизация")
    public String authorization(@RequestParam String login, @RequestParam String password, Model model) {
        String status = "";

        User user = new User(login, password);
        boolean[] authorize = getUser(login, password, model);

        if(!authorize[0]) { // Если нет записи
            userRepository.save(user);
            status = "Пользователь " + login + " был успешно зарегистрирован";
        }
        else {
            if(!authorize[1]) { // Если запись есть, но пароль не совпадает
                authorizeUser = null;
                status = "ОШИБКА!!! \nДля пользователя " + login + " был указан неверный пароль.";
            }
            else { // Пароль совпадает
                authorizeUser = login;
                status = "Пользователь " + login + " был успешно авторизован";
            }
        }

        return status;
    }

    public boolean[] getUser(String login, String password, Model model) {

        boolean[] authorize = new boolean[] { false, false };

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("login", users);

        for(User user:users) {
            if(login.equals(user.getLogin())) {
                if (password.equals(user.getPassword()))
                    authorize[1] = true; // Пароль совпадает

                authorize[0] = true; // Запись существует
                break;
            }
        }

        return authorize;
    }

    @GetMapping("/Информация об авторизованном пользователе")
    public String userInfo(Model model) {
        if (authorizeUser != null) {
            String userInfo = "Пользователь: " + authorizeUser + "\nПароль: ";
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("login", users);

            for(User user:users)
                if(authorizeUser.equals(user.getLogin())) {
                    String password = user.getPassword();
                    for (int i = 0; i < password.length(); i++)
                        userInfo += "*";
                }

            return userInfo;
        }
        else
            return "Вы не авторизованы в системе.";
    }

}
