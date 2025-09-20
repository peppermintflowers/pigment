package com.pigment.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
/** ADDRESS
 * This class is used to store the Address details of the User.
 */
@AllArgsConstructor
@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
