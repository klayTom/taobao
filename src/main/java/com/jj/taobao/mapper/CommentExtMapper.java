package com.jj.taobao.mapper;

import com.jj.taobao.model.Comment;
import com.jj.taobao.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentExtMapper {
   int inCommentCount(Comment comment);
}