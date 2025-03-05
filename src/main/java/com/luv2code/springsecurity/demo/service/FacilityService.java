package com.luv2code.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.dao.FacilityRepository;
import com.luv2code.springsecurity.demo.dto.ClinicWithFacilitiesDTO;
import com.luv2code.springsecurity.demo.dto.FacilityDTO;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    public List<ClinicWithFacilitiesDTO> searchFacilities(String country, String regionState, String city, String hospitalName) {
        List<Object[]> results = facilityRepository.searchFacilities(
                country != null && !country.isEmpty() ? country : null,
                regionState != null && !regionState.isEmpty() ? regionState : null,
                city != null && !city.isEmpty() ? city : null,
                hospitalName != null && !hospitalName.isEmpty() ? hospitalName : null
        );

        Map<Integer, ClinicWithFacilitiesDTO> clinicMap = new HashMap<>();

        for (Object[] row : results) {
            Integer clinicId = (Integer) row[0];
            String clinicName = (String) row[1];

            ClinicWithFacilitiesDTO clinicDTO = clinicMap.computeIfAbsent(clinicId, id -> {
                ClinicWithFacilitiesDTO dto = new ClinicWithFacilitiesDTO();
                dto.setClinicId(clinicId);
                dto.setClinicName(clinicName);
                dto.setFacilities(new ArrayList<>());
                return dto;
            });

            FacilityDTO facilityDTO = new FacilityDTO();
            facilityDTO.setFacilityId((Integer) row[2]);
            facilityDTO.setFacilityName((String) row[3]);
            facilityDTO.setUnitNo((String) row[4]);
            facilityDTO.setBlock((String) row[5]);
            facilityDTO.setStreet((String) row[6]);
            facilityDTO.setBuildingName((String) row[7]);
            facilityDTO.setRegionState((String) row[8]);
            facilityDTO.setCity((String) row[9]);
            facilityDTO.setCountry((String) row[10]);
            facilityDTO.setPostalCode((String) row[11]);
            facilityDTO.setTotalDoctor((Integer) row[12]);
            facilityDTO.setTotalSpeacialty((Integer) row[13]);
            facilityDTO.setTotalSubSpecialty((Integer) row[14]);

            clinicDTO.getFacilities().add(facilityDTO);
        }

        return new ArrayList<>(clinicMap.values());
    }
}

