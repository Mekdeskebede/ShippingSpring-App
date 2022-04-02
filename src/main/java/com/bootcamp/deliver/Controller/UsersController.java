package com.bootcamp.deliver.Controller;

import javax.validation.Valid;

import com.bootcamp.deliver.Model.Address;
import com.bootcamp.deliver.Model.Product;
import com.bootcamp.deliver.Model.User;
import com.bootcamp.deliver.Repository.AddressRepository;
import com.bootcamp.deliver.Repository.ProductRepository;
import com.bootcamp.deliver.Repository.UserRepository;

import org.springframework.ui.Model;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UsersController {

  public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

  @Autowired
  private UserRepository repo;

  @Autowired
  private AddressRepository addRepo;

  @Autowired
  private ProductRepository productRepo;

  @GetMapping("")
  public String viewHomePage() {
    return "index";
  }

  @GetMapping("/login")
  public String loginpage(Model model) {
    // model.addAttribute("product", new Product());
    {
      return "login";
    }
  }
  @GetMapping("/signup_1")
  public String showSignupPage(Model model) {
    model.addAttribute("user", new User());
    return "signup_1";
  }


  @PostMapping("/signup_2")
  public String addNewUser(

      Address address,
      @Valid User user,
      @RequestParam("email") String email,
      @RequestParam("firstname") String firstname,
      @RequestParam("lastname") String lastname,
      @RequestParam("Companyname") String Companyname,
      final @RequestParam("file") MultipartFile file,

      final BindingResult bindingResult,
      Model model)
       {

      boolean eUser = repo.findAll().isEmpty();
      if (bindingResult.hasErrors()) {
      model.addAttribute("signup_1", user);
      return "signup_1";
          }
    try {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      String encodedPassword = encoder.encode(user.getPassword());
      user.setPassword(encodedPassword);

      String fileTempo = file.getOriginalFilename();
      String fileName = fileTempo.replaceAll(" ", "_");
      String filePath = Paths.get(uploadDirectory, fileName).toString();
      String fileType = file.getContentType();
      long size = file.getSize();
      String fileSize = String.valueOf(size);
      fileName = "/uploads/" + fileName;


      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
      stream.write(file.getBytes());
      stream.close();
      user.setEmail(email);
      user.setFirstname(firstname);
      user.setLastname(lastname);
      user.setCompanyname(Companyname);
      user.setFilePath(filePath);
      user.setFileSize(fileSize);
      user.setFileType(fileType);
      user.setFileName(fileName);

       if (eUser) {
        user.setRole("Admin");
      } else {
        user.setRole("User");
      }

      repo.save(user);
    } catch (Exception e) {
        bindingResult.rejectValue("email", "user.email", "An account already exists for this email.");
        model.addAttribute("signup_1", user);
        return "signup_1";
    }
    return "signup_2";
  }

  @GetMapping("/product_list")
  public String afterLogin(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();

    User auth = repo.findByEmail(currentPrincipalName);
    String role = (String) auth.getRole();

    if (!role.equals("Admin")) {
      model.addAttribute("products", productRepo.findAll());
      return "redirect:/userproduct_list";
    } else {

      model.addAttribute("products", productRepo.findAll());
      return "adminproduct_list";
    }
  }

  @GetMapping("/adminproduct_list")
  public String listproduct(Model model) {
    List<Product> products = productRepo.findAll();
    model.addAttribute("products", products);

    return "adminproduct_list";
  }

  }











