package com.example.backend.repository.follow;

import com.example.backend.entity.follow.Following;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class FollowingRepository {
    private final MongoTemplate mongoTemplate;

    public void addFollowing(Long memberId, Following.Person followingPerson) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().addToSet("followingPerson", followingPerson);
        mongoTemplate.upsert(query, update, Following.class);
    }

    public void removeFollowing(Long memberId, Long followingId) {
        Query query = new Query(Criteria.where("_id").is(memberId));
        Update update = new Update().pull("followingPerson", new Query(Criteria.where("id").is(followingId)));
        mongoTemplate.updateFirst(query, update, Following.class);
    }

    public List<Following.Person> getFollowingsByCursor(Long memberId, Date cursor, int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(memberId)),
                Aggregation.unwind("followingPerson"),
                Aggregation.match(Criteria.where("followingPerson.followingTime").lt(cursor)),
                Aggregation.sort(Sort.Direction.DESC, "followingPerson.followingTime"),
                Aggregation.limit(limit),
                Aggregation.project().and("followingPerson.memberId").as("memberId")
                        .and("followingPerson.followingTime").as("followingTime")
        );

        return mongoTemplate
                .aggregate(aggregation, Following.class, Following.Person.class)
                .getMappedResults();
    }

    public boolean existsByMemberIdInFollowingPerson(Long memberId, Long followingId) {
        Query query = new Query(Criteria.where("_id").is(memberId)
                .and("followingPerson.id").is(followingId));
        return mongoTemplate.exists(query, Following.class);
    }
}
