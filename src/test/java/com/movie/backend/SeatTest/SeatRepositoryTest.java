package com.movie.backend.SeatTest;

import com.movie.backend.entity.Room;
import com.movie.backend.entity.Seat;
import com.movie.backend.entity.SeatType;
import com.movie.backend.repository.SeatRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SeatRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    public void testGetListSeat() {
        List<String> list = Arrays.asList("A1, A2, A3", "A4, A5, A6");
        String[] array = list.stream()
                .flatMap(s -> Arrays.stream(s.split(",\\s*")))
                .toArray(String[]::new);
        for(String s : array) {
            System.out.println(s);
        }
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testCreateSeatComplex1() {
        Room room3 = entityManager.find(Room.class , 3L) ;
        String[] text = new String[]{"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "J" , "K" , "L" , ""};
        List<Seat> seats = new ArrayList<>();
        String[] capacity = room3.getCapacity().split("x");
        int row = Integer.parseInt(capacity[0]);
        int col = Integer.parseInt(capacity[1]);
        System.out.println(col);
        System.out.println(row);
        for (int i = 1 ;i <= row ;i++) {
            for (int j = 1 ; j <= col ;j++) {
                if(i >= 1 && i <= 3  && j >=2 && j<=8) {
                    Seat seat = Seat.builder()
                            .seat_name(text[i - 1]+ (col-j + 1))
                            .room(room3)
                            .type(SeatType.NORMAL)
                            .row_num(i)
                            .column_num(j)
                            .build();
                    seats.add(seat);
                }else if(i >= 4 && i <= 10 && j >= 2 && j <= 10) {
                    Seat seat;
                    if(i == 4) {
                        String seatName = text[i - 1] + (col - j + 1);
                        seat = Seat.builder()
                                .seat_name(seatName)
                                .room(room3)
                                .type(SeatType.NORMAL)
                                .row_num(i)
                                .column_num(j)
                                .build();
                    }else {
                        seat = Seat.builder()
                                .seat_name(text[i - 1] + (col-j + 1))
                                .room(room3)
                                .type(SeatType.VIP)
                                .row_num(i)
                                .column_num(j)
                                .build();
                    }
                    seats.add(seat);
                } else if (i == row) {
                    Seat seat = Seat.builder()
                            .seat_name(text[i - 1]+ (col-j + 1))
                            .room(room3)
                            .type(SeatType.KING)
                            .row_num(i)
                            .column_num(j)
                            .build();
                    seats.add(seat);
                }
            }
        }

//        seats.forEach(seat -> System.out.println(seat.getSeat_name()));
        System.out.println(seats.get(0).getRow_num());
        System.out.println(seats.get(0).getColumn_num());
        System.out.println(seats.get(seats.size()-1).getColumn_num());
        System.out.println(seats.get(seats.size()-1).getRow_num());

        seatRepository.saveAll(seats) ;
    }

    @Test
    public void testCreateSeatComplex2() {
        Room room4 = entityManager.find(Room.class , 4L) ;
        String[] text = new String[]{"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "J" , "K" , "L" , ""};
        List<Seat> seats = List.of(
                Seat.builder()
                        .seat_name(text[4]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(5)
                        .column_num(22)
                        .build(),
                Seat.builder()
                        .seat_name(text[5]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(6)
                        .column_num(22)
                        .build(),
                Seat.builder()
                        .seat_name(text[6]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(7)
                        .column_num(22)
                        .build(),
                Seat.builder()
                        .seat_name(text[7]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(8)
                        .column_num(22)
                        .build(),
                Seat.builder()
                        .seat_name(text[8]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(9)
                        .column_num(22)
                        .build(),
                Seat.builder()
                        .seat_name(text[9]+1)
                        .room(room4)
                        .type(SeatType.NORMAL)
                        .row_num(10)
                        .column_num(22)
                        .build()
        );
//        String[] capacity = room4.getCapacity().split("x");
//        int row = Integer.parseInt(capacity[0]);
//        int col = Integer.parseInt(capacity[1]);
//        System.out.println(row);
//        System.out.println(col);

//        for (int i = 1 ; i <= row ; i++) {
//            for (int j = 1; j <= col; j++) {
//                if(i >= 1 && i <= 4 && (j <= 4  ) ) {
//                    Seat seat = Seat.builder()
//                            .seat_name(text[i - 1]+ (col-j -1))
//                            .room(room4)
//                            .type(SeatType.NORMAL)
//                            .row_num(i)
//                            .column_num(j)
//                            .build();
//                    seats.add(seat);
//                } else if ( i >= 1 && i <= 4 && j > 20) {
//                    Seat seat = Seat.builder()
//                            .seat_name(text[i - 1]+ (col-j + 1))
//                            .room(room4)
//                            .type(SeatType.NORMAL)
//                            .row_num(i)
//                            .column_num(j)
//                            .build();
//                    seats.add(seat);
//                }
//                else if (i >= 1 && i <= 4 && (j > 5 && j < 20)){
//                    Seat seat = Seat.builder()
//                            .seat_name(text[i - 1]+ (col-j))
//                            .room(room4)
//                            .type(SeatType.NORMAL)
//                            .row_num(i)
//                            .column_num(j)
//                            .build();
//                    seats.add(seat);
//                } else if(i >= 5 && i <= 10) {
//                    if(j >= 6 && j <= 19) {
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j))
//                                .room(room4)
//                                .type(SeatType.VIP)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    }else if (j >=1 && j < 6) {
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j -1))
//                                .room(room4)
//                                .type(SeatType.NORMAL)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    } else if (j > 20 && j <= 22){
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j + 1))
//                                .room(room4)
//                                .type(SeatType.NORMAL)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    }
//                }else{
//                    if(j > 6 && j < 19 ) {
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j - 1))
//                                .room(room4)
//                                .type(SeatType.DELUXE)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    }else if((j >= 1 && j < 5)){
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j -1))
//                                .room(room4)
//                                .type(SeatType.NORMAL)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    } else if (j > 20 && j <= 22) {
//                        Seat seat = Seat.builder()
//                                .seat_name(text[i - 1]+ (col-j ))
//                                .room(room4)
//                                .type(SeatType.NORMAL)
//                                .row_num(i)
//                                .column_num(j)
//                                .build();
//                        seats.add(seat);
//                    }
//                }
//            }
//        }

