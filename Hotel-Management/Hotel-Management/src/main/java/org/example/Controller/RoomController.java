package org.example.Controller;

import org.example.Exceptions.PhotoRetrievalException;
import org.example.Exceptions.ResourceNotFoundException;
import org.example.Model.BookedRoom;
import org.example.Model.Room;
import org.example.Response.BookingResponse;
import org.example.Response.RoomResponse;
import org.example.Service.BookingService;
import org.example.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class RoomController {

    private final BookingService bookingService;

    private final RoomService roomService;

    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addRoom(@RequestParam("photo") MultipartFile photo,
                                                @RequestParam("roomType") String roomType,
                                                @RequestParam("roomPrice") BigDecimal roomPrice)
            throws SQLException, IOException {
        Room savedRoom = roomService.addNewRoom(photo,roomType,roomPrice);
        RoomResponse roomResponse = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(),
                savedRoom.getRoomPrice());
        return ResponseEntity.ok(roomResponse);
    }
    @GetMapping("/room/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();

    }
    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : rooms){
            byte[] photoBytes = roomService.getRoomPhotoById(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(base64Photo);
                roomResponses.add(roomResponse);
            }

        }
        return ResponseEntity.ok(roomResponses);
    }
    @DeleteMapping("/delete/room/{roomId}")
    private ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestParam(required = false) String roomType,
                                                   @RequestParam(required = false) BigDecimal roomPrice,
                                                   @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        byte[] photoByte = photo != null && !photo.isEmpty()?
                photo.getBytes() : roomService.getRoomPhotoById(roomId);
        Blob photoBlob = photoByte!= null && photoByte.length > 0 ? new SerialBlob(photoByte) : null;
        Room theRoom = roomService.updateRoom(roomId,roomType,roomPrice,photoByte);
        theRoom.setPhoto(photoBlob);
        RoomResponse roomResponse = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomResponse);


    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId){
        Optional<Room> theRoom = roomService.gRoomById(roomId);
        return theRoom.map(room ->{
            RoomResponse roomResponse = null;
            try {
                roomResponse = getRoomResponse(room);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok(Optional.of(roomResponse));
        }).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
    }
@GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRoom(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {
        List<Room> availableRooms = roomService.getAvailableRooms(checkInDate,checkOutDate,roomType);
        List<RoomResponse> roomResponses = new ArrayList<>();
        for(Room room : availableRooms) {
            byte[] photoBytes = roomService.getRoomPhotoById(room.getId());
            if(photoBytes != null & photoBytes.length > 0) {
                String photoBase64 = Base64.encodeBase64String(photoBytes);
                RoomResponse roomResponse = getRoomResponse(room);
                roomResponse.setPhoto(photoBase64);
                roomResponses.add(roomResponse);
            }
        }

        if(roomResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(roomResponses);
        }

    }


    private RoomResponse getRoomResponse(Room room) throws SQLException {
        List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
        List<BookingResponse>bookingInfo = bookings.stream()
                .map(booking -> new BookingResponse(booking.getBookingId(),booking.getCheckInDate(),
                        booking.getCheckOutDate(),booking.getBookingConfirmationCode())).toList();
        byte[] photoByte = null;
        Blob photoBlob = room.getPhoto();
        if(photoBlob != null){
            try {
                photoByte = photoBlob.getBytes(1, (int)photoBlob.length());
            }catch (SQLException e){
                throw new PhotoRetrievalException("Error retrieving photo");

            }
        }
        return new RoomResponse(room.getId(),room.getRoomType(),room.getRoomPrice(),photoByte,room.isBooked(),
                bookingInfo);
    }
    private List<BookedRoom> getAllBookingsByRoomId(long roomId) {
        return bookingService.getAllBookingsByRoomId(roomId);
    }
}
