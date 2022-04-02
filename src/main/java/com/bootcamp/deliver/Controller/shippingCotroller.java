package com.bootcamp.deliver.Controller;

import com.bootcamp.deliver.Model.ShippingProvider;
import com.bootcamp.deliver.Repository.ShippingProvidersRepository;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@CrossOrigin("http://localhost:8080")
public class shippingCotroller {    
    

    @Autowired
  private ShippingProvidersRepository shiprovs;

  @GetMapping("/addshipping")
  public String listpg(Model model) {
    ShippingProvider shippingprovider = new ShippingProvider();
    model.addAttribute("shippingprovider", shippingprovider );

    return "addShippingProviders";

  }
 
  @PostMapping("/saveShipping")
  public String addShipping(@ModelAttribute("shippingProvider") ShippingProvider shippingProvider) {
    shiprovs.save(shippingProvider);

    return "redirect:/shipping_list";
  }


@GetMapping("/shipping_list")
public String viewproductPage(Model model) {
  List<ShippingProvider> shippingprovider = shiprovs.findAll();
  model.addAttribute("shippingprovider", shippingprovider);
  return "shipping_list";
}


@GetMapping("/shippingprovider/edit/{id}")
public String updatepage(@PathVariable long id, Model model) {
  ShippingProvider shippingprovider = shiprovs.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

  model.addAttribute("shippingprovider", shippingprovider);
  return "edit_shipping";
}

@GetMapping("/deletes/{id}")
public String deleteUser(@PathVariable("id") long id, Model model) {
  ShippingProvider shippingprovider = shiprovs.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
  shiprovs.delete(shippingprovider);
  return "redirect:/shipping_list";
}

@PostMapping("/shippingprovider/{id}")
public String updateShipping( @PathVariable("id") long id,@Valid ShippingProvider shippingprovider,
BindingResult result ,Model model){
  ShippingProvider existshipping = shiprovs.findById(id)
  .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
if (result.hasErrors()) {
shippingprovider.setId(id);
return "edit";
}

shippingprovider.setId(existshipping.getId());
shiprovs.save(shippingprovider);

return "redirect:/shipping_list";

}

}