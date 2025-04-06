package com.movie.backend.api;


import com.movie.backend.dto.RoomDTO;
import com.movie.backend.entity.Room;
import com.movie.backend.dto.DataContent;
import com.movie.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class RoomRestController {

    @Autowired
    private RoomService roomService ;


    @GetMapping("/admin/room")
    public DataContent firstPage() {
        return roomService.findAll();
    }
    @GetMapping("/admin/room/paginate")
    public DataContent findAllPaginate(@RequestParam("pageNum")int pageNum,
                                       @RequestParam("sortDir")String sortDir ,
                                       @RequestParam("sortField") String sortField
                                       ) {
        return roomService.findAll(pageNum, sortDir, sortField);
    }

    @GetMapping("/admin/room/{roomId}")
    public RoomDTO get(@PathVariable("roomId")Long roomId) {
        return roomService.get(roomId) ;
    }

    @GetMapping("/admin/room/cinema/{cinemaName}")
    public List<RoomDTO> findByCinema(@PathVariable("cinemaName")String cinemaName) {
        return roomService.findByCinemaName(cinemaName) ;
    }
    @PostMapping("/admin/room/save")
    public Room saveRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.saveRoom(roomDTO, null) ;
    }

    @PutMapping("/admin/room/update/{roomId}")
    public Room updateRoom(@RequestBody RoomDTO roomDTO,
                         @PathVariable("roomId") Long roomId) {
        return roomService.saveRoom(roomDTO, roomId) ;
    }

    @DeleteMapping("/admin/room/delete/{roomId}")
    public void delete(@PathVariable("roomId")Long roomId) {
        roomService.delete(roomId);
    }

}
