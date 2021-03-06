package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    // @Autowired
    // private IdWorker idWorker;

    public void add(Article article) {
        // article.setId(String.valueOf(idWorker.nextId()));
        articleDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        PageRequest pageData = PageRequest.of(page - 1, size);
        return articleDao.findByTitleAndAndContentLike(key, key, pageData);
    }
}
