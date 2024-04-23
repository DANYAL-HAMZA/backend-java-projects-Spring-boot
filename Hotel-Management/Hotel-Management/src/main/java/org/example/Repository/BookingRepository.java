package org.example.Repository;


import org.example.Model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookedRoom,Long> {
    List<BookedRoom> findByRoomId(long roomId);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);
}
