package entity.order;

import entity.media.Media;

public class OrderMedia {
    
    private Media media;
    private int price;
    private int quantity;
    private String shippingTime;

    public OrderMedia(Media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
        this.shippingTime = null;
    }
    
    @Override
    public String toString() {
        return "{" +
            "  media='" + media + "'" +
            ", quantity='" + quantity + "'" +
            ", price='" + price + "'" +
            ", shippingTime='" + shippingTime + "'" +
            "}";
    }
    
    public Media getMedia() {
        return this.media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

}
