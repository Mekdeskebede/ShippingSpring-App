package com.bootcamp.deliver.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;
import com.bootcamp.deliver.Model.Cart_Item;
import com.bootcamp.deliver.Model.Product;
import com.bootcamp.deliver.Model.Shipping_Cart;
import com.bootcamp.deliver.Model.User;
import com.bootcamp.deliver.Repository.Cart_ItemRepository;
import com.bootcamp.deliver.Repository.Shipping_CartRepository;
import com.bootcamp.deliver.Repository.ProductRepository;
import com.bootcamp.deliver.Repository.UserRepository;

import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.LEAST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;

@Controller
@CrossOrigin("http://localhost:8080")

public class Productcontroller {

  public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

  @Autowired
  private ProductRepository productRepo;

  @Autowired
  private Cart_ItemRepository packRepo;

  @GetMapping("/userproduct_list")
  public String viewproductPage(Model model) {
    List<Product> products = productRepo.findAll();
    model.addAttribute("products", products);
    model.addAttribute("cart", new Cart_Item());
    return "userproduct_list";
  }

  @GetMapping("/addproduct")
  public String addproductpage(Model model) {
    model.addAttribute("product", new Product());
    return "addproduct";
  }

  @GetMapping("/product/edit/{id}")
  public String updatepage(@PathVariable long id, Model model) {
    Product products = productRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

    model.addAttribute("products", products);
    return "editproduct";
  }

  @PostMapping("/product/{id}")
  public String updateProduct(@PathVariable("id") long id, @RequestParam("image") MultipartFile file,
      @Valid Product products,
      BindingResult result, Model model) {

    if (result.hasErrors()) {
      products.setId(id);
      return "editproduct";
    }
    try {
      Product existProduct = productRepo.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      products.setId(existProduct.getId());
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

      products.setFilePath(filePath);
      products.setFileSize(fileSize);
      products.setFileType(fileType);
      products.setFileName(fileName);
      productRepo.save(products);

    } catch (Exception e) {
      e.printStackTrace();

    }
    return "redirect:/adminproduct_list";
  }

  @GetMapping("/delete/{id}")
  public String deleteUser(@PathVariable("id") long id, Model model) {
    Product products = productRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    productRepo.delete(products);
    return "redirect:/adminproduct_list";
  }

  @GetMapping("/productdetail/{id}")
  public String showUpdateForm(@PathVariable("id") long id, Model model) {
    Product products = productRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
    // long ids = products.getId();
    Cart_Item cart = new Cart_Item();
    // cart.setProductid(ids);
    model.addAttribute("products", products);
    model.addAttribute("cart", cart);

    return "userproductdetail";
  }

  @PostMapping("/addproduct_list")
  public String addNewCourse(@Valid Product products,
      @RequestParam("image") MultipartFile file,
      @RequestParam("productName") String productName,
      @RequestParam("minNumberofpallet") Double minNumberofpallet,
      @RequestParam("maxNumberofproduct") Double maxNumberofproduct) {

    try {
      String fileTempo = file.getOriginalFilename();
      String fileName = fileTempo.replaceAll(" ", "_");
      String filePath = Paths.get(uploadDirectory, fileName).toString();
      String fileType = file.getContentType();
      long size = file.getSize();
      String fileSize = String.valueOf(size);
      Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
      fileName = "/uploads/" + fileName;

      // Save the file locally
      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
      stream.write(file.getBytes());
      stream.close();
      products.setProductName(productName);
      products.setMaxNumberofproduct(maxNumberofproduct);
      products.setFilePath(filePath);
      products.setFileSize(fileSize);
      products.setFileType(fileType);
      products.setFileName(fileName);
      // courses.setCreatedDate(currentTimestamp);
      productRepo.save(products);

    } catch (IOException e) {
      e.printStackTrace();

    }
    return "redirect:/adminproduct_list";
  }

  
}

