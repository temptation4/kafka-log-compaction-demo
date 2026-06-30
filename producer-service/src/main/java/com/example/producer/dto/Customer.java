package com.example.producer.dto;
public class Customer{
 private String customerId;
 private String name;
 private String city;
 public Customer(){}
 public Customer(String customerId,String name,String city){
  this.customerId=customerId;
  this.name=name;
  this.city=city;
 }
 public String getCustomerId(){return customerId;}
 public void setCustomerId(String customerId){this.customerId=customerId;}
 public String getName(){return name;}
 public void setName(String name){this.name=name;}
 public String getCity(){return city;}
 public void setCity(String city){this.city=city;}

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
