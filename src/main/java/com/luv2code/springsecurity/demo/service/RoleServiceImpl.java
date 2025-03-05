package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.dao.RoleRepository;
import com.luv2code.springsecurity.demo.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
    private RoleRepository roleDao;
	
	@Override
	public List<Role> findAllRoles() {
		
		return roleDao.findAll();
	}

}
