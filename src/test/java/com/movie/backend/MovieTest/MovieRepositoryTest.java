package com.movie.backend.MovieTest;


import com.movie.backend.entity.Genre;
import com.movie.backend.entity.Language;
import com.movie.backend.entity.Movie;
import com.movie.backend.entity.Rating;
import com.movie.backend.repository.GenreRepository;
import com.movie.backend.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository ;

    @Autowired
    private GenreRepository entityManager ;

    @Test
    public void  testFindAll() {
        List<Movie> listMovie = movieRepository.findAll();
        listMovie.forEach(movie -> System.out.println(movie.isShowing()));
        assert(listMovie.size() > 0);
    }

    @Test
    public void testCreate() {
//        Movie spiderManMovie = new Movie();
//        spiderManMovie.setTitle("Spider-Man: Far From Home");
//        spiderManMovie.setDescription("Following the events of Avengers: Endgame, Spider-Man must step up to take on new threats in a world that has changed forever.");
//        spiderManMovie.setDuration_minutes(129);
//        spiderManMovie.setRelease_date(LocalDate.of(2019, 7, 2));
//        spiderManMovie.setLanguage(Language.EN);
//        spiderManMovie.setRating(Rating.C13);
//        spiderManMovie.setDirector("Jon Watts");
//        spiderManMovie.setCast("Tom Holland, Samuel L. Jackson, Zendaya, Cobie Smulders");
//        spiderManMovie.setShowing(true);
//        spiderManMovie.setPoster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/s/m/smffh-localized_aw-vn-poster_6-3up_triangle.jpg");
//        spiderManMovie.setTrailer("https://www.example.com/spiderman-trailer.mp4");
//        Genre genre1 = entityManager.findById(1).get();
//        Genre genre2 = entityManager.findById(10).get();
//        Genre adventure = entityManager.findById(11).get();
//        spiderManMovie.addGenre(genre1);
//        spiderManMovie.addGenre(genre2);
//        movieRepository.save(spiderManMovie);

        List<Movie> movies = List.of(
                Movie.builder()
                        .title("PHIM ANH EM SUPER MARIO")
                        .description("Câu chuyện về cuộc phiêu lưu của anh em Super Mario đến vương quốc Nấm.")
                        .duration_minutes(93)
                        .release_date(LocalDate.of(2023 , 04 , 07))
                        .language(Language.EN)
                        .rating(Rating.P)
                        .cast("Chris Pratt, Anya Taylor-Joy, Charlie Day, …")
                        .director("Aaron Horvath, Michael Jelenic")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/s/u/super_mario_bros._payoff_poster.jpg")
                        .trailer("https://www.youtube.com/watch?v=UGO_i2tf1BM")
                        .build(),
                Movie.builder()
                        .title("PHIM CÚ ÚP RỔ ĐẦU TIÊN")
                        .description("Bộ phim hoạt hình chuyển thể từ loạt truyện tranh “Slam Dunk” nổi tiếng của Takehiko Inoue, khắc họa quá trình trưởng thành cá nhân của những học sinh trung học cống hiến tuổi trẻ cho bóng rổ. Phim theo chân Ryota Miyagi, hậu vệ của đội bóng rổ trường trung học Shohoku. ")
                        .duration_minutes(124)
                        .release_date(LocalDate.of(2023 , 04 , 14))
                        .language(Language.JP)
                        .rating(Rating.C13)
                        .cast("Masaya Fukunishi, Yoshiaki Hasegawa, Katsuhisa Hoki, Tetsu Inada, Ryota Iwasaki, Shinichiro Kamio, Mitsuaki Kanuka, Jun Kasama, Subaru Kimura,..")
                        .director("Takehiko Inoue, Yasuyuki Ebara")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/t/h/the_first_slam_dunk_-_key_visual_1_.jpg")
                        .trailer("https://www.youtube.com/watch?v=NEa0J_Q-NIY")
                        .build(),
                Movie.builder()
                        .title("KHẮC TINH CỦA QUỶ")
                        .description("Lấy cảm hứng từ những hồ sơ có thật của Cha Gabriele Amorth, Trưởng Trừ Tà của Vatican (Russell Crowe, đoạt giải Oscar®), bộ phim \"The Pope's Exorcist\" theo chân Amorth trong cuộc điều tra về vụ quỷ ám kinh hoàng của một cậu bé và dần khám phá ra những bí mật hàng thế kỷ mà Vatican đã cố gắng giấu kín.")
                        .duration_minutes(104)
                        .release_date(LocalDate.of(2023 , 04 , 14))
                        .language(Language.EN)
                        .rating(Rating.C18)
                        .cast("Russell Crowe, Franco Nero…")
                        .director("Julius Avery")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/p/o/pope_sexorcist_poster_h_m_ng_c.jpg")
                        .trailer("https://www.youtube.com/watch?v=p4LAYNacgkI")
                        .build(),
                Movie.builder()
                        .title("CUỘC CHIẾN ĐA VŨ TRỤ")
                        .description("Một phụ nữ trung niên nhập cư người Trung Quốc bị cuốn vào một cuộc phiêu lưu điên cuồng, nơi cô ấy một mình giải cứu thế giới bằng cách khám phá các vũ trụ khác và các bản thể khác của chính cô.")
                        .duration_minutes(139)
                        .release_date(LocalDate.of(2023 , 03 , 17))
                        .language(Language.EN)
                        .rating(Rating.C18)
                        .cast("Dương Tử Quỳnh, Quan Kế Huy, Stephanie Hsu, James Hong, Jamie Lee Curtis,...")
                        .director("Daniel Kwan, Daniel Scheinert")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/c/u/cu_c_chi_n_a_v_tr_-_payoff_poster_-_k_ch_th_c_fb_-_dkkc_24062022_1_.jpg")
                        .trailer("https://www.youtube.com/watch?v=4y5JUTzFlVs")
                        .build(),
                Movie.builder()
                        .title("KHÓA CHẶT CỬA NÀO SUZUME")
                        .description("\"Khóa Chặt Cửa Nào Suzume\" kể câu chuyện khi Suzume vô tình gặp một chàng trai trẻ ")
                        .duration_minutes(122)
                        .release_date(LocalDate.of(2023 , 03 , 10))
                        .language(Language.JP)
                        .rating(Rating.P)
                        .cast("ANIME")
                        .director("Makoto Shinkai")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/s/u/suzume_vn_teaser_poster.jpg")
                        .trailer("https://www.youtube.com/watch?v=CTxLZYbT9Rw")
                        .build(),
                Movie.builder()
                        .title("NGỤC TỐI & RỒNG: DANH DỰ CỦA KẺ TRỘM")
                        .description("Theo chân một tên trộm quyến rũ và một nhóm những kẻ bịp bợm nghiệp dư thực hiện vụ trộm sử thi nhằm lấy lại một di vật đã mất, nhưng mọi thứ trở nên nguy hiểm khó lường ")
                        .duration_minutes(134)
                        .release_date(LocalDate.of(2023 , 04 , 21))
                        .language(Language.EN)
                        .rating(Rating.C13)
                        .cast("Chris Pine, Michelle Rodriguez, Regé-Jean Page")
                        .director("John Francis Daley, Jonathan Goldstein")
                        .isShowing(false)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/7/0/700x1000___2.jpg")
                        .trailer("https://www.youtube.com/watch?v=P4IA6pIVb-w")
                        .build(),
                Movie.builder()
                        .title("TRANSFORMERS: QUÁI THÚ TRỖI DẬY")
                        .description("Bộ phim dựa trên sự kiện Beast Wars trong loạt phim hoạt hình \"Transformers\", nơi mà các robot có khả năng biến thành động vật khổng lồ cùng chiến đấu chống lại một mối đe dọa tiềm tàng.\n")
                        .duration_minutes(150)
                        .release_date(LocalDate.of(2023 , 6 , 9))
                        .language(Language.EN)
                        .rating(Rating.C13)
                        .cast("Michelle Yeoh, Dominique Fishback, Ron Perlman")
                        .director("Steven Caple Jr.")
                        .isShowing(false)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/t/f/tf7_700x1000.jpg")
                        .trailer("https://www.youtube.com/watch?v=FH4-_8oVWkw")
                        .build(),
                Movie.builder()
                        .title("SHAZAM! CƠN THỊNH NỘ CỦA CÁC VỊ THẦN")
                        .description("Một tác phẩm từ New Line Cinema mang tên “Shazam! Fury of the Gods,” tiếp tục câu chuyện về chàng trai Billy Batson, và bản ngã Siêu anh hùng trưởng thành của mình sau khi hô cụm từ “SHAZAM !,” ma thuật.\n")
                        .duration_minutes(130)
                        .release_date(LocalDate.of(2023 , 03 , 17))
                        .language(Language.EN)
                        .rating(Rating.P)
                        .cast("Zachary Levi, Asher Angel, Jack Dylan Grazer,")
                        .director("David F. Sandberg")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/s/h/shazam_fotg_payoff_poster_1_.jpg")
                        .trailer("https://youtube.com/watch?v=lPmzBaNJUzI")
                        .build(),
                Movie.builder()
                        .title("AVATAR: DÒNG CHẢY CỦA NƯỚC")
                        .description("Câu chuyện của “Avatar: Dòng Chảy Của Nước” lấy bối cảnh 10 năm sau những sự kiện xảy ra ở phần đầu tiên. Phim kể câu chuyện về gia đình mới của Jake Sully (Sam Worthington thủ vai) cùng những rắc rối theo sau và bi kịch họ phải chịu đựng khi phe loài người xâm lược hành tinh Pandora.")
                        .duration_minutes(192)
                        .release_date(LocalDate.of(2023 , 12 , 16))
                        .language(Language.EN)
                        .rating(Rating.C13)
                        .cast(" Sam Worthington, Zoe Saldana, Dương Tử Quỳnh,...")
                        .director("James Cameron")
                        .isShowing(false)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/a/v/avatar_2_payoff_posster_2_.jpg")
                        .trailer("https://www.youtube.com/watch?v=Ru4Jbmh7bcQ")
                        .build(),
                Movie.builder()
                        .title("CHIẾN BINH BÁO ĐEN 2: WAKANDA BẤT DIỆT")
                        .description("Nữ hoàng Ramonda, Shuri, M’Baku, Okoye và Dora Milaje chiến đấu để bảo vệ quốc gia của họ khỏi sự can thiệp của các thế lực thế giới sau cái chết của Vua T’Challa. ")
                        .duration_minutes(93)
                        .release_date(LocalDate.of(2023 , 11 , 10))
                        .language(Language.EN)
                        .rating(Rating.P)
                        .cast("Tenoch Huerta, Martin Freeman, Lupita Nyong'o")
                        .director("Ryan Coogler")
                        .isShowing(true)
                        .poster_url("https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/b/p/bp2_official_poster_1_.jpg")
                        .trailer("https://www.youtube.com/watch?v=sKX4zA52B9c")
                        .build()
        );
        movieRepository.saveAll(movies);
    }



}
