package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 文章实体类
 */
@Data
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 标题
     * 是否索引：该域是否被搜索
     * 是否分词：搜索时是整体匹配还是单词匹配
     * 是否存储：是否在页面上显示
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    /**
     * 文章正文
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    /**
     * 审核状态
     */
    private String state;
}
