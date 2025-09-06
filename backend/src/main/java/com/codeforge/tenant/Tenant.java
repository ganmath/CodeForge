package com.codeforge.tenant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tenants")
public class Tenant {
    @Id
    private String id;
    private String name;

    public Tenant() {}
    public Tenant(String id, String name) { this.id=id; this.name=name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}