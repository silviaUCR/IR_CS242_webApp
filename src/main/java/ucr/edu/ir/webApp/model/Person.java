package ucr.edu.ir.webApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Person {
    private final UUID id;
    private final String name;
    //private final String type;

    public Person(@JsonProperty("id") UUID id,
                  @JsonProperty("name") String name)
    {
        this.id = id;
        this.name = name;
        //this.type = type;
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}
