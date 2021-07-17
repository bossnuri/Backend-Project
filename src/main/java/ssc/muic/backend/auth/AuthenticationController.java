package ssc.muic.backend.auth;

import ssc.muic.backend.SimpleResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

//    @GetMapping
//    public String test(){
//        return "If this message is shown, it means login is successful because we didn't set to permit this path ";
//    }

    @PostMapping("/api/log")
    public SimpleResponseDTO login(HttpServletRequest request){

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            // check if there is a current user logged in, if so log that user out first
            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principle != null && principle instanceof org.springframework.security.core.userdetails.User) {
                request.logout();
            }
            request.login(username, password);
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("You are logged in successfully")
                    .build();
        } catch(SecurityException | ServletException e){
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message("Incorrect username or password")
                    .build();
        }
    }
    @GetMapping("/api/logout")
    public SimpleResponseDTO logout(HttpServletRequest request){
        try {
            request.logout();
            return SimpleResponseDTO
                    .builder()
                    .success(true)
                    .message("You are successfully logged out")
                    .build();
        } catch (ServletException e) {
            return SimpleResponseDTO
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }
}
