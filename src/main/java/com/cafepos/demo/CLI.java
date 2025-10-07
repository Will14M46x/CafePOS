package com.cafepos.demo;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;

import java.util.Map;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP"));
    }
}
