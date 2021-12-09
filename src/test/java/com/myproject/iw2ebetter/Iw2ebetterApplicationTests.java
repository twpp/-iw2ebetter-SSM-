package com.myproject.iw2ebetter;

import com.myproject.iw2ebetter.pojo.User;
import com.myproject.iw2ebetter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class Iw2ebetterApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        Connection connection = null;
        try {
           connection  = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
        }

        System.out.println(connection);
        //jdbcTemplate.execute("create table test(" +
        //        "num int)");

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        ops.set("iw2ebetter","iw2ebetter");

    }
    
    
    @Test
    void testGetUsers() {
        //List<User> users = userService.getUsers(1,2);
        //for(User user:users){
        //    System.out.println(""+user.getName()+user.getTelephone()+user.getAddress()+user.getUid());
        //}
    }

    @Test
    void testGetDir(){

        String s = System.getProperty("user.dir");
        System.out.println(s);
    }

}
