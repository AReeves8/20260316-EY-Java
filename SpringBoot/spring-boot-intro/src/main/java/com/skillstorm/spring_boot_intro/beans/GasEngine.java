package com.skillstorm.spring_boot_intro.beans;

public class GasEngine implements Engine {

    @Override
    public void run() {
        System.out.println("GAS GOES VROOOOOOOMMMM!");
    }

}
