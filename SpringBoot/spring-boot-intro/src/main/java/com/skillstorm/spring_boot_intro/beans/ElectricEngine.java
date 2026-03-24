package com.skillstorm.spring_boot_intro.beans;

public class ElectricEngine implements Engine {

    @Override
    public void run() {
        System.out.println("Electric goes ........");
    }
}
