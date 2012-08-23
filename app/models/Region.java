package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Region extends Model {

    @Id
    public Integer id;

    @Required
    public String name;

    public Region() {
    }

    public Region(String name) {
        this.name = name;
    }

    public static Finder<Integer, Region> find = new Finder(Integer.class, Region.class);

    public static Region findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    @Override
    public String toString() {
        return name;
    }
}