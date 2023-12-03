package com.bupt.software.service;

import com.bupt.software.dto.LogDTO;
import com.bupt.software.dto.response.APIResponse;
import com.bupt.software.model.Log;
import com.bupt.software.utils.Constant;
import com.bupt.software.utils.Utils;
import com.bupt.software.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j()
public class LogService {

    private final LogRepository logRepository;

    public APIResponse<LogDTO> findById(Long id) {
        Optional<Log> logOptional = logRepository.findById(id);
        if (!logOptional.isPresent()) {
            log.error("Log with id {} not found", id);
            return APIResponse.notFound(null, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("1"));
        }

        Log log = logOptional.get();

        LogDTO logDTO = LogDTO.builder()
                .id(log.getId())
                .date(Utils.convertLocalDateToString(log.getDate()))
                .message(log.getMessage())
                .level(log.getLevel())
                .build();

        return APIResponse.ok(logDTO, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("6"));
    }

    public APIResponse<List<LogDTO>> findAll() {
        List<Log> logList = logRepository.findAll();

        List<LogDTO> logDTOList = logList
                .stream()
                .map(log -> LogDTO.builder()
                        .id(log.getId())
                        .date(Utils.convertLocalDateToString(log.getDate()))
                        .message(log.getMessage())
                        .level(log.getLevel())
                        .build())
                .collect(Collectors.toList());

        return APIResponse.ok(logDTOList, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("10"));
    }

    public APIResponse<LogDTO> add(LogDTO logDTO) {
        Log log = Log.builder()
                .date(LocalDate.now())
                .message(logDTO.getMessage())
                .level(logDTO.getLevel())
                .build();

        log = logRepository.save(log);

        logDTO = LogDTO.builder()
                .id(log.getId())
                .date(Utils.convertLocalDateToString(log.getDate()))
                .message(log.getMessage())
                .level(log.getLevel())
                .build();

        return APIResponse.ok(logDTO, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("7"));
    }

    public APIResponse<LogDTO> update(LogDTO logDTO) {
        Optional<Log> logOptional = logRepository.findById(logDTO.getId());
        if (!logOptional.isPresent())
            return APIResponse.notFound(null, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("1"));

        Log log = logOptional.get();

        log.setDate(LocalDate.now());
        log.setMessage(logDTO.getMessage());
        log.setLevel(logDTO.getLevel());

        log = logRepository.save(log);

        logDTO = LogDTO.builder()
                .id(log.getId())
                .date(Utils.convertLocalDateToString(log.getDate()))
                .message(log.getMessage())
                .level(log.getLevel())
                .build();

        return APIResponse.ok(logDTO, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("8"));
    }

    public APIResponse<LogDTO> deleteById(Long id) {
        Optional<Log> logOptional = logRepository.findById(id);
        if (!logOptional.isPresent())
            return APIResponse.notFound(null, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("1"));

        logRepository.deleteById(id);

        return APIResponse.ok(null, Constant.getLogResponseHashMap(), Constant.LOG_RESPONSE_CODE_PREFIX.concat("9"));
    }
}
