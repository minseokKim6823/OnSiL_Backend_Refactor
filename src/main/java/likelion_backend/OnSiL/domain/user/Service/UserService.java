package likelion_backend.OnSiL.domain.user.Service;

import likelion_backend.OnSiL.domain.user.entity.User;
import likelion_backend.OnSiL.domain.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setNickname(userDetails.getNickname());
        user.setProfilePic(userDetails.getProfilePic());
        user.setHealthCon(userDetails.getHealthCon());
        user.setTextSize(userDetails.getTextSize());
        user.setKakaoId(userDetails.getKakaoId());
        user.setLoginState(userDetails.getLoginState());
        user.setStatus(userDetails.getStatus());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
