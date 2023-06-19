package com.api.service;

import com.api.entity.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DetectorService {
    private final static String DATA_SOURCE = "src/main/resources/data/data.json";
    private ObjectMapper objectMapper;

    public Detector getDetectorById(Long id) throws IOException {
        log.info("IN DetectorService getDetectorById() {}", id);
        Detector detector = null;
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonParser jsonParser = jsonFactory.createParser(new File(DATA_SOURCE))) {
            while (jsonParser.nextToken() != null) {
                String fieldName = jsonParser.getCurrentName();
                if ("id".equals(fieldName)) {
                    jsonParser.nextToken();
                    String currentId = jsonParser.getValueAsString();
                    if (id.equals(Long.parseLong(currentId))) {
                        jsonParser.nextToken(); // Перемещаемся к следующему токену после id
                        jsonParser.nextToken();
                        State state = State.valueOf(jsonParser.getValueAsString());
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        String serialNumber = jsonParser.getValueAsString();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        String model = jsonParser.getValueAsString();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        String address = jsonParser.getValueAsString();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        double latitude = jsonParser.getDoubleValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        double longitude = jsonParser.getDoubleValue();
                        GpsCoordinate gpsCoordinate = new GpsCoordinate(latitude, longitude);
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        latitude = jsonParser.getDoubleValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        longitude = jsonParser.getDoubleValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        String zoneAddress = jsonParser.getValueAsString();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        int x1 = jsonParser.getIntValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        int y1 = jsonParser.getIntValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        int x2 = jsonParser.getIntValue();
                        jsonParser.nextToken();
                        jsonParser.nextToken();
                        int y2 = jsonParser.getIntValue();
                        Zone zone = new Zone(
                                new GpsCoordinate(latitude, longitude),
                                zoneAddress,
                                new ArrayList<>(List.of(
                                        new Point(x1, y1),
                                        new Point(x2, y2)
                                ))
                        );

                        detector = Detector
                                .builder()
                                .id(id)
                                .state(state)
                                .serialNumber(serialNumber)
                                .model(model)
                                .address(address)
                                .gpsCoordinate(gpsCoordinate)
                                .zone(zone)
                                .build();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (detector == null)
            detector = getNewBuiltDetector();
//        detector.setState(State.NEW);
        return detector;
    }

    public Detector initialize(Detector detector) {
        log.info("IN DetectorService initialize() {}", detector);
        detector.setState(State.SETUP);
        return detector;
    }

    public Detector activate(Detector detector) {
        log.info("IN DetectorService activate() {}", detector);
        detector.setState(State.ACTIVE);
        return detector;
    }

    public Detector setup(Detector detector) {
        log.info("IN DetectorService setup() {}", detector);
        detector.setState(State.SETUP);
        return detector;
    }

    public Detector reset(Detector detector) throws IOException {
        log.info("IN DetectorService reset() {}", detector);
//        detectorRepository.delete(detector);
        detector = getNewBuiltDetector();
//        detector.setState(State.NEW);
        return detector;
    }

    public List<Detector> getAll() throws IOException {
        log.info("IN DetectorService getAll() {}");
        return objectMapper.readValue(new File(DATA_SOURCE), new TypeReference<>() {
        });
    }

    private Detector getNewBuiltDetector() throws IOException {
        log.info("IN DetectorService getNewBuiltDetector() {}");
        return Detector
                .builder()
                .id(getNewId())
                .state(State.NEW)
                .serialNumber("ABC-134")
                .model("V1")
                .address("New st.")
                .gpsCoordinate(
                        new GpsCoordinate(0.0, 0.0)
                )
                .zone(
                        new Zone(
                                new GpsCoordinate(0.0, 0.0),
                                "Russia",
                                new ArrayList<>(List.of(
                                        new Point(0, 0),
                                        new Point(0, 0)
                                ))
                        )
                )
                .build();
    }

    private long getNewId() throws IOException {
        log.info("IN DetectorService getNewId() {}");
        return getAll().
                stream().
                max(Comparator.comparing(Detector::getId))
                .get()
                .getId() + 1;
    }
}
