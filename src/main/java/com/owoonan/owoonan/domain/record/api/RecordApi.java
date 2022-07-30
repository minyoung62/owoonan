package com.owoonan.owoonan.domain.record.api;

import com.owoonan.owoonan.domain.record.dto.*;
import com.owoonan.owoonan.domain.record.service.RecordService;
import com.owoonan.owoonan.global.error.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
@Api(tags = "기록 기능")
public class RecordApi {

    private final RecordService recordService;

    @PostMapping()
    @ApiOperation(value = "기록 생성", notes = "saveDate에 2022-08-01 이렇게 넣어야함")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동 루틴은 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=505, message = "해당 사용자의 루틴이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> create(@Valid @RequestBody RecordCreateRequest request) {
        recordService.create(request.getRequestDtos(), request.getSaveDate() , getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("{recordId}")
    @ApiOperation(value = "기록 수정")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=508, message = "해당 사용자의 기록이 아닙니다", response = ErrorResponse.class),
    })
    public ResponseEntity<Void> update(@PathVariable Long recordId,@Valid @RequestBody RecordUpdateRequestDto request) {
        recordService.update(recordId, request.toEntity(), getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @DeleteMapping("{recordId}")
    @ApiOperation(value = "기록 삭제")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=508, message = "해당 사용자의 기록이 아닙니다", response = ErrorResponse.class),
    })
    public ResponseEntity<Void> delete(@PathVariable Long recordId) {
        recordService.delete(recordId, getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @GetMapping()
//    public ResponseEntity<List<ResultWorkoutRecordDto>> findRecord(
//            @DateTimeFormat(pattern = "yyyy-MM-dd")
//            @RequestParam("localDate") LocalDate localDate) {
//        return ResponseEntity.status(HttpStatus.OK).body(recordService.findRecordsForTheMonth(localDate, getPrincipal().getUsername()));
//    }

    @GetMapping()
    @ApiOperation(value = "월별 기록 조회", notes = "8월에 모든 데이터를 조회하기 위해 params에 2022-08-01 이렇게 넣어야함")
    public ResponseEntity<List<DayRecord>> findRecords(
            @Valid
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @RequestParam(value = "localDate", required = true) LocalDate localDate) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.findRecords(localDate, getPrincipal().getUsername()));
    }

    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
