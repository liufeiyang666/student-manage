package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.entity.SysUser;

public interface UserService {

    IPage<SysUser> getUserPage(Integer pageNum, Integer pageSize, String username, Integer status, String roleCode);

    SysUser getUserById(Long id);

    SysUser createUser(UserCreateRequest request);

    SysUser updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    void updateStatus(Long id, Integer status);

    void resetPassword(Long id);

    SysUser getCurrentUser();

    SysUser getCurrentUserVO();
}
