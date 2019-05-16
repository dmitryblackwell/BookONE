package com.blackwell.web;

import com.blackwell.constant.PageConstants;
import com.blackwell.entity.Book;
import com.blackwell.entity.Order;
import com.blackwell.entity.User;
import com.blackwell.service.BookService;
import com.blackwell.service.FileUploadService;
import com.blackwell.service.UserService;
import com.blackwell.util.OrderNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/{username}")
    public String getUser(@PathVariable String username, Model model){
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());
        return "userview";
    }

    @PostMapping("/{username}/orders")
    @ResponseBody
    public String saveOrder(@RequestParam("isbn") long isbn, @PathVariable("username") String username, @RequestParam("quantity") int quantity) {
        userService.addOrderForUser(username, bookService.getBook(isbn), quantity);
        return "order saved";
    }


    private static final String IMG_PATH = "resources/uploaded-images/users";

    @PostMapping("/upload-image")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username, HttpSession session) {
        String loginUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        final String UPLOADED_PATH = session.getServletContext().getRealPath("/") + IMG_PATH + File.separator + String.valueOf(username) + ".jpg";

        if(username.equals(loginUsername))
            fileUploadService.uploadFile(file, UPLOADED_PATH);

        return PageConstants.REDIRECT_USERS + "/" + username;
    }
}