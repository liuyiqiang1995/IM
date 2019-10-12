package com.qdcares.smartmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Description: JPA BaseRepository
 * @Author: LiuYiQiang
 * @Date: 10:15 2019/9/3
 */
@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {

}
