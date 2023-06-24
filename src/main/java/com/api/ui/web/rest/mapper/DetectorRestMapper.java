package com.api.ui.web.rest.mapper;

import com.api.infrustructure.service.dto.DetectorActivateDTO;
import com.api.infrustructure.service.dto.DetectorInitializeDTO;
import com.api.ui.web.rest.dto.DetectorActiveRequest;
import com.api.ui.web.rest.dto.DetectorInitializeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface DetectorRestMapper {
  @Mapping(target = "serialNumber", source = "request.serialNumber")
  @Mapping(target = "model", source = "request.model")
  @Mapping(target = "conformityCertificate", source = "request.conformityCertificate")
  DetectorInitializeDTO initializeRequestToDto(DetectorInitializeRequest request);

  @Mapping(target = "serialNumber", source = "serialNumber")
  @Mapping(target = "address", source = "request.address")
  @Mapping(target = "zone", source = "request.zone")
  @Mapping(target = "location", source = "request.location")
  DetectorActivateDTO activateRequestToDto(String serialNumber, DetectorActiveRequest request);
}
