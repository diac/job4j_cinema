package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SimpleUserService;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void whenLoginPage() {
        Model model = mock(Model.class);
        UserService userService = mock(SimpleUserService.class);
        UserController userController = new UserController(userService);
        String page = userController.loginPage(model, null);
        verify(model).addAttribute("fail", false);
        assertThat("user/login").isEqualTo(page);
    }

    @Test
    public void whenLoginSuccess() {
        Model model = mock(Model.class);
        HttpServletRequest request = new MockHttpServletRequest();
        User user = new User(0, "test", "test", "test", "test");
        UserService userService = mock(SimpleUserService.class);
        when(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(Optional.of(user));
        UserController userController = new UserController(userService);
        String redirect = userController.login(user, request);
        assertThat("redirect:/").isEqualTo(redirect);
    }

    @Test
    public void whenLoginFail() {
        Model model = mock(Model.class);
        HttpServletRequest request = new MockHttpServletRequest();
        User user = new User(0, "test", "test", "test", "test");
        UserService userService = mock(SimpleUserService.class);
        when(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword()))
                .thenReturn(Optional.empty());
        UserController userController = new UserController(userService);
        String redirect = userController.login(user, request);
        assertThat("redirect:/login?fail=true").isEqualTo(redirect);
    }

    @Test
    public void whenLogout() {
        HttpSession httpSession = mock(HttpSession.class);
        User user = new User(0, "test", "test", "test", "test");
        httpSession.setAttribute("user", user);
        UserService userService = mock(SimpleUserService.class);
        UserController userController = new UserController(userService);
        String redirect = userController.logout(httpSession);
        verify(httpSession).invalidate();
        assertThat("redirect:/login").isEqualTo(redirect);
        assertThat(httpSession.getAttribute("user")).isNull();
    }
}