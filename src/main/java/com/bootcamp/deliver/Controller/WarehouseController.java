package com.bootcamp.deliver.Controller;

import java.util.List;

import javax.validation.Valid;

import com.bootcamp.deliver.Model.warehouse;
import com.bootcamp.deliver.Repository.WareHouseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class WarehouseController {
  
    @Autowired
    private WareHouseRepository wareRepo;

  // @GetMapping("/addwarehouse") 
  // public String addwarehouse (Model model) {
  //   model.addAttribute("warehouse",new warehouse());
  //   return "addwarehouse";
  // } 

  @GetMapping("/addwarehouse")
  public String addwarehouse(Model model){
    List<warehouse> ware =wareRepo.findAll();
    if (ware.isEmpty()){
      model.addAttribute("warehouse",new warehouse());
     return "addwarehouse";
    } else{
      String str = "you have already added a ware house";
      model.addAttribute("str",str);
      return "redirect:/adminproduct_list";
    }
  }

  @PostMapping("/savewarehouse")
  public String savewarehouse(
      @Valid warehouse warehouse,
      Errors errors,
      SessionStatus status) {

    if (errors.hasErrors()) {
          return "addwarehouse";
      }
      wareRepo.save(warehouse);
      status.setComplete();

      return "redirect:/adminproduct_list";
  }

}
