package net.internalerror.web;

import lombok.AllArgsConstructor;
import net.internalerror.data.entity.SecurityToken;
import net.internalerror.data.entity.Task;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.SecurityTokenRepository;
import net.internalerror.data.repository.TaskRepository;
import net.internalerror.data.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {

    private final UserRepository userRepository;
    private final SecurityTokenRepository securityTokenRepository;
    private final TaskRepository taskRepository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserModel> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(new UserModel(
                    user.getId(),
                    user.getFirstname().hashCode(),
                    user.getLastname().hashCode(),
                    user.getEmail().hashCode()
            ));
        }

        model.addAttribute("users", users);
        return "users";
    }


    @GetMapping("/security-tokens")
    public String securityTokens(Model model) {
        List<SecurityTokenModel> securityTokens = new ArrayList<>();
        for (SecurityToken securityToken : securityTokenRepository.findAll()) {
            securityTokens.add(new SecurityTokenModel(
                    securityToken.getId(),
                    securityToken.getUser().hashCode(),
                    securityToken.getValidUntil().hashCode(),
                    securityToken.getToken().hashCode()
            ));
        }

        model.addAttribute("securityTokens", securityTokens);
        return "security-tokens";
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        List<TaskModel> tasks = new ArrayList<>();
        for (Task task : taskRepository.findAll()) {
            tasks.add(new TaskModel(
                    task.getId(),
                    task.getUser().hashCode(),
                    task.getDue().hashCode(),
                    task.getName().hashCode(),
                    task.getDetails().hashCode()
            ));
        }

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    public record UserModel(long id, int firstname, int lastname, int email) {

    }

    public record SecurityTokenModel(long id, int user, int validUntil, int token) {

    }

    public record TaskModel(long id, int user, int due, int name, int detail) {

    }

}
