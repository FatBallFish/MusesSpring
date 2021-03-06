package muses.art.service.user.impl;

import muses.art.dao.user.UserDao;
import muses.art.entity.user.User;
import muses.art.model.user.UserModel;
import muses.art.service.user.UserService;
import muses.art.util.Hasher;
import muses.art.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Boolean addNewUser(String username, String password, String mobile) {
        if (findUsernameIsUsed(username) || findMobileIsUsed(mobile)) { // 若用户名或手机号被使用
            return false;
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(generateEncryptedPassword(password));
            user.setMobile(mobile);
            user.setLevel(0);
            user.setNickname("User_" + mobile);
            user.setToken(generateToken());
            user.setAvatar("https://s1.ax1x.com/2018/06/22/PpsPDf.jpg");
            userDao.save(user);
            return true;
        }
    }

    private String generateToken() {
        return TokenGenerator.getToken();
    }

    @Override
    public Boolean deleteUser(Integer id) {
        User user = userDao.get(User.class, id);
        if (user != null) {
            userDao.delete(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updatePassword(Integer userId, String password) {
        User user = userDao.get(User.class, userId);
        if (user != null) {
            user.setPassword(generateEncryptedPassword(password));
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateInformation(Integer userId, Integer gender, Date birthday, String email, String nickname) {
        User user = userDao.get(User.class, userId);
        if (user != null) {
            user.setGender(gender);
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setNickname(nickname);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean findUsernameIsUsed(String username) {
        String HQL = "from User where username=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        User user = userDao.get(HQL, map);
        if (user != null) {
            return true; // 用户名已被使用
        }
        return false; // 用户名未被使用
    }

    @Override
    public Boolean findMobileIsUsed(String mobile) {
        String HQL = "from User where mobile=:mobile";
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        User user = userDao.get(HQL, map);
        if (user != null) {
            return true; // 手机号已被使用
        }
        return false; // 手机号未被使用
    }

    @Override
    public UserModel findUserByUsername(String username) {
        String HQL = "from User where username=:username";
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        User user = userDao.get(HQL, map);
        return entity2model(user);
    }

    @Override
    public UserModel findUserById(Integer id) {
        User user = userDao.get(User.class, id);
        return entity2model(user);
    }

    @Override
    public UserModel findUserByMobile(String mobile) {
        String HQL = "from User where mobile=:mobile";
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        User user = userDao.get(HQL, map);
        return entity2model(user);
    }

    @Override
    public UserModel findUserByToken(String token) {
        String HQL = "from User where token=:token";
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        User user = userDao.get(HQL, map);
        return entity2model(user);
    }

    public String generateEncryptedPassword(String password) {
        return new Hasher().encode(password, 20000);
    }

    private UserModel entity2model(User user) {
        if (user != null) {
            UserModel userModel = new UserModel();
            userModel.setAvatar(user.getAvatar());
            userModel.setEmail(user.getEmail());
            userModel.setGender(user.getGender());
            userModel.setId(user.getId());
            userModel.setLevel(user.getLevel());
            userModel.setBirthday(user.getBirthday());
            userModel.setMobile(user.getMobile());
            userModel.setToken(user.getToken());
            userModel.setPassword(user.getPassword());
            userModel.setNickname(user.getNickname());
            userModel.setUsername(user.getUsername());
            return userModel;
        } else {
            return null;
        }
    }
}
