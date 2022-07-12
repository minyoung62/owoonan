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
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(getPrincipal().getUsername()));
    }

    @PostMapping()
    @ApiOperation(value = "사용자 등록", notes = "RoleType이 GUEST이면 이 API를 이용해 USER로 만들 수 있음")
    @ApiResponses({
            @ApiResponse(code=500, message = "해당 유저가 존재하지 않습니다", response = ErrorResponse.class),
            @ApiResponse(code=506, message = "이미 가입한 유저입니다", response = ErrorResponse.class),
    })
    public ResponseEntity<Void> registerUser() {
        userService.registerUser(getPrincipal().getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
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

    @GetMapping("OAuth_Login_Description")
    @ApiOperation(value = "이 api는 소셜 로그인 설명을 위해 만든 것 입니다.(적상적으로 작동하지 않음) ",
    notes = "1. 소셜 로그인 이미지에 http://localhost:8080/oauth2/authorization/${socialType}?redirect_uri=http://localhost:3000/oauth/redirect 링크를 걸어둔다\n" +
            "2. socialType에는 kakao, google, naver가 들어간다\n" +
            "3. 프론트에서는 /oauth/redirect 주소의 쿼리스트링으로 값을 받는다 \n" +
            "4. 아래는 프론트에서 받았을 때 형태이다. \n"+
            "5. http://localhost:3000/oauth/redirect?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMzE1MjEyNDg2Iiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTY1OTE2MzgwNH0.DiCFXQicGFfmSdT5c5h628b-caxTNI3tQ2S249DqP7Q"
            )
    public ResponseEntity<Void> oauthLogin() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public User getPrincipal() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
