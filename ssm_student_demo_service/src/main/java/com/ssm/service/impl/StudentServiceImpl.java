package com.ssm.service.impl;


import com.ssm.error.DefaultError;
import com.ssm.error.exception.BaseBusinessException;
import com.ssm.mapper.StudentMapper;
import com.ssm.model.Student;
import com.ssm.model.example.StudentExample;
import com.ssm.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ouyangyufeng
 * @date 2019/1/23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {
    /**
     * 缓存过期时间
     */
    private static final long TIMEOUT = 60;

    /**
     * 缓存名称
     */
    private static final String CACHE_NAME = "listStu";

    /**
     * 实例化日志
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    Lock lock;

    /**
     * <p>通用查询学生信息</p>
     *
     * @param sid
     * @param sname
     * @param sage
     * @return
     */
    @Override
    public List<Student> selectByStudent(Long sid, String sname, Integer sage) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        //根据sid条件查询
        if (sid != null) {
            criteria.andSidEqualTo(sid);
        }

        //根据sname条件查询
        if (sname != null) {
            criteria.andSnameEqualTo(sname);
        }

        //根据sage条件查询
        if (sage != null) {
            criteria.andSageEqualTo(sage);
        }
        List<Student> listStu = studentMapper.selectByExample(example);
        return listStu.size() > 0 ? listStu : null;
    }

    /**
     * <p>查询学生所以信息</p>
     *
     * @return
     */
    @Override
    public List<Student> selectAllStudent() {
        ValueOperations<String, List<Student>> operations = redisTemplate.opsForValue();
        List<Student> listStu = null;
        boolean hasKey = redisTemplate.hasKey(CACHE_NAME);

        //避免缓存雪崩
        lock = new ReentrantLock();
        if (lock.tryLock()) {
            try {
                if (hasKey) {
                    // 读取缓存
                    listStu = operations.get(CACHE_NAME);
                    logger.info("CityServiceImpl.findCityById() : 从缓存中获取了数据 >> {}", listStu.toString());
                } else {
                    listStu = studentMapper.selectStudent();
                    // 插入缓存
                    operations.set(CACHE_NAME, listStu, TIMEOUT, TimeUnit.SECONDS);
                    logger.info("CityServiceImpl.findCityById() : 数据插入缓存 >> {}", listStu.toString());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return listStu.size() > 0 ? listStu : null;
    }

    /**
     * <p>添加学生信息</p>
     *
     * @param stu
     */
    @Override
    public void insertStudent(Student stu) {
        try {
            // 缓存存在，删除缓存
            if (redisTemplate.hasKey(CACHE_NAME)) {
                redisTemplate.delete(CACHE_NAME);
            }
            studentMapper.insertStudent(stu);
        } catch (Exception ex) {
            logger.error("添加失败! stu:{}; error:{}", stu, ex);
            throw new BaseBusinessException(DefaultError.STUDENT_INSERt_ERROR);
        }
    }

    /**
     * <p>删除学生信息</p>
     *
     * @param sid
     */
    @Override
    public void deleteStudent(Long sid) {
        try {
            if (studentMapper.selectByPrimaryKey(sid) == null) {
                throw new BaseBusinessException(DefaultError.STUDENT_DATA_ERROR);
            }
            // 缓存存在，删除缓存
            if (redisTemplate.hasKey(CACHE_NAME)) {
                redisTemplate.delete(CACHE_NAME);
            }
            studentMapper.deleteByPrimaryKey(sid);
        } catch (Exception ex) {
            logger.error("删除失败! sid:{}; error:{}", sid, ex);
            throw new BaseBusinessException(DefaultError.STUDENT_DELETE_ERROR);
        }
    }

    /**
     * <p>修改学生信息</p>
     *
     * @param stu
     */
    @Override
    public void updateStudent(Student stu) {
        try {
            if (redisTemplate.hasKey(CACHE_NAME)) {
                redisTemplate.delete(CACHE_NAME);
            }
            studentMapper.updateByPrimaryKey(stu);
        } catch (Exception ex) {
            logger.error("修改失败! stu:{}; error:{}", stu, ex);
            throw new BaseBusinessException(DefaultError.STUDENT_UPDATE_ERROR);
        }
    }
}
