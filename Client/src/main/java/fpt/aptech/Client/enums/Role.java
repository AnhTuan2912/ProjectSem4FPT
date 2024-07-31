/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.enums;

/**
 *
 * @author Admin
 */
public enum Role {
    ADMIN(1),
    CUSTOMER(2),
    STAFF(3);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
