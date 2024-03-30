package com.test.SqlLogin.controller;

import com.test.SqlLogin.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    private final DataSource dataSource;

    @Autowired
    public UsersController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam("id") String id, @RequestParam("pw") String pw) {
        ModelAndView modelAndView = new ModelAndView();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ? AND pw = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, pw);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                if (isAdmin) {
                    // 관리자인 경우
                    List<Users> users = getUsersList(connection);
                    modelAndView.setViewName("adminWelcome");
                    modelAndView.addObject("message", "Welcome, Admin " + name);
                    modelAndView.addObject("users", users);
                    modelAndView.addObject("id", id); // 아이디 추가
                    modelAndView.addObject("name", name); // 이름 추가
                } else {
                    // 일반 사용자인 경우
                    modelAndView.setViewName("userWelcome");
                    modelAndView.addObject("message", "Welcome, " + name);
                    modelAndView.addObject("id", id); // 아이디 추가
                    modelAndView.addObject("name", name); // 이름 추가
                }

            } else {
                modelAndView.setViewName("login");
                modelAndView.addObject("error", "Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "An error occurred");
        }

        return modelAndView;
    }

    private List<Users> getUsersList(Connection connection) throws SQLException {
        List<Users> users = new ArrayList<>();

        String sql = "SELECT id, name FROM users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Users user = new Users();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            users.add(user);
        }

        return users;
    }
}
