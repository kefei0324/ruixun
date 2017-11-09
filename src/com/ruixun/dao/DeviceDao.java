package com.ruixun.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.modules.persistence.MyJpaRepository;

import com.ruixun.entity.Device;

public interface DeviceDao
		extends MyJpaRepository<Device, Serializable>, PagingAndSortingRepository<Device, Serializable> {

	Device findByName(String name);

	List<Device> findAll(Sort sort);

	Page<Device> findAll(Pageable pageable);

}
