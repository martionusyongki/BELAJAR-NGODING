package lcy.restful.service;

import lcy.restful.entity.User;
import lcy.restful.model.RegisterUserRequest;
import lcy.restful.model.UpdateUserRequest;
import lcy.restful.model.UserResponse;
import lcy.restful.repository.UserRepository;
import lcy.restful.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validateService;

    @Transactional
    public void register(RegisterUserRequest request) {
        validateService.validate(request);

        if (userRepository.existsById(request.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already registered");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);
    }

    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request) {
        validateService.validate(request);
        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}
