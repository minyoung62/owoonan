package com.owoonan.owoonan.domain.workout.api;

import com.owoonan.owoonan.domain.workout.domain.vo.WorkoutPart;
import com.owoonan.owoonan.domain.workout.dto.WorkoutCreateRequest;
import com.owoonan.owoonan.domain.workout.dto.WorkoutResponseDto;
import com.owoonan.owoonan.domain.workout.dto.WorkoutUpdateRequestDto;
import com.owoonan.owoonan.domain.workout.service.WorkoutService;
import com.owoonan.owoonan.global.error.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
@Api(tags="운동 기능")
public class WorkoutApi {

    private final WorkoutService workoutService;


    @PostMapping()
    @ApiOperation(value = "운동 추가", notes = "디폴트로 있는 운동이 부족하다면 추가")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=503, message = "중복된 운동 이름 입니다", response = ErrorResponse.class)
    })
    public ResponseEntity<WorkoutResponseDto> create(@Valid @RequestBody WorkoutCreateRequest workoutCreateRequest) {
        WorkoutResponseDto workoutResponseDto = workoutService.create(workoutCreateRequest.toEntity(), getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(workoutResponseDto);
    }

    @GetMapping("/{workoutId}")
    @ApiResponses({
            @ApiResponse(code=501, message = "해당 운동이 존재하지 않습니다", response = ErrorResponse.class)
    })
    @ApiOperation(value = "운동 단건 조회", notes = "운동 단건 조회")
    public ResponseEntity<WorkoutResponseDto> findById(@PathVariable Long workoutId) {
        return ResponseEntity.status(HttpStatus.OK).body(workoutService.findById(workoutId));
    }

    @GetMapping()
    @ApiOperation(value = "부위별 운동들 조회", notes = "부위별 운동들을 조회 ")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=503, message = "중복된 운동 이름 입니다", response = ErrorResponse.class),
            @ApiResponse(code=400, message = "잘못된 운동 부위입니다", response = ErrorResponse.class)

    })
    public ResponseEntity<List<WorkoutResponseDto>> findByPart(@RequestParam String workoutPart) {
        return ResponseEntity.status(HttpStatus.OK).body(workoutService.findByPart(WorkoutPart.of(workoutPart), getPrincipal().getUsername()));
    }

    @PatchMapping("{workoutId}")
    @ApiOperation(value = "운동 이름 수정", notes = "운동 이름 수정")
    @ApiResponses({
            @ApiResponse(code=400, message = "잘못된 운동 부위입니다", response = ErrorResponse.class),
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동이 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=502, message = "해당 사용자의 운동이 아닙니다", response = ErrorResponse.class),
            @ApiResponse(code=503, message = "중복된 운동 이름 입니다", response = ErrorResponse.class),
    })
    public ResponseEntity<Void> update(@PathVariable Long workoutId,
                                       @Valid @RequestBody WorkoutUpdateRequestDto updateRequestDto) {
        workoutService.update(workoutId, updateRequestDto.toEntity(), getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=501, message = "해당 운동이 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=502, message = "해당 사용자의 운동이 아닙니다", response = ErrorResponse.class),
    })
    @DeleteMapping("{workoutId}")
    @ApiOperation(value = "운동 삭제", notes = "운동 삭제")
    public ResponseEntity<Void> delete(@PathVariable Long workoutId) {
        workoutService.delete(workoutId, getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
