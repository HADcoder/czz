package com.diet;/*
package com.jeeboot;

import com.jeeboot.core.persistence.Criteria;
import com.jeeboot.core.persistence.Restrictions;
import com.jeeboot.entity.TbTest;
import com.jeeboot.service.TbTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * @author LiuYu
 * @date 2018/4/24
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {

    @Autowired
    private TbTestService tbTestService;

    @Test
    public void testService() {
//        Object result = findById(1);
//        Object result = findOne(null);
//        Object result = findAll();
//        Object result = findAllByIds(null);
//        Object result = findAll(Example.of(new TbTest(), ExampleMatcher.matching()));
//        Object result = findAll(new Criteria<>());
//        Object result = findAll(PageRequest.of(1, 5));
//        Object result = findAll(Example.of(new TbTest(), ExampleMatcher.matching()), PageRequest.of(1, 5));
//        Object result = findAll(new Criteria<>(), PageRequest.of(1, 5));
//        Object result = save(null);
//        Object result = saveAll(null);
//        deleteById(27);
//        delete(null);
//        Object result = existsById(1);
//        System.out.println(JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat));
    }

    TbTest findById(Object id){
        return tbTestService.findById(id);
    }

    TbTest findOne(Criteria<TbTest> criteria){
        criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id",10));
        return tbTestService.findOne(criteria);
    }

    List<TbTest> findAll(){
        return tbTestService.findAll();
    }

    List<TbTest> findAllByIds(Iterable<Object> ids){
        ids = new ArrayList<Object>(){{add(1); add(2); add(3);}};
        return tbTestService.findAllByIds(ids);
    }

    List<TbTest> findAll(Example<TbTest> example){
        TbTest params = new TbTest();
        params.setName("3");

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith());
        example = Example.of(params, matcher);

        return tbTestService.findAll(example);
    }

    List<TbTest> findAll(Criteria<TbTest> criteria){
        criteria = new Criteria<>();
        criteria.add(Restrictions.likeLeft("name","4"));
        return tbTestService.findAll(criteria);
    }

    Page<TbTest> findAll(Pageable pageable){
        Sort sort = new Sort(Sort.Direction.DESC, "code");
        pageable = PageRequest.of(0, 5, sort);

        return tbTestService.findAll(pageable);
    }

    Page<TbTest> findAll(Example<TbTest> example, Pageable pageable){
        TbTest params = new TbTest();
        params.setName("测试4");

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith());
        example = Example.of(params, matcher);

        Sort sort = new Sort(Sort.Direction.DESC, "code");
        pageable = PageRequest.of(0, 5, sort);

        return tbTestService.findAll(example, pageable);
    }

    Page<TbTest> findAll(Criteria<TbTest> criteria, Pageable pageable){
        criteria = new Criteria<>();
        criteria.add(Restrictions.like("name","4"));

        Sort sort = new Sort(Sort.Direction.DESC, "code");
        pageable = PageRequest.of(0, 5, sort);
        return tbTestService.findAll(criteria, pageable);
    }

    TbTest save(TbTest entity){
        entity = new TbTest();
        entity.setCode("abc");
        entity.setName("Abc");
        entity.setCreateTime(new Date());
        return tbTestService.save(entity);
    }

    List<TbTest> saveAll(Iterable<TbTest> entities){
        List<TbTest> list = new ArrayList<>();
        TbTest entity = new TbTest();
        entity.setCode("abc111");
        entity.setName("Abc111");
        entity.setCreateTime(new Date());
        list.add(entity);

        TbTest entity1 = new TbTest();
        entity1.setCode("abc222");
        entity1.setName("Abc222");
        entity1.setCreateTime(new Date());
        list.add(entity1);

        return tbTestService.saveAll(list);
    }

    void deleteById(Object id){
        tbTestService.deleteById(id);
    }

    void delete(TbTest entity){
        entity = tbTestService.findById(28);
        tbTestService.delete(entity);
    }

    void deleteAll(Iterable<TbTest> entities){
        List<TbTest> list = tbTestService.findAllByIds(new ArrayList<Object>(){{add(1); add(2); add(3);}});
        tbTestService.deleteAll(list);
    }

    void deleteAll(){
        tbTestService.deleteAll();
    }

    boolean existsById(Object id){
        return tbTestService.existsById(id);
    }

    long count(){
        return tbTestService.count();
    }

    long count(Criteria<TbTest> criteria){
        return tbTestService.count(criteria);
    }
}
*/
