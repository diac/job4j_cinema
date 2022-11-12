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
    public String selectSessionView(Model model) {
        List<Session> sessions = sessionService.findAll();
        model.addAttribute("sessions", sessions);
        return "tickets/selectSession";
    }

    @PostMapping("/")
    public String selectSession(@ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        return "redirect:/tickets/selectRow";
    }

    @GetMapping("/tickets/selectRow")
    public String selectRowPage(Model model) {
        Hall hall = hallService.getHall();
        model.addAttribute("hall", hall);
        return "tickets/selectRow";
    }

    @PostMapping("/tickets/selectRow")
    public String selectRow(@ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        return "redirect:/tickets/selectPlace";
    }

    @GetMapping("/tickets/selectPlace")
    public String selectPlacePage(Model model, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        Hall hall = hallService.getHall();
        model.addAttribute("hall", hall);
        model.addAttribute("posRow", httpSession.getAttribute("ticket.pos_row"));
        return "tickets/selectPlace";
    }

    @PostMapping("/tickets/selectPlace")
    public String selectPlace(@ModelAttribute Ticket ticket, HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        return "redirect:/tickets/review";
    }

    @GetMapping("/tickets/review")
    public String reviewPage(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
        try {
            Ticket ticket = ticketFromHttpSession(req.getSession());
            model.addAttribute("ticket", ticket);
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
                return "redirect:/tickets/selectRow";
            }
            redirectAttributes.addFlashAttribute("successMessage", "Место забронировано");
        }  catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    private Ticket ticketFromHttpSession(HttpSession httpSession) throws IllegalArgumentException {
        int sessionId = (Integer) httpSession.getAttribute("ticket.session_id");
        Optional<Session> session = sessionService.findById(sessionId);
        if (session.isEmpty()) {
            throw (new IllegalArgumentException(String.format("Сеанс #%d не существует", sessionId)));
        }
        return new Ticket(
                0,
                session.get(),
                (Integer) httpSession.getAttribute("ticket.pos_row"),
                (Integer) httpSession.getAttribute("ticket.cell"),
                ((User) httpSession.getAttribute("user"))
        );
    }
}