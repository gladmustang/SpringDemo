package com.example.gateway.object;


import lombok.Getter;
import lombok.Setter;

public class Value {
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String quote;
    @Override
    public String toString() {
        return "Value{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                '}';
    }
}
