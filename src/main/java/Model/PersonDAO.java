package Model;

import Model.Pojo.Person;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class PersonDAO {

    private MongoCollection<Document> col;

    public PersonDAO(MongoClient mongo) {
        this.col = mongo.getDatabase("mongo").getCollection("Persons");
    }

    public Person createPerson(Person p) {
        //DBObject doc = PersonConverter.toDBObject(p);
        Gson gson=new Gson();

        Document doc=Document.parse(gson.toJson(p));
        this.col.insertOne(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }

    public void updatePerson(Person p) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();
        Gson gson=new Gson();


        Document q=new Document();
        q.append("_id",p.getId());


        this.col.updateOne(q, Document.parse(gson.toJson(p)));
    }

    public List<Person> readAllPerson() {
        Gson gson=new Gson();


        List<Person> data = new ArrayList<Person>();

        for (Document doc :  col.find()) {

            Person p = gson.fromJson(doc.toJson(),Person.class);
            data.add(p);
        }
        return data;
    }

    public void deletePerson(Person p) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();

        Document q=new Document();
        q.append("_id",p.getId());


        this.col.deleteOne(q);
    }

    public Person readPerson(Person p) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();

        Gson gson=new Gson();


        Document q=new Document();
        q.append("_id",p.getId());

        Document data = this.col.find(q).first();
        return gson.fromJson(data.toJson(),Person.class);
    }

}