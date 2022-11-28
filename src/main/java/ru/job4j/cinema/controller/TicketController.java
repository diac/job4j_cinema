package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@ThreadSafe
public final class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, SessionService sessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
        this.hallService = hallService;
    }

    @GetMapping("/")
    public String selectSessionPage(Model model) {
        List<Session> sessions = sessionService.findAll();
        model.addAttribute("sessions", sessions);
        return "tickets/selectSession";
    }

    @PostMapping("/")
    public String selectSession(@ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute(
                "ticket",
                new Ticket(
                        0,
                        ticket.getSessionId(),
                        0,
                        0,
                        ticket.getUserId()
                )
        );
        return "redirect:/tickets/selectPlace";
    }

    @GetMapping("/tickets/selectPlace")
    public String selectPlacePage(Model model, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        Ticket ticket = (Ticket) httpSession.getAttribute("ticket");
        Hall hall = hallService.getHall();
        model.addAttribute("ticket", ticket);
        model.addAttribute("hall", hall);
        model.addAttribute("placesHelper", ticketService.placesHelper(ticket.getSessionId()));
        return "tickets/selectPlace";
    }

    @PostMapping("/tickets/selectPlace")
    public String selectPlace(@ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("ticket", ticket);
        return "redirect:/tickets/review";
    }

    @GetMapping("/tickets/review")
    public String reviewPage(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try {
            Ticket ticket = ticketFromHttpSession(req.getSession());
            Session session = sessionService.findById(ticket.getSessionId()).orElse(null);
            model.addAttribute("ticket", ticket);
            model.addAttribute("ticketSession", session);
            return "tickets/review";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/tickets/placeOrder")
    public String placeOrder(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try {
            Optional<Ticket> savedTicket = ticketService.add(ticketFromHttpSession(req.getSession()));
            if (savedTicket.isEmpty()) {
                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "Не удалось забронировать билет. Попробуйте выбрать другой ряд и место."
                );
                return "redirect:/tickets/selectPlace";
            }
            redirectAttributes.addFlashAttribute("successMessage", "Место забронировано");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/tickets/cancelOrder")
    public String cancelOrder(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        HttpSession httpSession = req.getSession();
        httpSession.removeAttribute("ticket");
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Бронирование отменено"
        );
        return "redirect:/";
    }

    private Ticket ticketFromHttpSession(HttpSession httpSession) throws IllegalArgumentException {
        Ticket sessionTicket = (Ticket) httpSession.getAttribute("ticket");
        Optional<Session> session = sessionService.findById(sessionTicket.getSessionId());
        int sessionId = 0;
        if (session.isPresent()) {
            sessionId = session.get().getId();
            session = sessionService.findById(sessionId);
        }
        if (session.isEmpty()) {
            throw (new IllegalArgumentException(String.format("Сеанс #%d не существует", sessionId)));
        }
        return new Ticket(
                0,
                sessionId,
                sessionTicket.getPosRow(),
                sessionTicket.getCell(),
                ((User) httpSession.getAttribute("user")).getId()
        );
    }
}