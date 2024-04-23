package org.example.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BookedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @Column(name = "check_in")
    private LocalDate checkInDate;
    @Column(name = "check_out")

    private LocalDate checkOutDate;
    @Column(name = "name")

    private String guestFullName;
    @Column(name = "email")

    private String guestEmail;
    @Column(name = "adults")

    private int numberOfAdults;
    @Column(name = "children")

    private int numberOfChildren;
    @Column(name = "total_guests")

    private int totalNumberOfGuests;
    @Column(name = "confirmation_code")

    private String bookingConfirmationCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")

    private Room room;


    public void calculateTotalNumberOfGuests() {
        this.totalNumberOfGuests = this.numberOfAdults + this.numberOfChildren;

    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        calculateTotalNumberOfGuests();
    }
    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        calculateTotalNumberOfGuests();
    }


    public void setConfirmationCode(String bookingConfirmationCode) {
this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
