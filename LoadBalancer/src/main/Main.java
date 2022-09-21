package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        LoadBalancer loadBalancer=new LoadBalancer(8000);
        loadBalancer.start();

    }


}
