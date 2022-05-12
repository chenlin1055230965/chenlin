package com.qdc.demoeurekaauth_server.service.impl;

import com.qdc.demoeurekaauth_server.pojo.Role;
import com.qdc.demoeurekaauth_server.pojo.User;
import com.qdc.demoeurekaauth_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        if(user == null||user.getId() < 1){
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(),user.getPassword(),
                        true,true,true,true,getGrantAuthoritiest(user));
    }

    private Collection<? extends GrantedAuthority> getGrantAuthoritiest(User user) {
        Set<Role> role = new HashSet<Role>();
        Role role1 = new Role(1,"admin");
        role.add(role1);
        user.setRoles(role);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Role r : user.getRoles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
        }
        return authorities;
    }
}
