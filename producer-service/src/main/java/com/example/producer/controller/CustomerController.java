package com.example.producer.controller;
import com.example.producer.dto.Customer;
import com.example.producer.service.CustomerProducer;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/customers")
public class CustomerController{
 private final CustomerProducer producer;
 public CustomerController(CustomerProducer producer){
  this.producer=producer;
 }
 @PostMapping
 public String publish(@RequestBody Customer customer){
  producer.publish(customer);
  return "Customer Published";
 }
}
