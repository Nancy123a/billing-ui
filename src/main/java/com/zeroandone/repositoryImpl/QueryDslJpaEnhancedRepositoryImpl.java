package com.zeroandone.repositoryImpl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.zeroandone.repository.QueryDslPredicateAndProjectionExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by shahbour on 10/11/16.
 */
public class QueryDslJpaEnhancedRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
        implements QueryDslPredicateAndProjectionExecutor<T, ID> {

    //All instance variables are available in super, but they are private
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;

    public QueryDslJpaEnhancedRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    public QueryDslJpaEnhancedRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager,
                                             EntityPathResolver resolver) {

        super(entityInformation, entityManager, resolver);
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    @Override
    public <PROJ> Page<PROJ> customFindWithProjection(FactoryExpression<PROJ> factoryExpression, Predicate predicate, Pageable pageable) {

        JPQLQuery countQuery = createQuery(predicate);
        JPQLQuery query = querydsl.applyPagination(pageable, createQuery(predicate));

        Long total = countQuery.fetchCount();
        List<PROJ> content = total > pageable.getOffset() ? query.select(factoryExpression).fetch() : Collections.<PROJ>emptyList();

        return new PageImpl<PROJ>(content, pageable, total);
    }

    @Override
    public <PROJ> List<PROJ> customFindWithProjection(FactoryExpression<PROJ> factoryExpression, Predicate predicate, List<Expression<?>> groupBy,Predicate havingCondition) {
        JPQLQuery query = createQuery(predicate);
        groupBy.forEach(p -> query.groupBy(p));
        if(havingCondition != null) {
            query.having(havingCondition);
        }
        return query.select(factoryExpression).fetch();
    }

}
