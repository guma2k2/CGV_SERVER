package com.movie.backend.api;

import com.movie.backend.dto.PaymentRequestVM;
import com.movie.backend.dto.SaleByMovie;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.dto.VNPayResponse;
import com.movie.backend.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/ticket")
public class TicketRestController {
    @Autowired
    private TicketService ticketService;


    @PostMapping("/vn-pay")
    public ResponseEntity<VNPayResponse> pay(@RequestBody PaymentRequestVM request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(ticketService.createVNPayPayment(request, httpServletRequest));
    }


    @PostMapping("/create")
    private ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO) {
        ticketService.saveTicket(ticketDTO);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/user/{id}")
    public List<TicketDTO> findByUserId(@PathVariable("id")Long id) {
        return ticketService.findByUserId(id) ;
    }

    @GetMapping("/{id}")
    public TicketDTO findById(@PathVariable("id")Long ticketId) {
        return ticketService.findById(ticketId);
    }

    @GetMapping("/admin/revenue/between/{startDate}/{endDate}")
    public List<SaleByMovie> getRevenueByMovie(@PathVariable("startDate")LocalDate startDate,
                                            @PathVariable("endDate")LocalDate endDate) {
        return ticketService.reportByMovie(startDate, endDate);
    }
    @PostMapping("/admin/revenue/file")
    public ResponseEntity<byte[]> exportByMovie(@RequestBody List<SaleByMovie> request) {
        byte[] datas = ticketService.exportByMovie(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=statistics.xlsx");
        return new ResponseEntity<>(datas, headers, HttpStatus.OK);
    }
}
