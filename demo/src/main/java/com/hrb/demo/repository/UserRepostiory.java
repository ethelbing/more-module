package com.hrb.demo.repository;


import com.hrb.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link com.hrb.demo.domain.User},{@link org.springframework.stereotype.Repository}
 * */
@Repository
public class UserRepostiory {
    /**
     * 采用内存性的存储方式 -> Map
     * 考虑高并发时候的情况
     * */
    private ConcurrentMap<Integer,User> repository = new ConcurrentHashMap<>();
    private final static AtomicInteger idGeneerator = new AtomicInteger();

    /**
     * 保存用户对象
     * @param user {@link User}
     * @return 如果保存成功，返回<code>true</code>
     *         否则，返回<code>false</code>
     * */
    public boolean save(User user){
//        id 从1开始
        Integer id= idGeneerator.incrementAndGet();
        // 设置 ID
        user.setId(id);
        return repository.put(id,user) == null;
    }

    /** 返回所有用户列表
     * @return
     * */
    public Collection<User> findAll(){
        return repository.values();
    }
}
