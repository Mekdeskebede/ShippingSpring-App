package com.bootcamp.deliver.Controller;
import java.util.List;
import javax.validation.Valid;
import com.bootcamp.deliver.Model.Cart_Item;
import com.bootcamp.deliver.Model.Product;
import com.bootcamp.deliver.Model.User;
import com.bootcamp.deliver.Repository.Cart_ItemRepository;
import com.bootcamp.deliver.Repository.ProductRepository;

import com.bootcamp.deliver.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@CrossOrigin("http://localhost:8080")

public class PackageController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private Cart_ItemRepository packRepo;

    @Autowired
    private UserRepository repo;

    @PostMapping("/addtocart/{id}")
    public String addtocart(
            @Valid Cart_Item cart,
            @PathVariable("id") long id,
            @RequestParam("numofprod") int numofprod) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User euser= repo.findByEmail(currentPrincipalName);

        Product prod = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

        cart.setUser(euser);     
        cart.setProduct(prod);
        cart.setNumofprod(numofprod);
        packRepo.save(cart);
        return "redirect:/userproduct_list";
    }


    @GetMapping("/mycart")
    public String viewproductPage(
        @Valid Cart_Item packages,
        Model model) {
        List<Cart_Item> products = packRepo.findAll();
        
        List<Product> product = productRepo.findAll();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User euser= repo.findByEmail(currentPrincipalName);
        Long userid = euser.getId();

        model.addAttribute("products", products);
        model.addAttribute("product", product);
        model.addAttribute("userid", userid);
        return "orderedproduct";
    }

}

