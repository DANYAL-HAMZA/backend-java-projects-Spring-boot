package org.example.Service;

import org.example.Model.BookedRoom;

import java.util.List;


public interface BookingService {
    List<BookedRoom> getAllBookingsByRoomId(long roomId);

    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();
}
