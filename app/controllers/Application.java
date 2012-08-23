package controllers;

import models.Customer;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public static final String USER_ID = "UserId";

    public static final int ONE_HOUR = 3600;

    public static final Customer TURE_TEST = new Customer("Ture Test");

    public static Result index() {
        Customer customer = Customer.getCustomer();
        if (customer == null) {
            return redirect(routes.Application.login());
        }
        return ok(views.html.index.render(customer));
    }

    public static Result login() {
        return ok(views.html.login.render());
    }

    /**
     * Sets fake user and redirects to index page..
     */
    public static Result authenticate() {
        String userId = session(USER_ID);
        Customer customer;
        if (userId != null) {
            customer = Customer.find.byId(Integer.parseInt(userId));
            if (customer != null) {
                cache(userId, customer);
            } else {
                customer = TURE_TEST;
            }
        } else {
            customer = TURE_TEST;
        }
        customer.save();
        userId = Integer.toString(customer.id);
        session(USER_ID, userId);
        cache(userId, customer);
        return redirect(routes.Application.index());
    }

    private static void cache(String userId, Customer customer) {
        Cache.set(USER_ID + userId, customer, ONE_HOUR);
    }

}