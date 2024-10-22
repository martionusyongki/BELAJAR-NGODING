package lcy.webmvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lcy.webmvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Objects;

@Controller
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public void helloWorld(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String responseBody = helloService.hello(name);

//        if (Objects.isNull(name)) {
//            name = "Guest";
//        }

//        response.getWriter().println("Hello " + name);

        response.getWriter().println(responseBody);
    }
}
