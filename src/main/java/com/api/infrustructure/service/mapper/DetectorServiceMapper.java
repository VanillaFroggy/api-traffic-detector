package com.api.infrustructure.service.mapper;

import com.api.infrustructure.persistance.entity.Detector;
import com.api.infrustructure.service.dto.DetectorActivateDTO;
import com.api.infrustructure.service.dto.DetectorInitializeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetectorServiceMapper {
    @Mapping(target = "serialNumber", source = "dto.serialNumber")
    @Mapping(target = "model", source = "dto.model")
    @Mapping(target = "conformityCertificate", source = "dto.conformityCertificate")
    Detector initializeDtoToEntity(DetectorInitializeDTO dto);

    @Mapping(target = "serialNumber", source = "dto.serialNumber")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "location", source = "dto.location")
    @Mapping(target = "zone", source = "dto.zone")
    Detector activateDtoToEntity(DetectorActivateDTO dto);
}
