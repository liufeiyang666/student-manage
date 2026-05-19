package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.service.UserService;
import com.example.demo.utils.PageResult;
import com.example.demo.utils.Result;
import com.example.demo.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户CRUD、状态管理、密码重置")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "查询用户列表", description = "分页查询用户列表")
    public Result<PageResult<UserVO>> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer status) {
        IPage<UserVO> page = userService.getUserPage(pageNum, pageSize, username, status);
        PageResult<UserVO> result = PageResult.of(page.getRecords(), page.getTotal(), pageNum, pageSize);
        return Result.success(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "查询用户详情", description = "根据ID查询用户详情")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return Result.success(userVO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "新增用户", description = "创建新用户")
    public Result<UserVO> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserVO userVO = userService.createUser(request);
        return Result.success("创建成功", userVO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新用户", description = "更新用户信息")
    public Result<UserVO> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        UserVO userVO = userService.updateUser(id, request);
        return Result.success("更新成功", userVO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除用户", description = "删除用户")
    public Result<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "更新用户状态", description = "启用或禁用用户")
    public Result<?> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success(status == 1 ? "启用成功" : "禁用成功");
    }

    @PutMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "重置密码", description = "将密码重置为123456")
    public Result<?> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return Result.success("密码已重置为123456");
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "修改密码", description = "用户修改自己的密码")
    public Result<?> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return Result.success("密码修改成功");
    }
}
