package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Configs;

public class Order {
    
    private int shippingFees;
    private List<OrderMedia> listOrderMedia;
    private HashMap<String, String> deliveryInfo;

    public Order(){
        this.listOrderMedia = new ArrayList<>();
    }

    public Order(List<OrderMedia> listOrderMedia) {
        this.listOrderMedia = listOrderMedia;
    }

    public void addOrderMedia(OrderMedia om){
        this.listOrderMedia.add(om);
    }

    public void removeOrderMedia(OrderMedia om){
        this.listOrderMedia.remove(om);
    }

    public List<OrderMedia> getListOrderMedia() {
        return this.listOrderMedia;
    }

    public void setlstOrderMedia(List<OrderMedia> listOrderMedia) {
        this.listOrderMedia = listOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    public int getShippingFees() {
        return shippingFees;
    }

    public HashMap<String, String> getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap<String, String> deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : listOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (Configs.PERCENT_VAT/100)*amount);
    }

}
