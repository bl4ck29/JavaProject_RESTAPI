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
    private String cre;

    public ClientItems(){}
    public ClientItems(int id, String name, String pattern, String cre){
        this.id = id;
        this.name = name;
        this.pattern = pattern;
        this.cre = cre;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPattern(){return this.pattern;}
    public String getCreatorId(){return this.cre;}
}
