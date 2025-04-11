package com.movie.backend.service;


import com.movie.backend.dto.RoomDTO;
import com.movie.backend.entity.*;
import com.movie.backend.exception.BadRequestException;
import com.movie.backend.exception.MovieException;
import com.movie.backend.exception.NotFoundException;
import com.movie.backend.exception.RoomException;
import com.movie.backend.repository.RoomRepository;
import com.movie.backend.dto.DataContent;
import com.movie.backend.dto.Paginate;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Value("${application.service.room.roomPerPage}")
    public int roomPerPage ;
    @Autowired
    private ModelMapper modelMapper;


    public Room saveRoom(RoomDTO roomDTO, Long roomId) {
        boolean update = roomId != null ;
        String requestName = roomDTO.getName() ;
        String cinemaName = roomDTO.getCinema().getName() ;
        Room checkRoomExit = roomRepository.findByCinemaAndName(cinemaName, requestName);
        if(update) {
            if(checkRoomExit != null ) {
                if (!Objects.equals(checkRoomExit.getId(), roomId)) {
                    throw new RoomException("Name not valid") ;
                }
            }
        } else {
            if (checkRoomExit != null) {
                throw new RoomException("Name not valid") ;
            }
        }

        String name = roomDTO.getName();
        String capacity =  roomDTO.getCapacity();
        Cinema cinema = modelMapper.map(roomDTO.getCinema() , Cinema.class ) ;


        if(update) {
            Room oldRoom = roomRepository.findById(roomId).orElseThrow(() -> new RoomException("Not found room"));
            oldRoom.setName(name);
            oldRoom.setCapacity(capacity);
            oldRoom.setCinema(cinema);
            return roomRepository.save(oldRoom) ;
        }
        Room newRoom = Room.builder()
                .name(name)
                .capacity(capacity)
                .cinema(cinema)
                .build();
        return roomRepository.save(newRoom) ;
    }

    // list room is showing movie
    public List<RoomDTO> findByCinemaName(String name) {
        return roomRepository
                .findByCinema(name)
                .stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }
    public RoomDTO get(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomException("Room not found"));
        RoomDTO roomDTO =  modelMapper.map(room , RoomDTO.class) ;
        return roomDTO ;
    }

    public DataContent findAll() {
        return findAll(1 , "desc" , "id") ;
    }



    public DataContent findAll(Integer pageNum , String sortDir , String sortField) {
        if (pageNum == null || sortDir == null || sortField == null) {
            throw new RoomException("The pageNum or sortDir or sortField cannot null");
        }
        Sort sort = Sort.by(sortField) ;
        sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1 , roomPerPage , sort );
        Page<Room> pages = roomRepository.findAll(pageable);
        int total = pages.getTotalPages();
        int totalElements = (int) pages.getTotalElements();


        List<RoomDTO> rooms = pages.getContent()
                .stream()
                .map(room ->{
                    RoomDTO roomDTO = modelMapper.map(room, RoomDTO.class) ;
                    roomDTO.setSeats(null);
                    return roomDTO ;
                })
                .collect(Collectors.toList());
        Paginate paginate = Paginate.builder()
                .currentPage(pageNum)
                .sortDir(sortDir)
                .sortField(sortField)
                .totalPage(total)
                .totalElements(totalElements)
                .sizePage(roomPerPage)
                .build();
        return new DataContent(paginate , rooms);
    }

    @Transactional
    public void delete(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("room not found")) ;
        if (room.getSeats().size() > 0) {
            throw new BadRequestException("this room had seats");
        }
        roomRepository.deleteById(roomId);
    }

}
