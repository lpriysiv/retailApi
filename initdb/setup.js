db = db.getSiblingDB('productdb');
db.createUser({
    user: "test_user" , 
    pwd: _getEnv('MONGO_PASSWORD'), 
    roles: [  
        { role:"dbOwner", db: "productdb" }
    ]
});

db.products.insertMany([
    {
        "_id":13860428,
        "price": 13.49,
        "currencyCode":"USD"
    },
    {
        "_id":12954218,
        "price": 4.05,
        "currencyCode":"USD"
    },
    {
        "_id":13264003,
        "price": 5.99,
        "currencyCode":"USD"
    }
 ])