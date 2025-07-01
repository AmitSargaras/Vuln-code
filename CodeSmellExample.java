
package com.owasp.demo.vulnerable;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/vuln")
public class CodeSmellExample {

    // Hardcoded secret - Secret Detection
    private static final String AWS_SECRET = "AKIAIOSFODNN7EXAMPLE";

    @GetMapping("/deadcode")
    public String process() {
        helper(); // Active usage
        unusedMethod(); // Dead code
        return "Processing done";
    }

    public void helper() {
        System.out.println("Helper called");
    }

    public void duplicateLogic() {
        System.out.println("This is duplicate code logic");
    }

    public void anotherDuplicateLogic() {
        System.out.println("This is duplicate code logic");
    }

    public void unusedMethod() {
        int x = 10;
        int y = 20;
        int z = x + y;
        // No usage
    }
}
