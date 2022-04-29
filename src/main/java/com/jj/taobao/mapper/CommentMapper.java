package com.jj.taobao.mapper;

import com.jj.taobao.model.Comment;
import com.jj.taobao.model.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    long countByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int deleteByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    List<Comment> selectByExampleWithRowbounds(CommentExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    Comment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbg.generated Thu Apr 28 21:14:01 GMT+08:00 2022
     */
    int updateByPrimaryKey(Comment record);
}