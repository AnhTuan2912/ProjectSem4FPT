/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.Client.enums;

/**
 *
 * @author Admin
 */
public enum Status {
    ACTIVE(1),
    DEACTIVE(2),
    BLOCK(3),
    PENDING(4);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
