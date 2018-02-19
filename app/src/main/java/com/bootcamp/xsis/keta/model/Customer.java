package com.bootcamp.xsis.keta.model;

import io.realm.RealmObject;

/**
 * Created by ASUS Notebook on 19/02/2018.
 */

public class Customer extends RealmObject {


    private int customer_id;
    private String customer_name;
    private String customer_address;
    private String customer_country;
    private String customer_province;
    private String customer_city;
    private int customer_post_code;
    private String customer_email;
    private String customer_username;
    private String customer_password;
    private String customer_gander;
    private String customer_phone;
    private String customer_bank_account;
    private String customer_create_date;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_country() {
        return customer_country;
    }

    public void setCustomer_country(String customer_country) {
        this.customer_country = customer_country;
    }

    public String getCustomer_province() {
        return customer_province;
    }

    public void setCustomer_province(String customer_province) {
        this.customer_province = customer_province;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public int getCustomer_post_code() {
        return customer_post_code;
    }

    public void setCustomer_post_code(int customer_post_code) {
        this.customer_post_code = customer_post_code;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_username() {
        return customer_username;
    }

    public void setCustomer_username(String customer_username) {
        this.customer_username = customer_username;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_gander() {
        return customer_gander;
    }

    public void setCustomer_gander(String customer_gander) {
        this.customer_gander = customer_gander;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_bank_account() {
        return customer_bank_account;
    }

    public void setCustomer_bank_account(String customer_bank_account) {
        this.customer_bank_account = customer_bank_account;
    }

    public String getCustomer_create_date() {
        return customer_create_date;
    }

    public void setCustomer_create_date(String customer_create_date) {
        this.customer_create_date = customer_create_date;
    }
}
