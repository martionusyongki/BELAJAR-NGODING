package lcy.restful.service;

import lcy.restful.entity.User;
import lcy.restful.model.LoginUserRequest;
import lcy.restful.model.TokenResponse;
import lcy.restful.repository.UserRepository;
import lcy.restful.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request) {
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password wrong"));

        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setToken_expired_at(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getToken_expired_at())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password wrong");
        }
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setToken_expired_at(null);

        userRepository.save(user);
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
    }
}
