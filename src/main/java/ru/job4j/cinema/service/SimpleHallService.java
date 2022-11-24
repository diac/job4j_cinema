package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;

import java.util.List;

@Service
@ThreadSafe
public  class SimpleHallService implements HallService {

    public Hall getHall() {
        return new Hall(List.of(5, 10, 5, 12, 12));
    }
}