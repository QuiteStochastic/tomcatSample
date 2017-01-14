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

    private MongoCollection<Document> collection;

    public PersonDAO(MongoClient mongo) {
        this.collection = mongo.getDatabase("mongo").getCollection("Persons");
    }

    public Person createPerson(Person p) {
        //DBObject doc = PersonConverter.toDBObject(p);
        Gson gson=new Gson();

        Document doc=Document.parse(gson.toJson(p));
        this.collection.insertOne(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }

    public void updatePerson(Person newPerson) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();
        Gson gson=new Gson();


        ObjectId id = new ObjectId(newPerson.getId());//id should be the same as before

        Document q=new Document();
        q.append("_id",id);

        newPerson.setId(null);//avoid parse errors
        Document newDoc=Document.parse(gson.toJson(newPerson));
        newDoc.append("_id",id);

        System.out.println("newDoc: "+newDoc.toJson());
        this.collection.findOneAndUpdate(q, newDoc);

    }

    public List<Person> readAllPerson() {



        List<Person> personList = new ArrayList<Person>();

        for (Document doc :  collection.find()) {

            Person p = convertDocToPerson(doc);

            personList.add(p);
        }
        return personList;
    }


    public void deletePerson(Person p) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();

        Document q=new Document();

        ObjectId id = new ObjectId(p.getId());

        q.append("_id",id);


        this.collection.deleteOne(q);
    }

    public Person readPerson(Person p) {
        //DBObject query = BasicDBObjectBuilder.start().append("_id", new ObjectId(p.getId())).get();


        ObjectId id = new ObjectId(p.getId());

        Document q=new Document();
        q.append("_id",id);

        Document data = this.collection.find(q).first();

        return convertDocToPerson(data);
    }


    private Person convertDocToPerson(Document doc) {

        Gson gson=new Gson();

        System.out.println("doc: "+doc.toJson());

        Person p = gson.fromJson(doc.toJson(),Person.class);

        ObjectId id = (ObjectId) doc.get("_id");
        p.setId(id.toString());
        return p;
    }

}