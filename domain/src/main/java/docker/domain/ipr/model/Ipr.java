package docker.domain.ipr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ipr {

    private Long id;
    private String message;
    private Long number;
    private Boolean isOK;
    private LocalDate someDate;
    private DualClass dual;
    private List<Some> some;

    public Ipr(Long id, String message, Long number, Boolean isOK, LocalDate someDate, DualClass dual) {
        this.id = id;
        this.message = message;
        this.number = number;
        this.isOK = isOK;
        this.someDate = someDate;
        this.dual = dual;
    }
}
