package com.mwahler.PRTServer.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mwahler.PRTServer.models.DataPointEntity;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBDataPointRepository implements DataPointRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<DataPointEntity> dataPointCollection;
    @Value("${spring.data.mongodb.database}")
    private String database;

    public MongoDBDataPointRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        dataPointCollection = client.getDatabase(database).getCollection("dataPoints", DataPointEntity.class);
    }

    @Override
    public DataPointEntity save(DataPointEntity dataPointEntity) {
        dataPointEntity.setId(new ObjectId());
        dataPointCollection.insertOne(dataPointEntity);
        return dataPointEntity;
    }

    @Override
    public List<DataPointEntity> saveAll(List<DataPointEntity> dataPointEntities) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                dataPointEntities.forEach(p -> p.setId(new ObjectId()));
                dataPointCollection.insertMany(clientSession, dataPointEntities);
                return dataPointEntities;
            }, txnOptions);
        }
    }

    @Override
    public List<DataPointEntity> findAll() {
        return dataPointCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<DataPointEntity> findAllByCar(String id) {
        return dataPointCollection.find(eq("carId", new ObjectId(id))).into(new ArrayList<>());
    }

    @Override
    public List<DataPointEntity> findAll(List<String> ids) {
        return dataPointCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public DataPointEntity findOne(String id) {
        return dataPointCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return dataPointCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return dataPointCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> dataPointCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> dataPointCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public DataPointEntity update(DataPointEntity dataPointEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return dataPointCollection.findOneAndReplace(eq("_id", dataPointEntity.getId()), dataPointEntity, options);
    }

    @Override
    public long update(List<DataPointEntity> dataPointEntities) {
        List<ReplaceOneModel<DataPointEntity>> writes = dataPointEntities.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()),
                        p))
                .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> dataPointCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).toList();
    }
}