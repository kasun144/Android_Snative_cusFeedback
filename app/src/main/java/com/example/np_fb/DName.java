package com.example.np_fb;


public class DName {
    private String name;
    private String contactno;
    private String email;
    private int status;

    public DName(String name, String email, String contactno, int status) {
        this.name = name;
        this.email = email;
        this.contactno = contactno;
        this.status = status;
    }

    public String getName() {
        return name;
    }


    public String getContactno() {
        return contactno;
    }

    public String getEmail() {
        return email;
    }


    public int getStatus() {
        return status;
    }
}
