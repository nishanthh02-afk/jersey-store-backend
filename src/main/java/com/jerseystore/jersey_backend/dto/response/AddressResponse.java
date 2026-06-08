package com.jerseystore.jersey_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {

    private Long id;
    private Long userId;
    private String doorNumber;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private Boolean isDefault;

}
