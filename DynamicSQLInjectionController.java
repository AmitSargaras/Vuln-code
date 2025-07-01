
package com.owasp.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.sql.*;

@RestController
@RequestMapping("/api")
public class DynamicSQLInjectionController {

    // Dynamic SQL Injection Vulnerability Example
    @GetMapping("/search")
    public String searchUser(@RequestParam String username, @RequestParam String password) {
        String result = "";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
            Statement stmt = conn.createStatement();

            // Vulnerable dynamic SQL
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                result = "Welcome " + rs.getString("username");
            } else {
                result = "Invalid credentials";
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        return result;
    }
}
