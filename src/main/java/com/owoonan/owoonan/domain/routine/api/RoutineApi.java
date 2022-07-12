package com.owoonan.owoonan.domain.routine.api;

import com.owoonan.owoonan.domain.routine.dto.*;
import com.owoonan.owoonan.domain.routine.service.RoutineService;
import com.owoonan.owoonan.global.error.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routine")
@RequiredArgsConstructor
@Api(tags ="루틴 기능")
public class RoutineApi {

    private final RoutineService routineService;

    @PostMapping()
    @ApiOperation(value = "루틴 만들기")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 사용자의 운동이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<Long> create(@RequestBody RoutineCreateRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.OK).
                body(routineService.create(
                    requestDto.toEntity(),
                    requestDto.getWorkoutIds(),
                    requestDto.getWorkoutParts(),
                    getPrincipal().getUsername())
                );
    }

    @PatchMapping("{routineId}")
    @ApiOperation(value = "루틴 수정")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=504, message = "해당 운동 루틴은 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=505, message = "해당 사용자의 루틴이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> update(@PathVariable Long routineId,
                                       @RequestBody RoutineUpdateRequestDto requestDto) {
        routineService.update(routineId,
                requestDto.toEntity(),
                requestDto.getWorkoutIds(),
                requestDto.getWorkoutParts(),
                getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    @ApiOperation(value = "모든 루틴 조회")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class)
    })
    public ResponseEntity<List<RoutineResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(routineService.findAll(getPrincipal().getUsername()));
    }

    @GetMapping("/{routineId}")
    @ApiOperation(value = "루틴 단건 조회")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동 루틴은 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=505, message = "해당 사용자의 루틴이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<WorkoutRoutineResponseDto> findOne(@PathVariable Long routineId) {
        return ResponseEntity.status(HttpStatus.OK).body(routineService.findOne(routineId, getPrincipal().getUsername()));
    }

    @GetMapping("/{routineId}/uncheckedWorkout")
    @ApiOperation(value = "루틴에 포함되지 않은 운동 가져오기")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동 루틴은 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=505, message = "해당 사용자의 루틴이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<List<UnCheckParOfWorkout>> findUncheckedWorkout(@PathVariable Long routineId) {
        return ResponseEntity.status(HttpStatus.OK).body(routineService.findUncheckedWorkout(routineId, getPrincipal().getUsername()));
    }

    @DeleteMapping("{routineId}")
    @ApiOperation(value ="루틴 삭제")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동 루틴은 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=505, message = "해당 사용자의 루틴이 아닙니다", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> delete(@PathVariable Long routineId) {
        routineService.delete(routineId, getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
