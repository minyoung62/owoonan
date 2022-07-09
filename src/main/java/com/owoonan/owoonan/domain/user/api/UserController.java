package com.owoonan.owoonan.domain.user.api;


import com.owoonan.owoonan.domain.user.dto.UserResponseDto;
import com.owoonan.owoonan.domain.user.service.UserService;
import com.owoonan.owoonan.global.error.ErrorResponse;
import com.owoonan.owoonan.global.error.exception.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Api(tags = "회원 기능")
public class UserController {

    private final UserService userService;

    @GetMapping
    @ApiOperation(value = "사용자 정보 조회", notes = "인증된 사용자 단 건 조회")
    @ApiResponses({
            @ApiResponse(code=500, message = "사용자가 존재하지 않습니다." , response = ErrorResponse.class)

    })
    public ResponseEntity<UserResponseDto> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(getPrincipal().getUsername()));
    }

    @DeleteMapping
    @ApiOperation(value = "회원 탈퇴", notes = "회원의 정보 삭제")
    @ApiResponses({
            @ApiResponse(code=500, message = "사용자가 존재하지 않습니다." , response = ErrorResponse.class)

    })
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser(getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
