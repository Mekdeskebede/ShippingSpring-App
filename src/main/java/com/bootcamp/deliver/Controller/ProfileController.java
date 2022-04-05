package com.bootcamp.deliver.Controller;

import javax.validation.Valid;
import com.bootcamp.deliver.Model.User;
import com.bootcamp.deliver.Repository.AddressRepository;
import com.bootcamp.deliver.Repository.UserRepository;
import org.springframework.ui.Model;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProfileController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";
    @Autowired
    private UserRepository repo;


@GetMapping("/users_list")
public String viewUserList(Model model) {
    List<User> user = repo.findAll();
    
    model.addAttribute("user", user);
    return "users_list";
}

@GetMapping("/deleteUser/{id}")
public String deleteUser(@PathVariable("id") long id, Model model) {
  User user = repo.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
  repo.delete(user);
  return "redirect:/users_list";
}

@GetMapping("/profile")
public String addFormProduct(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User auth = repo.findByEmail(currentPrincipalName);
    if (auth == null) {
        return "redirect:/";
    }
    User user = repo.findUserById(auth.getId());
    model.addAttribute("user", user);
    return "profile";
}

@GetMapping("/profileAdmin")
public String adminProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User auth = repo.findByEmail(currentPrincipalName);
    if (auth == null) {
        return "redirect:/";
    }
    User user = repo.findUserById(auth.getId());
    model.addAttribute("user", user);
    return "profileAdmin";
}



@GetMapping("/profile/edit/{id}")
public String updatepage(@PathVariable long id, Model model) {
    User user = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

    model.addAttribute("user", user);
    return "editprofile";
}

@PostMapping("/profile/{id}")
public String updateProduct(@PathVariable("id") long id, @RequestParam("image") MultipartFile file,
        @RequestParam("firstname") String firstname,
        @RequestParam("lastname") String lastname,
        @RequestParam("Companyname") String companyname,
        @Valid User user,
        BindingResult result, Model model) {
    boolean eUser = repo.findAll().isEmpty();

    if (result.hasErrors()) {
        user.setId(id);
        return "editprofile";
    }
    try {
        User existProduct = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setId(existProduct.getId());
        if (eUser) {
            user.setRole("Admin");
        } else {
            user.setRole("user");
        }
        String fileTempo = file.getOriginalFilename();
        String fileName = fileTempo.replaceAll(" ", "_");
        String filePath = Paths.get(uploadDirectory, fileName).toString();
        String fileType = file.getContentType();
        long size = file.getSize();
        String fileSize = String.valueOf(size);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        fileName = "/uploads/" + fileName;

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(file.getBytes());
        stream.close();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User auth = repo.findByEmail(currentPrincipalName);
        String role = (String) auth.getRole();
        if (!role.equals("Admin")) {
            user.setRole("User");
        } else {
            user.setRole("Admin");
        }
        user.setFilePath(filePath);
        user.setFileSize(fileSize);
        user.setFileType(fileType);
        user.setFileName(fileName);
        user.setCompanyname(companyname);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        repo.save(user);

    } catch (Exception e) {
        e.printStackTrace();

    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User auth = repo.findByEmail(currentPrincipalName);
    String role = (String) auth.getRole();
    if (!role.equals("Admin")) {
        return "redirect:/profile";
    } else {
        return "redirect:/profileAdmin";
    }
    
}


@GetMapping("/forgot")

    public String forgotpassword(){
        return "email_form";
    }
    
@PostMapping("/sendemail")
    public String sendemail(){
        return "verification";
    }
}