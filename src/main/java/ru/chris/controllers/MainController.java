package ru.chris.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chris.services.UserService;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping("/bank")
public class MainController {

    private final UserService service;

    @Autowired
    public MainController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/test")
    public String test() {
        return "ETO TEST !!! ";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> checkData(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    )
    {
        if (service.checkUser(username, password)){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    )
    {
        System.out.println(username + " " + password + " " + email);
        service.saveUser(username, password, email);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping("/baseInfo")
    public List<String> getBaseInfo() {
        return service.getUserInfo();
    }

    @RequestMapping("/tableInfo")
    public List<List<String>> getTableInfo() {
        return service.getDataForTable();
    }

    @RequestMapping("/acc")
    public String createAcc() {
        service.createInfo();
        return "";
    }

    @RequestMapping("/getSomeInfo")
    public List<String> getSomeInfo() {
        return service.getSomeInfo();
    }


    @PostMapping("/payment")
    public ResponseEntity<Object> doPayment(
            @RequestParam("otherNumber") String otherNumber,
            @RequestParam("amount") String amount,
            @RequestParam("password") String password
    )
    {
        System.out.println("otherNumber = " + otherNumber);
        System.out.println("amount = " + amount);
        System.out.println("password = " + password);
        if (service.doPayment(otherNumber, amount, password)){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/donation")
    public ResponseEntity<Object> doDonation(
            @RequestParam("amount") String amount,
            @RequestParam("password") String password
    )
    {
        System.out.println("Donation");
        System.out.println("amount = " + amount);
        System.out.println("password = " + password);
        if (service.doDonation(amount, password)){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> doWithdraw(
            @RequestParam("amount") String amount,
            @RequestParam("password") String password
    )
    {
        System.out.println("Withdraw");
        System.out.println("amount = " + amount);
        System.out.println("password = " + password);
        if (service.doWithdraw(amount, password)){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Object> doDeposit(
            @RequestParam("var") int var,
            @RequestParam("amount") String amount,
            @RequestParam("password") String password
    )
    {
        System.out.println("deposit");
        System.out.println("var = " + var);
        System.out.println("amount = " + amount);
        System.out.println("password = " + password);
        if (service.doDeposit(var, amount, password)){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
