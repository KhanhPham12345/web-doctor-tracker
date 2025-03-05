package com.luv2code.springsecurity.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Create your response body (could be any JSON object you wish)
        ResponseBody responseBody = new ResponseBody("Authentication successful", HttpStatus.OK.value());

        // Set the response type as JSON
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());

        // Write the response body as JSON
        objectMapper.writeValue(response.getWriter(), responseBody);
    }

    // You can create your custom response object to hold any relevant data
    static class ResponseBody {
        private String message;
        private int status;

        public ResponseBody(String message, int status) {
            this.message = message;
            this.status = status;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
