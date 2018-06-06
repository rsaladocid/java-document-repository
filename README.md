com.rsaladocid.repository
=========================

A simple common API that supports CRUD operations to store and retrieve key-value pairs from your favorite document-based database.

Currently, the following NoSQL document-based databases are supported: ElasticSearch

Basic usage
-----------------

### Create document

A document is automatically generated when the key-value pairs are stored into a document-based database:

```java
Repository repository = new ElasticSearchRepository("accounts", "user", builder);

try {
	repository.connect();
	
	Map<String, Object> data = new HashMap<String, Object>();
	data.put("name", "Alice");
	data.put("email", "alice@geemail.com");

	Document document = repository.create(data);
	
	document.getId(); // Returns: asQ9872jdsq
	document.getData(): // Returns: {name=Alice,email=alice@geemail.com}
	
	repository.disconnect();
} catch (IOException e) {
	e.printStackTrace();
}
```

### Retrieve document

Any document can be retrieved from the database using the corresponding id:

```java
Repository repository = new ElasticSearchRepository("accounts", "user", builder);

try {
	repository.connect();
	
	Document document = repository.retrieve("asQ9872jdsq");
	
	document.getId(); // Returns: asQ9872jdsq
	document.getData(): // Returns: {name=Alice,email=alice@geemail.com}
	
	repository.disconnect();
} catch (IOException e) {
	e.printStackTrace();
}
```

### Update document
-----------------

When the content of a document is changed, don´t forget commit it:

```java
Repository repository = new ElasticSearchRepository("accounts", "user", builder);

try {
	repository.connect();
	
	Document document = repository.retrieve("asQ9872jdsq");
	document.getData(): // Returns: {name=Alice,email=alice@geemail.com}
	
	document.getData().put("name", "Bob");
	document.getData().put("email", "bob@geemail.com");
	
	String id = repository.update(document);
	
	Document updatedDocument = repository.retrieve(id);
	updatedDocument.getData(): // Returns: {name=Bob,email=bob@geemail.com}
	
	repository.disconnect();
} catch (IOException e) {
	e.printStackTrace();
}
```

### Delete document
-----------------

Any document can be removed from the database using the corresponding id:


```java
Repository repository = new ElasticSearchRepository("accounts", "user", builder);

try {
	repository.connect();

	repository.delete("asQ9872jdsq");
	repository.retrieve("asQ9872jdsq"); // Returns: null
	
	repository.disconnect();
} catch (IOException e) {
	e.printStackTrace();
}
```


License
-------
Code is under the [MIT License](https://opensource.org/licenses/MIT)