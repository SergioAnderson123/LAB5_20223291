package org.example.lab5_20223291.controller;

import jakarta.validation.Valid;
import org.example.lab5_20223291.entity.Product;
import org.example.lab5_20223291.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping(value = {"", "/"})
    public String listaProductos(Model model) {
        model.addAttribute("listaProductos", productRepository.findAll());
        return "product/list";
    }

    @GetMapping("/new")
    public String nuevoProductoFrm(Model model, @ModelAttribute("product") Product product) {
        return "product/editFrm";
    }

    @PostMapping("/save")
    public String guardarProducto(RedirectAttributes attr, Model model,
                                  @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) { //si no hay errores, se realiza el flujo normal

            if (product.getProductname().equals("gaseosa")) {
                model.addAttribute("msg", "Error al crear producto");
                return "product/editFrm";
            } else {
                if (product.getId() == 0) {
                    attr.addFlashAttribute("msg", "Producto creado exitosamente");
                } else {
                    attr.addFlashAttribute("msg", "Producto actualizado exitosamente");
                }
                productRepository.save(product);
                return "redirect:/product";
            }

        } else { //si hay al menos 1 error

            return "product/editFrm";
        }
    }


    @GetMapping("/delete")
    public String borrarProducto(@RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isPresent()) {
            productRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Producto borrado exitosamente");
        }
        return "redirect:/product";

    }




}