package br.com.raphaelneves.models;

import java.util.UUID;

/**
 * Created by raphaeloneves on 09/06/2017.
 */
public class Role {

    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        return ((Role)o).getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public Role(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
