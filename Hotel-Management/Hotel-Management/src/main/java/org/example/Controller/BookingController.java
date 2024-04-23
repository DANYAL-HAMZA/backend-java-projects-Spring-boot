package org.example.Controller;
import org.example.Model.BookedRoom;

import org.example.Exceptions.InvalidBookingRequestException;
import org.example.Exceptions.ResourceNotFoundException;
import org.example.Model.Room;
import org.example.Response.BookingResponse;
import org.example.Response.RoomResponse;
import org.example.Service.BookingService;
import org.example.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin("http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;
    @GetMapping("/all-bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom room : bookings){
            BookingResponse bookingResponse = getBookingResponse(room);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }
    //the question mark in the method below indicates that we are not returning anything from the method.
    @GetMapping("/confirmationCode/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try {
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }

    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),theRoom.getRoomType(),theRoom.getRoomPrice());
        return new BookingResponse(booking.getBookingId(),booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),booking.getGuestEmail(),
        booking.getNumberOfAdults(),booking.getNumberOfChildren(), booking.getTotalNumberOfGuests(),
                booking.getBookingConfirmationCode(),room);
    }

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBookings(@PathVariable Long roomId,@RequestBody BookedRoom bookingRequest){
        try {
            String confirmationCode = bookingService.saveBooking(roomId,bookingRequest);
            return  ResponseEntity.ok("Room booked successfully ! Your confirmation " +
                    "code is :"+confirmationCode);
        }catch(InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
    }

}
