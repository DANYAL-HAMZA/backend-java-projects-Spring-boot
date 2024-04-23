package org.example.Response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor

public class RoomResponse {
    private long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String photo;
    private boolean isBooked = false;


    private List<BookingResponse> bookings;

    public RoomResponse(long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }
    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, byte[] photo, boolean isBooked,
                        List<BookingResponse>bookings){
        this.id=id;
        this.roomPrice=roomPrice;
        this.roomType=roomType;
        this.bookings=bookings;
        this.isBooked=isBooked;
        this.photo=photo != null ? Base64.encodeBase64String(photo):null;
    }

}
