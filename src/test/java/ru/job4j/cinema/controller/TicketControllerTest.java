package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Test
    public void whenSelectSessionPage() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "Movie #1"),
                new Session(1, "Movie #2")
        );
        Model model = mock(Model.class);
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        when(sessionService.findAll()).thenReturn(sessions);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String page = ticketController.selectSessionPage(model);
        verify(model).addAttribute("sessions", sessions);
        assertThat(page).isEqualTo("tickets/selectSession");
    }

    @Test
    public void whenSelectSession() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.selectSession(ticket, request);
        assertThat(
                request.getSession().getAttribute("ticket.session_id")
        ).isEqualTo(ticket.getSession().getId());
        assertThat(redirect).isEqualTo("redirect:/tickets/selectRow");
    }

    @Test
    public void whenSelectRowPage() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        Hall hall = new Hall(List.of(1, 2, 3));
        when(hallService.getHall()).thenReturn(hall);
        Model model = mock(Model.class);
        String page = ticketController.selectRowPage(model);
        verify(model).addAttribute("hall", hall);
        assertThat(page).isEqualTo("tickets/selectRow");
    }

    @Test
    public void whenSelectRow() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.selectRow(ticket, request);
        assertThat(
                request.getSession().getAttribute("ticket.pos_row")
        ).isEqualTo(ticket.getPosRow());
        assertThat(redirect).isEqualTo("redirect:/tickets/selectPlace");
    }

    @Test
    public void whenSelectPlacePage() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        Hall hall = new Hall(List.of(1, 2, 3));
        when(hallService.getHall()).thenReturn(hall);
        Model model = mock(Model.class);
        String page = ticketController.selectPlacePage(model, request);
        verify(model).addAttribute("hall", hall);
        verify(model).addAttribute("posRow", request.getSession().getAttribute("ticket.pos_row"));
        assertThat(page).isEqualTo("tickets/selectPlace");
    }

    @Test
    public void whenSelectPlace() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.selectPlace(ticket, request);
        assertThat(
                request.getSession().getAttribute("ticket.cell")
        ).isEqualTo(ticket.getCell());
        assertThat(redirect).isEqualTo("redirect:/tickets/review");
    }

    @Test
    public void whenReviewPage() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Session session = new Session(0, "Test session");
        when(sessionService.findById(0)).thenReturn(Optional.of(session));
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        httpSession.setAttribute("user", new User());
        Model model = mock(Model.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String page = ticketController.reviewPage(model, request, redirectAttributes);
        verify(model).addAttribute("ticket", ticket);
        assertThat(page).isEqualTo("tickets/review");
    }

    @Test
    public void whenReviewPageAndSessionDoesNotExistThenRedirect() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        httpSession.setAttribute("user", new User());
        Model model = mock(Model.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.reviewPage(model, request, redirectAttributes);
        assertThat(redirect).isEqualTo("redirect:/");
    }

    @Test
    public void whenPlaceOrder() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        HttpSession httpSession = request.getSession();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        httpSession.setAttribute("user", new User());
        when(ticketService.add(ticket)).thenReturn(Optional.of(ticket));
        Session session = new Session(0, "Test session");
        when(sessionService.findById(0)).thenReturn(Optional.of(session));
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.placeOrder(request, redirectAttributes);
        assertThat(redirect).isEqualTo("redirect:/");
    }

    @Test
    public void whenPlaceOrderFail() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        HttpSession httpSession = request.getSession();
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        httpSession.setAttribute("user", new User());
        when(ticketService.add(ticket)).thenReturn(Optional.empty());
        Session session = new Session(0, "Test session");
        when(sessionService.findById(0)).thenReturn(Optional.of(session));
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.placeOrder(request, redirectAttributes);
        assertThat(redirect).isEqualTo("redirect:/tickets/selectRow");
    }

    @Test
    public void whenCancelOrder() {
        SessionService sessionService = mock(SessionService.class);
        TicketService ticketService = mock(TicketService.class);
        HallService hallService = mock(HallService.class);
        HttpServletRequest request = new MockHttpServletRequest();
        HttpSession httpSession = request.getSession();
        Ticket ticket = new Ticket(0, new Session(), 0, 0, new User());
        httpSession.setAttribute("ticket.session_id", ticket.getSession().getId());
        httpSession.setAttribute("ticket.pos_row", ticket.getPosRow());
        httpSession.setAttribute("ticket.cell", ticket.getCell());
        httpSession.setAttribute("user", new User());
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        TicketController ticketController = new TicketController(ticketService, sessionService, hallService);
        String redirect = ticketController.cancelOrder(request, redirectAttributes);
        assertThat(httpSession.getAttribute("ticket.session_id")).isNull();
        assertThat(httpSession.getAttribute("ticket.pos_row")).isNull();
        assertThat(httpSession.getAttribute("ticket.cell")).isNull();
        assertThat(redirect).isEqualTo("redirect:/");
    }
}