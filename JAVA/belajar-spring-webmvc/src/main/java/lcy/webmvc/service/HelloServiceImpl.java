package lcy.webmvc.service;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        if (name == null) {
            return "Hello Guest";
        }
        return "Hello LCY";
    }
}
