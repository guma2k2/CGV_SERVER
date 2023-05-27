package com.movie.backend.api;

import com.movie.backend.dto.SeatDTO;
import com.movie.backend.entity.Seat;
import com.movie.backend.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class SeatRestController {

    @Autowired
    private SeatService seatService ;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/admin/seat/save")
    public Seat save(@RequestBody  Seat seat) {
        return seatService.save(seat) ;
    }

    @PutMapping("/admin/seat/update/{seatId}")
    public Seat update(@RequestBody  Seat seat , @PathVariable("seatId") Long seatId) {
        Seat seatDTO = seatService.get(seatId) ;
        if(seatDTO.getSeat_name() != seat.getSeat_name()){
            if (seatService.checkExitNameInRoom(seat.getSeat_name() , seatDTO.getRoom().getId() )) {
                   seatDTO.setSeat_name(seat.getSeat_name());
            }
        }
        return seatService.save(seatDTO) ;
    }

    @DeleteMapping("/admin/seat/delete/{seatId}")
    public void delete( @PathVariable("seatId") Long seatId) {
        seatService.delete(seatId); ;
    }

}
