package group1.se2project.controller;

import group1.se2project.GlobalData.GlobalData;
import group1.se2project.model.Product;
import group1.se2project.repository.ProductRepository;
import group1.se2project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable(value = "id") Long id,Model model ) {
        GlobalData.cart.add(productRepository.getById(id));
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "successalert";
    }

    @GetMapping(value = "/cart")
    public String cart(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @RequestMapping(value = "deleteCart/{id}")
    public String deleteFromCart(@PathVariable (value = "id") Long id ){
        GlobalData.cart.remove(productRepository.getById(id));

        return "shop";
    }

    @RequestMapping(value = "/checkout")
    public String checkout (Model model) {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return  "checkout";
    }
}
