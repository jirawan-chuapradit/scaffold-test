package com.tvisarut.scaffold.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tvisarut.scaffold.entity.Employee;
import com.tvisarut.scaffold.repository.EmployeeRepository;

@Service
public class AuthService implements UserDetailsService{

	@Autowired
	private EmployeeRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		Employee user = repository.findByUsername(userName);
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
}
