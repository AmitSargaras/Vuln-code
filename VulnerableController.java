
package com.owasp.demo.controller;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class VulnerableController {

    // A1: Broken Access Control
    @GetMapping("/admin")
    public String getAdminData(@RequestParam String userRole) {
        if ("admin".equals(userRole)) {
            return "Sensitive admin data";
        }
        return "Access Denied";
    }

    // A2: Cryptographic Failures
    @PostMapping("/storeCard")
    public String storeCard(@RequestParam String cardNumber) throws Exception {
        FileWriter fw = new FileWriter("cards.txt", true);
        fw.write(cardNumber + "\n");
        fw.close();
        return "Stored!";
    }

    // A3: Injection (SQLi)
    @GetMapping("/user")
    public String getUser(@RequestParam String id) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id + "'");
        if (rs.next()) return rs.getString("username");
        return "User not found";
    }

    // A4: Insecure Design
    @PostMapping("/transfer")
    public String transfer(@RequestParam int amount, @RequestParam String toAccount) {
        return "Transferred " + amount + " to " + toAccount;
    }

    // A5: Security Misconfiguration
    @GetMapping("/config")
    public String getConfig() {
        return System.getenv().toString();
    }

    // A6: Vulnerable & Outdated Components
    // Apache Commons IO 2.4 is used (vulnerable version)

    // A7: Identification & Authentication Failures
    @PostMapping("/login")
    public String login(@RequestParam String user, @RequestParam String pass) {
        if ("admin".equals(user) && "admin123".equals(pass)) {
            return "Welcome admin!";
        }
        return "Login failed!";
    }

    // A8: Software & Data Integrity Failures
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File uploaded = new File("/tmp/" + file.getOriginalFilename());
        file.transferTo(uploaded);
        return "File uploaded";
    }

    // A9: Security Logging & Monitoring Failures
    @GetMapping("/audit")
    public String auditTrail() {
        // No logging implemented
        return "Audit done";
    }

    // A10: SSRF
    @GetMapping("/fetch")
    public String fetchUrl(@RequestParam String url) throws Exception {
        InputStream in = new URL(url).openStream();
        return IOUtils.toString(in, "UTF-8");
    }
}
