package com.piu.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.piu.sys.dao.UserDao;
import com.piu.sys.entity.CustomUser;
import com.piu.sys.entity.User;

@Service
public class CustomUserService  implements UserDetailsService{ //自定义UserDetailsService 接口

    @Autowired
    UserDao userDao;
    
    
    public UserDetails loadUserByUsername(String username) { 
    	//重写loadUserByUsername 方法获得 userdetails 类型用户
        User user = userDao.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //user.setRoles(roleDao.findByUserId(user.getId()));
        
        
        
      /*  List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities。
        for(Role role:user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), authorities);*/
        return  new CustomUser(user);
    }
    
}