//        System.out.println(seats.get(0).getRow_num());
//        System.out.println(seats.get(0).getColumn_num());
//        System.out.println(seats.get(seats.size()-1).getColumn_num());
//        System.out.println(seats.get(seats.size()-1).getRow_num());
//
        seatRepository.saveAll(seats) ;
    }

    @Test
    public void testCreateSeatNormal() {
        Room room10 = entityManager.find(Room.class , 10L) ;
        String[] text = new String[]{"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H"  , "I" , "J" , "K" , "L" ,
                "M" , "N" , "0" , ""};
        List<Seat> seats = new ArrayList<>();
        String[] capacity = room10.getCapacity().split("x");
        int row = Integer.parseInt(capacity[0]);
        int col = Integer.parseInt(capacity[1]);
        System.out.println(row);
        System.out.println(col);
        for (int i = 1 ; i <= row ; i++) {
            for (int j = 1; j <= col; j++) {
//                System.out.println(text[i - 1]+ (col-j + 1) + " ");
                if (i >= 1 && i <= 4) {
                    Seat seat = Seat.builder()
                            .seat_name(text[i - 1] + (col - j + 1))
                            .room(room10)
                            .type(SeatType.NORMAL)
                            .row_num(i)
                            .column_num(j)
                            .build();
                    seats.add(seat);
                } else if (i > 4 && i <= 13) {
                    Seat seat = Seat.builder()
                            .seat_name(text[i - 1] + (col - j + 1))
                            .room(room10)
                            .type(SeatType.VIP)
                            .row_num(i)
                            .column_num(j)
                            .build();
                    seats.add(seat);
                } else {
                    if (j >= 1 && j <= 10) {
                        Seat seat = Seat.builder()
                                .seat_name(text[i - 1] + (col - j - 2))
                                .room(room10)
                                .type(SeatType.COUPLE)
                                .row_num(i)
                                .column_num(j)
                                .build();
                        seats.add(seat);
                    } else if (j >= 13 && j <= 20) {
                        Seat seat = Seat.builder()
                                .seat_name(text[i - 1] + (col - j ))
                                .room(room10)
                                .type(SeatType.COUPLE)
                                .row_num(i)
                                .column_num(j)
                                .build();
                        seats.add(seat);

                    }

                }
            }
        }

//        seats.forEach(seat -> System.out.println(seat.getSeat_name()));
//        System.out.println(seats.get(0).getRow_num());
//        System.out.println(seats.get(0).getColumn_num());
//        System.out.println(seats.get(seats.size()-1).getRow_num());
//        System.out.println(seats.get(seats.size()-1).getColumn_num());
        seatRepository.saveAll(seats);
    }
    public void testCreateSeatBasic() {
        Room room4 = entityManager.find(Room.class , 4L) ;
        String[] text = new String[]{"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H"  , "I" , "J" , "K" , "L" ,
                "M" , "N" , "0" , ""};
        List<Seat> seats = new ArrayList<>();
        String[] capacity = room4.getCapacity().split("x");
        int row = Integer.parseInt(capacity[0]);
        int col = Integer.parseInt(capacity[1]);
    }


    @Test
    public void findSeat() {
        String[] seat = {"A1" , "A2"} ;
        Long roomId = 1L ;
        List<Seat> seats = seatRepository.findByNameRoom(seat , roomId);
        assert seats.size() == 2 ;
    }
}
