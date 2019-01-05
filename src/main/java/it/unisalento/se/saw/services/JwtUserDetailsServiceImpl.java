package it.unisalento.se.saw.services;

import it.unisalento.se.saw.exceptions.UserNotFoundException;
import it.unisalento.se.saw.models.Authority;
import it.unisalento.se.saw.models.AuthorityName;
import it.unisalento.se.saw.models.User;
import it.unisalento.se.saw.security.JwtUserFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private it.unisalento.se.saw.repositories.UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	try {
	        it.unisalento.se.saw.domain.User userDB = userRepository.checkUser(username);
	        
	    	User user = new User();
	    	
			user.setUsername(userDB.getEmail());
	    	user.setPassword(userDB.getPassword());
	
	    	user.setEnabled(true);
	    	
	    	AuthorityName name = AuthorityName.ROLE_USER;
	    	
	    	List<Authority> authorities = new ArrayList<Authority>();
	    	Authority authority = new Authority();
	    	//authority.setId(Long.valueOf("1"));
	    	authority.setName(name);
	    	authorities.add(authority);
	    	user.setAuthorities(authorities);
	
	        return JwtUserFactory.create(user);
	        
    	} catch (NullPointerException e) {
    		throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    	}
    }
}