/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

import java.io.Serializable;

/**
 * Class that represents a single listing, or entry created in a notice board (Tab),
 * it inherites form Serializable, so that list of its instances can be saved to a file easly 
 * @author JK
 * @version f2.0
 */
public class Listing implements Serializable {
    /**
     * listing title, displayed as its identifier
     */
    private String title;
    /**
     * listing author's asking price
     */
    private float price;
    /**
     * longer, detailed listing descryption
     */
    private String desc;
    /**
     * true if author is willing to negotiate the price
     */
    private boolean negotiable;
    /**
     * username of listing author
     */
    private String authorUname;
    /**
     * contact info provided by listing author
     */
    private String authorContact;
    
    /**
     * enum that reprezents avilability of listing
     * AVILABLE - just posted, nobody claimed buying yet
     * CLAIMED - at least 1 person wants to buy this item
     * CLOSED - sold, left in db for archive purposes
     */
    private enum Status {
        AVILABLE, CLAIMED, CLOSED
    } 
    
    private Status status;
    
    /**
     * 6-argument constructor, used when creating a new Listing,
     * all newly created listings are AVILABLE
     * @param title listing title
     * @param price asked price
     * @param desc detailed, longer listing descryption
     * @param negotiable if true, it means that price can be negotiated
     * @param authorUname name of listing author
     * @param authorContact contact info provided by author
     */
    public Listing(String title, float price, String desc, boolean negotiable, String authorUname, String authorContact) {
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.negotiable = negotiable;
        this.authorUname = authorUname;
        this.authorContact = authorContact;
        this.status = Status.AVILABLE;
    }
    
    /**
     * Generates shipping code based on listing data and user shipping address
     * @param username current user name
     * @param address shipping address
     * @return generated code in format: "TAB"
     * @throws ListingAccessException thrown if user tries to generate code for someboby elses listing
     */
    public String generateCode(String username, String address)
    throws ListingAccessException {
        if(!this.authorUname.equals(username)) {
            throw new ListingAccessException("You can only generate codes of your own listings!");
        } else {
            if(address.replace(" ", "").length() < 5 || address.length() > 48) {
                return "INVALID";
            }
            address = address.replace(" ", "-");
            return "TAB" + this.title.charAt(0) + this.authorUname.charAt(0) + this.price +"-" + address;
        }
    }
    
    public void closeListing() {
        this.status = Status.CLOSED;
    }

    public String getAuthorUname() {
        return authorUname;
    }

    public String getAuthorContact() {
        return authorContact;
    }

    public void setAuthorUname(String authorUname) {
        this.authorUname = authorUname;
    }

    public void setAuthorContact(String authorContact) {
        this.authorContact = authorContact;
    }
    
    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNegotiable(boolean negotiable) {
        this.negotiable = negotiable;
    }
    
    public boolean isAvilable() {
        return this.status.equals(Status.AVILABLE);
    }
    
    public boolean isClaimed() {
        return this.status.equals(Status.CLAIMED);
    }
}
