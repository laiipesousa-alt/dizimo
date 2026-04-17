package com.sen.mainnn.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sen.mainnn.model.Dizimo;
import com.sen.mainnn.model.Gasto;
import com.sen.mainnn.repository.DizimoRepository;
import com.sen.mainnn.repository.GastoRepository;

@Controller
public class DizimoController {

    @Autowired
    private DizimoRepository dizimoRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Dizimo> dizimos = dizimoRepository.findAll();
        List<Gasto> gastos = gastoRepository.findAll();
        double totalDizimos = dizimos.stream()
                .map(Dizimo::getValor)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
        double totalGastos = gastos.stream()
                .map(Gasto::getValor)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
        double saldo = totalDizimos - totalGastos;
        model.addAttribute("totalDizimos", totalDizimos);
        model.addAttribute("totalGastos", totalGastos);
        model.addAttribute("saldo", saldo);
        model.addAttribute("quantidadeDizimos", dizimos.size());
        return "index";
    }

    @GetMapping("/dizimos")
    public String dizimos(Model model, @RequestParam(required = false) String busca) {
        List<Dizimo> dizimos;
        if (busca != null && !busca.isEmpty()) {
            dizimos = dizimoRepository.findByNomeMembroContainingIgnoreCase(busca);
        } else {
            dizimos = dizimoRepository.findAll();
        }
        model.addAttribute("dizimos", dizimos);
        model.addAttribute("novoDizimo", new Dizimo());
        return "dizimos";
    }

    @PostMapping("/dizimos/salvar")
    public String salvar(@ModelAttribute Dizimo dizimo) {
        dizimoRepository.save(dizimo);
        return "redirect:/dizimos";
    }

    @GetMapping("/dizimos/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        dizimoRepository.deleteById(id);
        return "redirect:/dizimos";
    }

    @GetMapping("/gastos")
    public String gastos(Model model) {
        model.addAttribute("gastos", gastoRepository.findAll());
        model.addAttribute("novoGasto", new Gasto());
        return "gastos";
    }

    @PostMapping("/gastos/salvar")
    public String salvarGasto(@ModelAttribute Gasto gasto) {
        gastoRepository.save(gasto);
        return "redirect:/gastos";
    }

    @GetMapping("/gastos/deletar/{id}")
    public String deletarGasto(@PathVariable Long id) {
        gastoRepository.deleteById(id);
        return "redirect:/gastos";
    }


@GetMapping("/dizimos/editar/{id}")
public String editarDizimo(@PathVariable Long id, Model model) {
    Dizimo dizimo = dizimoRepository.findById(id).orElseThrow();
    model.addAttribute("novoDizimo", dizimo);
    model.addAttribute("dizimos", dizimoRepository.findAll());
    return "dizimos";
}

@GetMapping("/gastos/editar/{id}")
public String editarGasto(@PathVariable Long id, Model model) {
    Gasto gasto = gastoRepository.findById(id).orElseThrow();
    model.addAttribute("novoGasto", gasto);
    model.addAttribute("gastos", gastoRepository.findAll());
    return "gastos";
}

@GetMapping("/footer")
public String footer() {
	
	return "footer";
}



}
