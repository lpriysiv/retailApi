db = db.getSiblingDB('testdb');
db.createUser({
    user: "test_user" , 
    pwd: "test_password", 
    roles: [  
        { role:"dbOwner", db: "testdb" }
    ]
});

db.products.insertOne([
    {
        "_id":111,
        "price": 10.99,
        "currencyCode":"USD"
    }
 ])