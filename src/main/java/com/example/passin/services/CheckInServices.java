package com.example.passin.services;

import com.example.passin.domain.attendee.Attendee;
import com.example.passin.domain.checkin.CheckIn;
import com.example.passin.domain.checkin.exception.CheckInAlreadyExistsException;
import com.example.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInServices {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.veruryCheckInExists(attendee.getId());
        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkInRepository.save(newCheckIn);
    }

    private void veruryCheckInExists(String attendeeId) {
        Optional<CheckIn> isCheckIn = getCheckIn(attendeeId);
        if(isCheckIn.isPresent()) throw  new CheckInAlreadyExistsException("Attendee already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
