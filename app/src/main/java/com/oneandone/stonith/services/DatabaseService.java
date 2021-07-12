package com.oneandone.stonith.services;

import com.oneandone.stonith.entities.Server;
import com.oneandone.stonith.errors.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseService.class.getName());
    private static final String getServersQuery = "SELECT * FROM servers WHERE serial_number = :serialNumber;";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Server getServer(String serialNumber) throws UnauthorizedException {
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("serialNumber", serialNumber);
        List<Server> servers =  jdbcTemplate.query(getServersQuery, parameters, new BeanPropertyRowMapper<>(Server.class));
        if (servers.isEmpty()) {
            LOG.info("Server {} was not found inside the database.", serialNumber);
            throw new UnauthorizedException("Server %s was not found inside the database.", serialNumber);
        }
        return servers.get(0);
    }
}
