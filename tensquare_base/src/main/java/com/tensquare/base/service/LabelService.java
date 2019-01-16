package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String labelId) {
        return labelDao.findById(labelId).get();
    }

    public void save(Label label) {
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void delete(String labelId) {
        labelDao.deleteById(labelId);
    }

    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象，及条件即将被封装到该对象中。where 列名 = label.getXXX
             * @param query 查询关键字的封装。例如 group by，order by等。几乎不用！
             * @param cb 用来封装条件对象。
             * @return 返回null代表没有任何条件
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // new一个list集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && label.getLabelname() != "") {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"); // where labelname like "%xiaoming%"
                    list.add(predicate);
                }
                if (label.getState() != null && label.getState() != "") {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState()); // where state = "1"
                    list.add(predicate);
                }
                // new一个数组作为最终返回的条件
                Predicate[] parr = new Predicate[list.size()];
                // 把list转换为数组
                // parr = list.toArray(parr);
                list.toArray(parr);
                return cb.and(parr); // where labelname like "%xiaoming%" and state = "1"
            }
        });
    }

    public Page<Label> pageQuery(Label label, int page, int size) {
        // 封装分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && label.getLabelname() != "") {
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && label.getState() != "") {
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                list.toArray(parr);
                return cb.and(parr);
            }
        }, pageable);
    }
}
