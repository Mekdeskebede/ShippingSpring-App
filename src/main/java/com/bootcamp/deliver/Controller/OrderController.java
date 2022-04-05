package com.bootcamp.deliver.Controller;

// import java.util.ArrayList;
import java.util.List;
// import java.util.Optional;

import javax.validation.Valid;

import com.bootcamp.deliver.Model.DeliveryAddress;
// import com.bootcamp.deliver.Model.GeoLocation;
import com.bootcamp.deliver.Model.Order;
import com.bootcamp.deliver.Model.Product;
import com.bootcamp.deliver.Model.Cart_Item;
import com.bootcamp.deliver.Model.Shipping_Cart;
import com.bootcamp.deliver.Model.ShippingProvider;
import com.bootcamp.deliver.Model.User;
import com.bootcamp.deliver.Model.warehouse;
import com.bootcamp.deliver.Repository.DeliveryAddressRepository;
import com.bootcamp.deliver.Repository.OrderRepository;
import com.bootcamp.deliver.Repository.Cart_ItemRepository;
// import com.bootcamp.deliver.Repository.OrderDetailRepository;
import com.bootcamp.deliver.Repository.Shipping_CartRepository;
import com.bootcamp.deliver.Repository.ProductRepository;
import com.bootcamp.deliver.Repository.ShippingProvidersRepository;
import com.bootcamp.deliver.Repository.UserRepository;
import com.bootcamp.deliver.Repository.WareHouseRepository;
import com.bootcamp.deliver.Service.GeoLocationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class OrderController {

  @Autowired
  private Shipping_CartRepository prodOrderRepo;

  @Autowired
  private Cart_ItemRepository packRepo;

  @Autowired
  private DeliveryAddressRepository deliaddrepo;

  // @Autowired
  // private ProductRepository productRepo;

  @Autowired
  private ShippingProvidersRepository shipRepo;

  @Autowired
  private UserRepository repo;

  @Autowired
  private OrderRepository orderrepo;

  @Autowired
  private WareHouseRepository warerepo;

  @Autowired
  private GeoLocationServiceImpl geolocation;

  @GetMapping("/ordercart")
  public String orderForm(Model model) {
    model.addAttribute("deliveryaddress", new DeliveryAddress());
    return "orderProdPage";
  }

  @PostMapping("/finalorder")
  public String finalOrder(
    @Valid DeliveryAddress deliveryaddress,
    Model model,
      Errors errors, SessionStatus status) {
    if (errors.hasErrors()) {
      System.out.print("this is a fucking error!!!!");
    }
    deliaddrepo.save(deliveryaddress);
    status.setComplete();

// Find the warehouse address
Long id = (long) 1;
warehouse warehouse = warerepo.getById(id);

//  get warehouse location

// String addressOrigin = warehouse.toString();
// Optional<GeoLocation> coordinatesOrigin = geolocation.computeGeoLocation(addressOrigin);
 // get the delivery location

// String addressDelivery = deliveryaddress.toString();
// Optional<GeoLocation> coordinatesDelivery = geolocation.computeGeoLocation(addressDelivery);

// calculate distance

// GeoLocation location = coordinatesDelivery.get();
// Double distance = location.getLatitude();

//  get all the items from the cart of a ceratin user

Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
String currentPrincipalName = authentication.getName();
User euser = repo.findByEmail(currentPrincipalName);
Long userid = euser.getId();
// List<Package> cart = packRepo.findPackageById(euser.getId());
List<Cart_Item> cart = packRepo.findAll();

//  get all the shipping providers

List<ShippingProvider> shippingprovider = shipRepo.findAll();

//  Calculate the overall pallet needed based on the number of product and maximum product given
Double AllPallet = 0.0;

for(Cart_Item ct: cart){

if(euser == ct.getUser() ){
    Double numofprod = (double) ct.getNumofprod();
    Product product = ct.getProduct();
    Double minPallet = product.getMinNumberofpallet();
    Double maxNumProd = product.getMaxNumberofproduct();

    int ratio = (int) (numofprod / maxNumProd);
    Double remainder = (numofprod - ratio) / maxNumProd;

    Double NetPallet =  (ratio + remainder) * minPallet;

    AllPallet +=  NetPallet;
}
      }
Double distance = 10000.0;
Double price = 0.0;

// initiate the price variable in the shipping provider loop
for (ShippingProvider sp: shippingprovider){
        Double discount = sp.getDiscountFactor();
        Double initialPrice = sp.getInitialPrice();
        Double RatePerKilo = sp.getRatePerKilo();
        Double initialDistance = sp.getInitialDistance();
        Double RatePerPallet = sp.getRatePerPallet();
        // String name = sp.getName();

         if (distance < initialDistance){
              price = initialPrice + (AllPallet*RatePerPallet);     
         }else{
           price = initialPrice + (AllPallet * RatePerPallet) + (distance * RatePerKilo);

         }
        Shipping_Cart order = new Shipping_Cart();

        order.setUser(euser);
        order.setShippingprovider(sp);
        order.setPrice(price);

        prodOrderRepo.save(order);
      }
    return "redirect:/price";
  } 

  @GetMapping("/price")
  public String Pricedisplay (Model model) {
    List<Shipping_Cart> productorder = prodOrderRepo.findAll();
    List<ShippingProvider> shippingprovider = shipRepo.findAll();

    Order ordering = new Order();
    model.addAttribute("ordering", ordering);
    model.addAttribute("productorder", productorder);
    
    model.addAttribute("shippingprovider", shippingprovider);
    return "pricedisplay";
  }

  @PostMapping("/orderit/{id}")
  public String Order(
    @Valid Order ordering,
    @PathVariable("id") long id,
    Model model
    ) {
    Shipping_Cart prodorder = prodOrderRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User euser = repo.findByEmail(currentPrincipalName);
    Long userid = euser.getId();

      Double price = prodorder.getPrice();
      String name = prodorder.getShippingprovider().getName();
      ShippingProvider sp = prodorder.getShippingprovider();

      ordering.setPrice(price);
      ordering.setShipName(name);
      ordering.setUser(euser);
      ordering.setShippingprovider(sp);

      orderrepo.save(ordering);
      List <Cart_Item> pack = packRepo.findAll();

      for(Cart_Item pk:pack){

        if (pk.getUser() == euser){
            packRepo.delete(pk);
        }
      }
      prodOrderRepo.deleteAll();

    return "redirect:/myorders";
  }

  @GetMapping("/allorders")
  public String allorders(Model model) {
    List<User> user = repo.findAll();
    List<Order> order = orderrepo.findAll();
    model.addAttribute("order", order);
     model.addAttribute("user", user);
    return "allorder";
  }
  
  @GetMapping("/myorders")
  public String userorders(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User euser = repo.findByEmail(currentPrincipalName);
    List<Order> order = orderrepo.findAll();
    model.addAttribute("order", order);
    model.addAttribute("euser", euser);
    return "userorderlist";
  }
}
