
package com.owasp.demo.vulnerable;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/vuln")
public class FileUploadExample {

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File destination = new File("/tmp/uploads/" + fileName);
        file.transferTo(destination); // No validation on file type or path
        return "Uploaded!";
    }
}
