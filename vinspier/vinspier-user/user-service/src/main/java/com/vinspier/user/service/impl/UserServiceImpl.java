package com.vinspier.user.service.impl;

import com.vinspier.common.util.NumberUtils;
import com.vinspier.user.mapper.UserMapper;
import com.vinspier.user.pojo.User;
import com.vinspier.user.service.UserService;
import com.vinspier.user.util.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    // redis key前缀
    private static final String KEY_PREFIX = "user-verify:";

    @Override
    public Boolean checkUer(String data, Long type) {
        User record = new User();
        if (type == 1){
            record.setUsername(data);
        } else if (type == 2){
            record.setPhone(data);
        } else {
            return null;
        }
        return this.userMapper.selectCount(record) == 0;
    }

    @Override
    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)){
            return;
        }
        // 生成验证码
        String code = NumberUtils.generateCode(6);
        // 发送消息到rabbitMQ
        HashMap<String, String> msg = new HashMap<>();
        msg.put("phone",phone);
        msg.put("code", code);
        this.amqpTemplate.convertAndSend("vinspier.sms.exchange","verifyCode.sms", msg);
        // 把验证码保存到redis中 5mi过期时间
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone,code, 5, TimeUnit.MINUTES);
    }

    @Override
    public void register(User user, String code) {
        // 查询redis中的验证码
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
        // 1、校验验证码
        if (!StringUtils.equals(code, redisCode)){
            return;
        }
        // 2、生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 3、加盐加密
        String codePassword = CodecUtils.md5Hex(user.getPassword(), salt);
        user.setPassword(codePassword);
        // 4、新增用户
        user.setId(null);
        user.setCreated(new Date());
        this.userMapper.insertSelective(user);
        // ToDo 删除redis中的code
    }

    @Override
    public User queryUser(String username, String password) {
        // 根据用户名去数据库中查找用户
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (user == null){
            return null;
        }
        // 获取盐，对用户输入的密码进行加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());
        // 和数据库中的密码进行比较
        if (StringUtils.equals(password, user.getPassword())){
            return user;
        }
        return null;
    }
}
