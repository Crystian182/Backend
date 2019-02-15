package it.unisalento.se.saw.restapi;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.core.JsonProcessingException;

import it.unisalento.se.saw.Iservices.IFCMService;
import it.unisalento.se.saw.Iservices.IUserService;
import it.unisalento.se.saw.dto.UserDTO;
import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.exceptions.WrongCredentialsException;
import it.unisalento.se.saw.security.JwtAuthenticationRequest;
import it.unisalento.se.saw.security.JwtTokenUtil;
import it.unisalento.se.saw.services.JwtAuthenticationResponse;
import it.unisalento.se.saw.services.UserService;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationRestController {
	
	//private static String SERVER_KEY = "AAAAQVkzfAk:APA91bGHHPI1O839zNgKoGU73IN0jQ-2_kce-3BtOGZw9yC9C_3FxhYOpG7V7bEKIfpzsKmzboxBR5SwJsE8nvKLYafFD4fLBFD4kktCZ-jNR6qgXMSUySuzkmCthBDSJcWOlf7OH8f8";
    //private static String DEVICE_TOKEN = "YOUR_DEVICE_TOKEN";

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private IUserService userService;
    

    @RequestMapping(value = "public/login", method = RequestMethod.POST)
    public UserDTO createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device, HttpServletResponse response) throws Exception {
    	
    	try {
        // Effettuo l autenticazione
	        final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        authenticationRequest.getEmail(),
	                        authenticationRequest.getPassword()
	                )
	        );
	    	
	        SecurityContextHolder.getContext().setAuthentication(authentication);
        
    	} catch (BadCredentialsException e) {
			// TODO: handle exception
    		throw new WrongCredentialsException();
		}
    	
    	
        // Genero Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        response.setHeader(tokenHeader,token);
        String title = "My First Notification";
        String message = "Hello, I'm push notification";
        // Ritorno il token
        return userService.login(authenticationRequest.getEmail(), token);
        //return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
    }
    
    /*public static void sendPushNotification(String title, String message) throws Exception {
        String pushMessage = "{\"data\":{\"title\":\"" +
                title +
                "\",\"message\":\"" +
                message +
                "\"},\"to\":\"" +
                DEVICE_TOKEN +
                "\"}";
        // Create connection to send FCM Message request.
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Send FCM message content.
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(pushMessage.getBytes());

        System.out.println(conn.getResponseCode());
        System.out.println(conn.getResponseMessage());
    }*/

    @RequestMapping(value = "refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(tokenHeader);

        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            response.setHeader(tokenHeader,refreshedToken);

            return ResponseEntity.ok(refreshedToken);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @RequestMapping(value = "public/istokenvalid", method = RequestMethod.GET)
    public boolean isTokenValid(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(tokenHeader);

        try {
        	if (jwtTokenUtil.canTokenBeRefreshed(token)) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
        	return false;
        }
        
    }
    

}