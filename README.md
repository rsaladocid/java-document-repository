com.rsaladocid.repository
=========================

A simple vendor-agnostic API that supports CRUD operations on key-value pairs indexed by your favorite document-oriented database.

Basic usage
-----------------

### Create index

First of all, you have to index your data. The index allows you to retrieve, update or delete your key-value pairs at any time.

```java
Repository repository = ...;
	
Map<String, Object> data = new HashMap<String, Object>();
data.put("name", "Alice");
data.put("email", "alice@geemail.com");

CreateIndexOperation createIndexOperation = new CreateIndexOperation(data);
Index index = repository.create(createIndexOperation); // Returns: asQ9872jdsq
```

### Retrieve index

Retrieving your data is so easy as using the corresponding index.

```java
IndexOperation indexOperation = new IndexOperation(index);
Map<String, Object> data = repository.retrieve(indexOperation); // Returns: {name=Alice,email=alice@geemail.com}
```

### Update index

You can always replace your outdated data by new ones.

```java
Map<String, Object> newData = new HashMap<String, Object>();
newData.put("name", "Bob");
newData.put("email", "bob@geemail.com");

UpdateIndexOperation updateOperation = new UpdateIndexOperation(index, newData);
repository.update(updateOperation);

IndexOperation indexOperation = new IndexOperation(index);
repository.retrieve(indexOperation); // Returns: {name=Bob,email=bob@geemail.com}
```

### Delete index

When you don't need your data anymore, remove them.

```java
IndexOperation indexOperation = new IndexOperation(index);
repository.delete(indexOperation);

repository.retrieve(operation); // Throws: MissingIndexException
```

License
-------
Code is under the [MIT License](https://opensource.org/licenses/MIT)