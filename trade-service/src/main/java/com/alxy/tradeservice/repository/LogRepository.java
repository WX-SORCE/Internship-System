package com.alxy.tradeservice.repository;



import com.alxy.tradeservice.entiy.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
