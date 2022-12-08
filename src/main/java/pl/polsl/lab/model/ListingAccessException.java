/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.model;

/**
 * Exception that may occur when somebody tries to delete someone elses listing
 * @author JK
 * @version f1.0
 */
public class ListingAccessException extends Exception {
    /**
     * 1-arg constructor
     * @param msg error message to be displayed
     */
    public ListingAccessException(String msg) {
        super(msg);
    }
}
