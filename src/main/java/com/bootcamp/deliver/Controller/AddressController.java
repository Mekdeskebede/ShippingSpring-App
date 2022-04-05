package com.bootcamp.deliver.Controller;

import com.bootcamp.deliver.Model.Address;
import com.bootcamp.deliver.Repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {

  @Autowired
  private AddressRepository addRepo;

  @GetMapping("/signup_2")
  public String getsignup(Model model) {
    model.addAttribute("address", new Address());
    return "signup_2";
  }

  @PostMapping("")
  public String signUser(Address address, final BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("signup_2", address);
      return "signup_2";
    }

    String switherland = "switherland";

    String Count = address.getCountry();
    if(Count == switherland){
      return "signup_2";
    }

    addRepo.save(address);

    return "login";

  }

}
