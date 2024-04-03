package com.example.backend.repository.follow;

import com.example.backend.entity.follow.Following;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class FollowingRepository {
    private final MongoTemplate mongoTemplate;

    public void addFollowing(Long memberId, Long followingId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().addToSet("followingIds", followingId);
        mongoTemplate.upsert(query, update, Following.class);
    }

    public void removeFollowing(Long memberId, Long followingId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().pull("followingIds", followingId);
        mongoTemplate.updateFirst(query, update, Following.class);
    }

    public boolean existsFollowingId(Long memberId, Long followingId) {
        Query query = new Query(Criteria.where("_id").is(memberId)
                .and("followingIds").in(followingId));
        return mongoTemplate.exists(query, Following.class);
    }

    public Following findById(Long memberId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        return mongoTemplate.findOne(query, Following.class);
    }
}
