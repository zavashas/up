package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/calculator")
    public String calculator() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam("a") double a,
            @RequestParam("b") double b,
            @RequestParam("op") String op,
            Model model
    ) {
        double result = 0;

        switch (op) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = (b != 0) ? a / b : Double.NaN;
        }

        model.addAttribute("a", a);
        model.addAttribute("b", b);
        model.addAttribute("op", op);
        model.addAttribute("result", result);

        return "calc-result";
    }

    @GetMapping("/converter")
    public String converter() {
        return "converter";
    }

    @PostMapping("/convert")
    public String convert(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") double amount,
            Model model
    ) {
        double rate = getRate(from, to);
        double result = amount * rate;

        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("amount", amount);
        model.addAttribute("rate", rate);
        model.addAttribute("result", result);

        return "convert-result"; // шаблон convert-result.html
    }

    private double getRate(String from, String to) {
        if (from.equals(to)) return 1.0;

        if (from.equals("RUB") && to.equals("USD")) return 0.011;
        if (from.equals("USD") && to.equals("RUB")) return 90.0;

        if (from.equals("RUB") && to.equals("EUR")) return 0.01;
        if (from.equals("EUR") && to.equals("RUB")) return 100.0;

        if (from.equals("USD") && to.equals("EUR")) return 0.9;
        if (from.equals("EUR") && to.equals("USD")) return 1.1;

        return 1.0;
    }
}
