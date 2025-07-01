
package com.owasp.demo.vulnerable;

import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
@RequestMapping("/vuln")
public class SQLInjectionExample {

    // Hardcoded credentials - A7
    private String dbUser = "admin";
    private String dbPass = "admin123";

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", dbUser, dbPass);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) return "Login success!";
        } catch (Exception e) {
            // No logging
        }
        return "Login failed";
    }
}
