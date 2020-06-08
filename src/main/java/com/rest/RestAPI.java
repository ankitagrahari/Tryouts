package com.rest;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class RestAPI {

    private static MongoClient getMongoClient(){
//        This will create client for default host localhost and default port 27017
//        MongoClient client = new MongoClient();
//        This will create client for given host and default port 27017
//        MongoClient client = new MongoClient("localhost");
//        This will create client for given host and port
//        MongoClient client = new MongoClient("localhost", 27017);
//        We can use the connection string to get the client
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient = new MongoClient(connectionString);

        return mongoClient;
    }

    public static List<Document> createDocuments(MongoClient client){

        //Listing all the database
        MongoIterable<String> itr = client.listDatabaseNames();
        for (MongoCursor<String> it = itr.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        //Access given database
        MongoDatabase db = client.getDatabase("mydb");
        System.out.println(db.getName());

        //Create a collection under mydb
//        db.createCollection("mycol");

        //Access given collection
        MongoCollection<Document> collection = db.getCollection("mycol");
        System.out.println("After creating the collection, total count of documents is "+collection.count());

        Document document = new Document("name", "NewDoc")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y",102));
        collection.insertOne(document);
        System.out.println("After adding one document to the collection, total count of documents is "+collection.count());

        List<Document> documents = new ArrayList<>();
        Document document1 = new Document("name", "SecDoc")
                .append("type", "middleware")
                .append("count", 2)
                .append("versions", Arrays.asList("v1.2", "v1.0"));
        Document document2 = new Document("name", "ThirdDoc")
                .append("type", "database")
                .append("count", 3)
                .append("versions", Arrays.asList("11g", "12c", "18c", "19c"));

        documents.add(document); documents.add(document1); documents.add(document2);
        collection.insertMany(documents);
        System.out.println("After adding three document to the collection, total count of documents is "+collection.count());
        RestAPI.listAllDocuments(collection);
        return documents;
    }

    public static void findDocuments(MongoClient client){
        MongoDatabase db = client.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("mycol");
        Document firstDoc = collection.find().first();
        System.out.println(firstDoc.toJson());
    }

    public static void listAllDocuments(MongoCollection<Document> collection){
        System.out.println("Listing all documents in the collection in JSON format");
        MongoCursor<Document> cursor = collection.find().iterator();
        while(cursor.hasNext()){
            System.out.println(cursor.next().toJson());
        }
    }

    public static void queryDocuments(MongoClient client){
        MongoDatabase db = client.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("mycol");
        Document docTypeMiddleware = collection.find(eq("type","middleware")).first();
        System.out.println("Document where type=middleware: ");
        System.out.println(docTypeMiddleware.toJson());

        Document docTypeDBCount1 = collection.find(and(eq("type", "database"), eq("count", 1))).first();
        System.out.println("Document where type=database and count=1");
        System.out.println(docTypeDBCount1.toJson());
    }

    public static void updateDocuments(MongoClient client){
        MongoDatabase db = client.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("mycol");
        UpdateResult updateResult = collection.updateMany(eq("type","database"), new Document("$set", new Document("count", 5)));
        System.out.println(updateResult.getModifiedCount());
        RestAPI.listAllDocuments(collection);
    }

    public static void deleteDocument(MongoClient client){
        MongoDatabase db = client.getDatabase("mydb");
        MongoCollection<Document> collection = db.getCollection("mycol");
        DeleteResult deleteResult = collection.deleteOne(eq("type", "middleware"));
        System.out.println(deleteResult.getDeletedCount());

        RestAPI.listAllDocuments(collection);
    }

    public static void main(String[] args) {
        try(MongoClient client = RestAPI.getMongoClient()) {
//            RestAPI.createDocuments(client);
//            RestAPI.findDocuments(client);
//            RestAPI.queryDocuments(client);
//            RestAPI.updateDocuments(client);
            RestAPI.deleteDocument(client);
        }
    }
}
