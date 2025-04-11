package com.movie.backend.service;

import com.movie.backend.dto.*;
import com.movie.backend.entity.*;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.BookingComboRepository;
import com.movie.backend.repository.BookingRepository;
import com.movie.backend.repository.TicketRepository;
import com.movie.backend.repository.UserRepository;
import com.movie.backend.ultity.DateTimeUtil;
import com.movie.backend.ultity.RandomString;
import com.movie.backend.ultity.VNPayConfig;
import com.movie.backend.ultity.VNPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketService {

    @Autowired
    private VNPayConfig vnPayConfig;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CinemaService cinemaService ;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingComboRepository bookingComboRepository;

    private final static String ticketCountKey = "ticketCount";
    private final static String totalRevenueKey = "totalRevenue";


    public List<TicketDTO> findByUserId(Long userId) {
        if (userId == null) {
            throw new UserException("The id of user cannot found");
        }
        List<Ticket> tickets = ticketRepository.findByUser(userId);
        List<TicketDTO> ticketDTOS = tickets.stream().map(ticket -> {
            TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
            Long bookingId = ticket.getBooking().getId();
            ticketDTO.setBookingId(bookingId);
            ticketDTO.setUserId(userId);
            String createdAt = DateTimeUtil.formatLocalDateTime(ticket.getCreatedTime());
            ticketDTO.setCreatedAt(createdAt);
            List<BookingComboDTO> comboDTOS = bookingComboRepository.findByBooking(bookingId)
                    .stream()
                    .map(bookingCombo ->  modelMapper.map(bookingCombo, BookingComboDTO.class))
                    .collect(Collectors.toList());;
            ticketDTO.setCombos(comboDTOS);
            return ticketDTO ;
        }).collect(Collectors.toList());
        return ticketDTOS;
    }

    @Transactional
    public void saveTicket(TicketDTO ticketDTO) {
        Long bookingId = ticketDTO.getBookingId();
        String randomCode = RandomString.make(12);
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Ticket ticket = Ticket.builder()
                .bank(ticketDTO.getBank())
                .booking(booking)
                .qrCode(randomCode)
                .build();
        bookingRepository.updateBookingStatus(bookingId, BookingStatus.SUCCESS);
        ticketRepository.save(ticket);
    }

    public TicketDTO findById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        Long bookingId = ticket.getBooking().getId();
        List<BookingComboDTO> comboDTOS = bookingComboRepository.findByBooking(bookingId)
                .stream()
                .map(bookingCombo ->  modelMapper.map(bookingCombo, BookingComboDTO.class))
                .collect(Collectors.toList());;
        ticketDTO.setCombos(comboDTOS);
        return ticketDTO ;
    }
    public List<SaleByMovie> reportByMovie(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();
        List<Ticket> tickets = ticketRepository.findByDateMovie(startDateTime, endDateTime);
        Map<Movie, Map<String, Object>> revenueByMovie = tickets.stream()
                .collect(Collectors.groupingBy(
                        ticket -> ticket.getBooking().getEvent().getMovie(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ticketList -> {
                                    int ticketCount = ticketList.size();
                                    long totalRevenue = (long) ticketList.stream()
                                            .mapToDouble(t -> t.getBooking().getTotal_amount())
                                            .sum();
                                    Map<String, Object> result = new HashMap<>();
                                    result.put(ticketCountKey, ticketCount);
                                    result.put(totalRevenueKey, totalRevenue);
                                    return result;
                                }
                        )
                ));

        List<SaleByMovie> sales = new ArrayList<>();
        revenueByMovie.forEach((movie, data) -> {
            int ticketCount = (int) data.get(ticketCountKey);
            Long totalRevenue = (Long) data.get(totalRevenueKey);
            SaleByMovie sale = new SaleByMovie(movie.getId(), movie.getTitle(), movie.getPoster_url(), ticketCount, totalRevenue);
            sales.add(sale);
        });
        return sales;
    }


    public VNPayResponse createVNPayPayment(PaymentRequestVM request, HttpServletRequest httpServletRequest) {
        long amount = request.amount() * 100L;
        String bankCode = request.bankCode();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(request);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtils.getIpAddress(httpServletRequest));
        //build query url
        String queryUrl = VNPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtils.getPaymentURL(vnpParamsMap, false);
        queryUrl += "&vnp_SecureHash=" + VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new VNPayResponse("ok", "success", paymentUrl);
    }

    public byte[] exportByMovie(List<SaleByMovie> request) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Statistics");

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Stt");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Tên khóa học");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Số lượng");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Tổng tiền (đồng)");
        headerCell.setCellStyle(headerStyle);

        // Populate Data Rows
        int rowIdx = 1;
        for (SaleByMovie stat : request) {
            Row row = sheet.createRow(rowIdx);
            row.createCell(0).setCellValue(rowIdx);
            row.createCell(1).setCellValue(stat.title());
            row.createCell(2).setCellValue(stat.ticketCount());
            row.createCell(3).setCellValue(stat.totalRevenue());
            rowIdx++;
        }

        // Auto-size Columns
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        // Write to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }
}
