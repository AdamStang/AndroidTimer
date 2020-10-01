package com.example.timer_skuska;

public class ExampleItem {
    private String name;
    private String work;
    private String rest;
    private String rounds;

    public ExampleItem(String nameA, String workA, String restA, String roundsA){
        name = nameA;
        work = workA;
        rest = restA;
        rounds = roundsA;
    }

    public String getName(){
        return name;
    }

    public String getWork(){
        return work;
    }

    public String getRest(){
        return rest;
    }

    public String getRounds(){
        return rounds;
    }
}
