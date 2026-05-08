package org.example.lab5_20223291.controller;


import jakarta.validation.Valid;
import org.example.lab5_20223291.entity.Customer;
import org.example.lab5_20223291.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customer")

public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping(value = {"", "/"})
    public String listaClientes(Model model) {
        model.addAttribute("listaCustomers", clienteRepository.findAll());
        return "customer/list";
    }

    @GetMapping("/new")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new Customer());
        model.addAttribute("accion", "Crear");
        return "customer/form";
    }

    @GetMapping("/edit")
    public String editarCliente(@RequestParam("id") int id, Model model) {

        Customer customer = clienteRepository.findById(id).orElse(null);
        if (customer == null) return "redirect:/customer";
        model.addAttribute("customer", customer);
        model.addAttribute("accion", "Editar");
        return "customer/form";
    }

    @PostMapping("/save")
    public String guardarCliente(@Valid @ModelAttribute Customer customer,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes attr) {
        if (result.hasErrors()) {
            model.addAttribute("accion", customer.getId() == null ? "Crear" : "Editar");
            return "customer/form";
        }
        clienteRepository.save(customer);
        attr.addFlashAttribute("mensaje", "Cliente guardado correctamente");
        return "redirect:/customer";
    }

    @GetMapping("/delete")
    public String eliminarCliente(@RequestParam("id") int id, RedirectAttributes attr) {
        clienteRepository.deleteById(id);
        attr.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        return "redirect:/customer";
    }








}
