package controllers;

import models.Customer;
import play.Logger;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public static final String USER_ID = "UserId";

    public static class LoginForm {
        public String email;
        public String password;
    }

    public static Result index() {
        Customer customer = Customer.getCustomer();
        if (customer == null) {
            return redirect(routes.Application.login());
        }
        return ok(views.html.index.render(customer));
    }

    public static Result login() {
        return ok(views.html.login.render(form(LoginForm.class)));
    }

    /**
     * Sets fake user and redirects to index page..
     */
    public static Result authenticate() {
        String userIdString = session(USER_ID);
        Customer customer;
        if (userIdString != null) {
            customer = Customer.find.byId(Integer.parseInt(userIdString));
            if (customer != null) {
                Cache.set(USER_ID + userIdString, customer);
            } else {
                customer = new Customer("Ture Test");
            }
        } else {
            customer = new Customer("Ture Test");
        }
        customer.save();
        userIdString = "" + customer.id;
        session(USER_ID, userIdString);
        Cache.set(USER_ID + userIdString, customer, 3600);
        return redirect(routes.Application.index());
    }

}