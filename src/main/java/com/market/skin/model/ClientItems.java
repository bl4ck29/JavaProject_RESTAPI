package com.market.skin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientItems {
    private int id;
    private String name;
    private String pattern;

    public ClientItems(){}
    public ClientItems(int id, String name, String pattern){
        this.id = id;
        this.name = name;
        this.pattern = pattern;
    }

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setPattern(String pattern){this.pattern = pattern;}

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPattern(){return this.pattern;}
}
