package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dto.request.ChangePasswordRequest;
import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.entity.SysUser;
import com.example.demo.vo.UserVO;

public interface UserService {

    IPage<UserVO> getUserPage(Integer pageNum, Integer pageSize, String username, Integer status);

    UserVO getUserById(Long id);

    UserVO createUser(UserCreateRequest request);

    UserVO updateUser(Long id, UserUpdateRequest request);

    void deleteUser(Long id);

    void updateStatus(Long id, Integer status);

    void resetPassword(Long id);

    void changePassword(ChangePasswordRequest request);

    SysUser getCurrentUser();

    UserVO getCurrentUserVO();
}
