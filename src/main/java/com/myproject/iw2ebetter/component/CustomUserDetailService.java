package com.myproject.iw2ebetter.component;

import com.myproject.iw2ebetter.pojo.UserDTO;
import com.myproject.iw2ebetter.pojo.Role;
import com.myproject.iw2ebetter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    
    

    @Override
    //记住这个方法是 处理登录进来的用户信息的！！！
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        Set<Role> set = user.getRoles();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(Role role:set){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
        }

        //我们将会在注册的时候加密 所以这里不要加密 只需要将权限绑定
        return new User(username,user.getPassword(),grantedAuthorities);
    }
}
