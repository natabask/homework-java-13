package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Ticket;
import ru.netology.repository.TicketRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TicketManagerTest {
    private TicketRepository repo = new TicketRepository();
    private TicketManager manager = new TicketManager(repo);

    private Ticket aeroflot = new Ticket(1, 55_000, "LED", "BKK", 480);
    private Ticket emirates = new Ticket(2, 67_000, "LED", "BKK", 520);
    private Ticket qatar = new Ticket(3, 61_300, "LED", "BKK", 364);
    private Ticket pobeda = new Ticket(4, 12_100, "LED", "OGZ", 80);
    private Ticket aeroflotReturn = new Ticket(5, 53_000, "BKK", "LED", 480);
    private Ticket uzbekistan = new Ticket(6, 55_000, "LED", "BKK", 500);
    private Ticket wizzAir = new Ticket(7, 15_000, "LED", "BUD", 190);

    @Test
    public void shouldReturnNullIfNoSuitableFlights() {
        manager.add(pobeda); // Летим во Владикавказ
        manager.add(wizzAir); // Летим в Будапешт

        Ticket[] expected = new Ticket[]{};
        Ticket[] actual = manager.findAll("LED", "BKK");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotSearchReturnFlights() {
        manager.add(aeroflot); // Летим во Владикавказ
        manager.add(aeroflotReturn); // Летим обратно в Питер

        Ticket[] expected = new Ticket[]{aeroflot};
        Ticket[] actual = manager.findAll("LED", "BKK");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFindAndSortAllSuitableFlights() {
        manager.add(aeroflot); // Питер - Бангкок 55 000 RU
        manager.add(emirates); // Питер - Бангкок 67 000 RU
        manager.add(qatar); // Питер - Бангкок 61 300 RU
        manager.add(pobeda);
        manager.add(aeroflotReturn);
        manager.add(wizzAir);

        Ticket[] expected = new Ticket[]{aeroflot, qatar, emirates};
        Ticket[] actual = manager.findAll("LED", "BKK");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldFindAndSortFlightsWithSamePrice() {
        manager.add(aeroflot); // Питер - Бангкок
        manager.add(emirates); // Питер - Бангкок
        manager.add(qatar); // Питер - Бангкок
        manager.add(pobeda);
        manager.add(aeroflotReturn);
        manager.add(wizzAir);
        manager.add(uzbekistan); // Питер - Бангкок 55 000 RU

        Ticket[] expected = new Ticket[]{aeroflot, uzbekistan, qatar, emirates};
        Ticket[] actual = manager.findAll("LED", "BKK");
        assertArrayEquals(expected, actual);
    }
}