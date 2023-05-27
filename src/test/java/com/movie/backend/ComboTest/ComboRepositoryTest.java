package com.movie.backend.ComboTest;

import com.movie.backend.entity.Combo;
import com.movie.backend.repository.ComboRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ComboRepositoryTest {

    @Autowired
    private ComboRepository comboRepository ;
    @Test
    public void testCreateCombo() {
        List<Combo> combos = List.of(
                 Combo
                        .builder()
                        .title("JUNGLE BROWN MY COI")
                        .price(259000)
                        .description("1 bình Jungle Brown + 1 nước siêu lớn")
                        .img_url("https://www.cgv.vn/media/concession/web/6426a4ac90777_1680254125.png")
                        .build(),
                Combo
                        .builder()
                        .title("CGV SNACK COMBO")
                        .price(119000)
                        .description("1 Bắp Lớn + 2 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.")
                        .img_url("https://www.cgv.vn/media/concession/web/63aaa389a9927_1672127370.png")
                        .build(),
                Combo
                        .builder()
                        .title("MY SNACK COMBO")
                        .price(95000)
                        .description("1 Bắp Lớn + 1 Nước Siêu Lớn + 1 Snack. Nhận trong ngày xem phim.")
                        .img_url("https://www.cgv.vn/media/concession/web/63aaa361ceea4_1672127330.png")
                        .build(),
                Combo
                        .builder()
                        .title("CGV COMBO")
                        .price(115000)
                        .description("1 Bắp Lớn + 2 Nước Siêu Lớn. Nhận trong ngày xem phim.")
                        .img_url("https://www.cgv.vn/media/concession/web/63aaa31525d4c_1672127253.png")
                        .build(),
                Combo
                        .builder()
                        .title("MY COMBO")
                        .price(89000)
                        .description("1 bắp lớn + 1 nước siêu lớn. Nhận trong ngày xem phim*")
                        .img_url("https://www.cgv.vn/media/concession/web/63aaa2d81b6bf_1672127192.png")
                        .build(),
                Combo
                        .builder()
                        .title("JUNGLE BROWN COUPLE")
                        .price(499000)
                        .description("2 bình Jungle Brown + 2 nước siêu lớn + 1 bắp ngọt lớn")
                        .img_url("https://www.cgv.vn/media/concession/web/6426a4fc4b1ca_1680254204.png")
                        .build()

        );
        comboRepository.saveAll(combos);

    }
}
