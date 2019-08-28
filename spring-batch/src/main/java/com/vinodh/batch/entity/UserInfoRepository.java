package com.vinodh.batch.entity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfo, Long> {
}
