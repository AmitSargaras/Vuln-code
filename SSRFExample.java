
package com.owasp.demo.vulnerable;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.URL;
import org.apache.commons.io.IOUtils;

@RestController
@RequestMapping("/vuln")
public class SSRFExample {

    @GetMapping("/fetch")
    public String fetchExternal(@RequestParam String url) throws IOException {
        InputStream input = new URL(url).openStream(); // SSRF vulnerability
        return IOUtils.toString(input, "UTF-8"); // Sensitive info exposure
    }
}
