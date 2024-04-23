package org.example.Service;

import org.example.Exceptions.InvalidBookingRequestException;
import org.example.Model.BookedRoom;
import org.example.Model.Room;
import org.example.Repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(long roomId) {

        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);

    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check in date must come before the checkout date");
        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if(roomIsAvailable){
            room.addBookings(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else {
            throw new InvalidBookingRequestException("sorry this room is not available for the selected date");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream().noneMatch(existingBooking ->
                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate()) ||
                bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate()) ||
                        (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate()) &&
                        bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())) ||

                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())&&
                bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())) ||

                        (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate()) &&
                                bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))  ||

                                (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate()) &&
                                        bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate())||

                                        (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())&&
                                                bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))

                                ));
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode);
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
}
