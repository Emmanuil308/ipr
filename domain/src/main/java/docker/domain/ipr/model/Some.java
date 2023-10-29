package docker.domain.ipr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Some {

    private Long id;
    private Ipr ipr;
    private String field1;
    private String field2;
}
