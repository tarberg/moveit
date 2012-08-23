package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "order_table")
public class Order extends Model {

    @Id
    public Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    public Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    public List<Parcel> parcels;

    public Date orderDate;

    public Order() {
    }

    public Order(Customer customer) {
        this.customer = customer;
    }

    public static Finder<Integer, Order> find = new Finder(Integer.class, Order.class);

    public static List<Order> all() {
        return find.all();
    }

    public static List<Order> findByCustomer(Customer customer) {
        return find.where().eq("customer", customer).findList();
    }

}