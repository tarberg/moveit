package models;

import controllers.Application;
import play.cache.Cache;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static play.mvc.Controller.session;

@Entity
public class Customer extends Model {

    @Id
    public Integer id;

    @Required
    public String name;

    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, orphanRemoval=true)
    public Set<Order> orders;

    public Order currentOrder;

    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public static Finder<Integer, Customer> find = new Finder(Integer.class, Customer.class);

    public static Customer getCustomer() {
        String userId = session(Application.USER_ID);
        return (Customer) Cache.get(Application.USER_ID + userId);
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", id, name);
    }

    public void submitOrder() {
        currentOrder.orderDate = new Date();
        orders.add(currentOrder);
        currentOrder = null;
        this.save();
    }

}