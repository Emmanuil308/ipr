package docker.domain.ipr;

import docker.domain.ipr.model.Ipr;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;

@Service
public class IprRepositoryImpl implements IprRepository {
    JdbcTemplate jdbcTemplate;
    public IprRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Ipr> findAll() {
//        return jdbcTemplate.query("select * from ipr",
//                (rs, rowNum) -> new Ipr(rs.getLong("id"),
//                        rs.getString("message"),
//                        rs.getLong("number"),
//                        rs.getBoolean("is_ok"),
//                        rs.getDate("some_date").toLocalDate(), null));
        return null;
    }

    @Override
    public Long save(Ipr item) {
        var insertIpr = new SimpleJdbcInsert(jdbcTemplate).withTableName("ipr")
                .usingGeneratedKeyColumns("id");

        var parameters = new HashMap<String, Object>();

        parameters.put("message", item.getMessage());
        parameters.put("number", item.getNumber());
        parameters.put("is_ok", item.getIsOK());
        parameters.put("some_date", item.getSomeDate());

        return (Long) insertIpr.executeAndReturnKey(parameters);
    }

}
