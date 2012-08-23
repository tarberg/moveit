package models;

import play.Logger;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Parcel extends Model {

    @Id
    public Integer tsiKod;

    @ManyToOne(fetch = FetchType.EAGER)
    public Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    public Region fromRegion;

    @ManyToOne(fetch = FetchType.EAGER)
    public Region toRegion;

    public String size;

    public Boolean express = false;

    public Parcel() {
    }

    public static Finder<Integer, Parcel> find = new Finder(Integer.class, Parcel.class);

    public Parcel(String from, String to, String size, boolean express) {
        Region fromRegion = Region.findByName(from);
        if (fromRegion == null) {
            fromRegion = new Region(from);
            fromRegion.save();
        }
        Region toRegion = Region.findByName(to);
        if (toRegion == null) {
            toRegion = new Region(to);
            toRegion.save();
        }
        this.fromRegion = fromRegion;
        this.toRegion = toRegion;
        this.size = size;
        this.express = express;
    }

    public static List<Parcel> all() {
        return find.all();
    }

    @Override
    public String toString() {
        return String.format(
                "Parcel: [tsiKod = %s, from = %s, to = %s, size = %s, type = %s]",
                tsiKod, fromRegion, toRegion, size, express);
    }

}