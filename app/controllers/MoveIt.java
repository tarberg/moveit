package controllers;

import models.Customer;
import models.Order;
import models.Parcel;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class MoveIt extends Controller {

    public static class ParcelForm {
        public String from;
        public String to;
        public String size;
        public boolean express;
    }

    static Form<ParcelForm> parcelForm = form(ParcelForm.class);

    public static Result newOrder() {
        return ok(views.html.newOrder.render(Customer.getCustomer().currentOrder, parcelForm));
    }

    public static Result submit() {
        Customer customer = Customer.getCustomer();
        if (customer.currentOrder == null
                || customer.currentOrder.parcels == null
                || customer.currentOrder.parcels.isEmpty()) {
            flash("error", "An order must have at least one package");
            return badRequest(views.html.newOrder.render(customer.currentOrder, parcelForm));
        }
        customer.submitOrder();
        return redirect(routes.MoveIt.orders());
    }

    public static Result orders() {
        return ok(views.html.orders.render(Order.all()));
    }

    public static Result order(Integer orderId) {
        Order order = models.Order.find.byId(orderId);

        // Must do this to be able to display region names. Eager fetching not working?
        for (Parcel parcel : order.parcels) {
            parcel.fromRegion.refresh();
            parcel.toRegion.refresh();
        }

        return ok(views.html.order.render(order));
    }

    public static Result cancel() {
        Logger.debug("Deleting current order");
        Customer.getCustomer().currentOrder = null;
        return redirect(routes.Application.index());
    }

    public static Result addPackage() {
        Customer customer = Customer.getCustomer();
        if (customer.currentOrder == null) {
            customer.currentOrder = new Order(customer);
        }

        Form<ParcelForm> filledForm = parcelForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(views.html.newOrder.render(customer.currentOrder, parcelForm));
        } else {
            ParcelForm parcelForm = filledForm.get();
            Parcel parcel = new Parcel(parcelForm.from, parcelForm.to, parcelForm.size, parcelForm.express);
            parcel.order = customer.currentOrder;
            customer.currentOrder.parcels.add(parcel);
            return redirect(routes.MoveIt.newOrder());
        }
    }

}