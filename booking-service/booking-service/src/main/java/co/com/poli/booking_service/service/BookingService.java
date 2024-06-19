package co.com.poli.booking_service.service;

import co.com.poli.booking_service.persistence.entity.Booking;

import java.util.List;

public interface BookingService {
    void save(Booking booking);
    void delete(Booking booking);
    List<Booking> findAll();
    Booking findById(Long id);
    List<Booking> findByUserId(Long id);
}
