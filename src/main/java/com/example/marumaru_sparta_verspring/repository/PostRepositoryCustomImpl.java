package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Param;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findByCustom_cursorPaging(Pageable pageable, String sorting, Long cursorIdx) {
        QPost post = QPost.post;

        List<Post> content = queryFactory
                .select(post)
                .from(post)
                .join(post.user)
                .fetchJoin()
                .orderBy(PostSort(pageable))
                .where(cursorId(sorting,cursorIdx))
                .limit(pageable.getPageSize())
                .fetch();

        //카운트 쿼리 따로
        long total = queryFactory
                .select(post.idx)
                .from(post)
                .join(post.user)
                .fetchCount();

        return new PageImpl<>(content,pageable,total);
    }

    /**
     * 커서페이징 조건절
     * @param sorting
     * @param cursorId
     * @return
     */
    private BooleanExpression cursorId(String sorting, Long cursorId) {
        QPost post = QPost.post;
        
        // id < 파라미터를 첫 페이지에선 사용하지 않기 위한 동적 쿼리
        if (cursorId==null) {
            return null; // // BooleanExpression 자리에 null 이 반환되면 조건문에서 자동으로 제거
        }
        else if(sorting.equals("createdAt"))
            return post.idx.lt(cursorId);
        else if(sorting.equals("view"))
            return post.view.lt(cursorId);
        else if(sorting.equals("countOfLikes"))
            return post.countOfLikes.lt(cursorId);
        else
            return post.idx.lt(cursorId);   //최신순
    }


    @Override
    public Page<Post> findByCustom_offsetPaging(Pageable pageable) {
        QPost post = QPost.post;

        List<Post> content = queryFactory
                .select(post)
                .from(post)
                .join(post.user)
                .fetchJoin()
                .orderBy(PostSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(post)
                .from(post)
                .fetchCount();

        return new PageImpl<>(content,pageable,total);
    }

    /**
     * OrderSpecifier 를 쿼리로 반환하여 정렬조건을 맞춰준다.
     * 리스트 정렬
     * @param page
     * @return
     */
    private OrderSpecifier<?> PostSort(Pageable page) {
        QPost post = QPost.post;

        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            //정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()){
                    case "createdAt" :
                    case "descending":
                        return new OrderSpecifier(direction, post.createdAt);
                    case "countOfLikes":
                        return new OrderSpecifier(direction, post.countOfLikes);
                    case "view":
                        return new OrderSpecifier(direction, post.view);

                }
            }
        }
        return null;
    }

}
