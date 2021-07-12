package com.oneandone.stonith.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneandone.stonith.entities.DialectConfiguration;
import com.oneandone.stonith.errors.RequestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Service
public class FileReaderService {
    @Value("${dialect.base.folder}")
    private String basedir;

    public DialectConfiguration readFile(String dialect, String operation) throws RequestException {
        Path filePath = Paths.get(String.format("%s/%s/%s.json", basedir, dialect, operation));
        String fileContent;
        try {
            fileContent =  Files.readString(filePath);
        } catch (IOException e) {
            throw new RequestException(e, "Failed to read file. This operation may not be permitted" +
                    " for dialect %s and %s operation", dialect, operation);
        }
        DialectConfiguration configuration;
        try {
            JSONObject jsonObject = new JSONObject(fileContent);
            configuration = new DialectConfiguration();
            configuration.setEndpoint(jsonObject.getString("endpoint"));
            configuration.setMethod(this.getHttpMethod(jsonObject.getString("method")));
            configuration.setHeaders(new ObjectMapper().readValue(jsonObject.getJSONObject("headers").toString(), HashMap.class));
            configuration.setBody(jsonObject.getJSONObject("body").toString());
        } catch (JSONException | JsonProcessingException e) {
            throw new RequestException(e, "Failed to parse json fields for dialect %s and %s operation.", dialect, operation);
        }
        return configuration;
    }

    private HttpMethod getHttpMethod(String method) throws RequestException {
        switch(method) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PATCH":
                return HttpMethod.PATCH;
            case "DELETE":
                return HttpMethod.DELETE;
            case "PUT":
                return HttpMethod.PUT;
        }
        throw new RequestException("Invalid method \"%s\" encountered. Aborting.", method);
    }

}
